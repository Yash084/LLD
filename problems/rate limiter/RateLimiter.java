import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.*;

enum RateLimiterType {
    TOKEN_BUCKET,
    LEAKY_BUCKET,
    FIXED_WINDOW,
    SLIDING_WINDOW_LOG,
    SLIDING_WINDOW_COUNTER
}

enum UserTier {
    FREE_TIER,
    PREMIUM_TIER
}


class RateLimitingConfig {
    int maxRequests;
    int windowInSeconds;

    public RateLimitingConfig(int maxRequests, int windowInSeconds){
        this.maxRequests = maxRequests;
        this.windowInSeconds = windowInSeconds;
    }
}

class User {
    String userId;
    UserTier tier;

    public User(String userId, UserTier tier){
        this.userId = userId;
        this.tier = tier;
    }
}

abstract class RateLimiter {

    protected final RateLimitingConfig config;

    protected final RateLimiterType type;

    RateLimiter(RateLimitingConfig config,
                RateLimiterType type) {
        this.config = config;
        this.type = type;
    }

    public abstract boolean allowRequest(String userId);
}

class Bucket {
    int tokens;
    long lastRefillTime;

    public Bucket(int tokens, long lastRefillTime) {
        this.tokens = tokens;
        this.lastRefillTime = lastRefillTime;
    }
}

class TokenBucketRateLimiter extends RateLimiter {

    private final Map<String, Bucket> bucketMap = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(RateLimitingConfig config) {
        super(config, RateLimiterType.TOKEN_BUCKET);
    }

    @Override
    public boolean allowRequest(String userId) {
        AtomicBoolean allowed = new AtomicBoolean(false);
        long now = System.currentTimeMillis() / 1000;
        bucketMap.compute(userId, (id, bucket)->{
            if(bucket == null){
                bucket = new Bucket(config.maxRequests, now);
            }
            refill(bucket, now);
            if(bucket.tokens > 0){
                bucket.tokens--;
                allowed.set(true);
            }
            return bucket;
        });
        return allowed.get();
    }

    void refill(Bucket bucket, long now){
        var rate = config.windowInSeconds / config.maxRequests;
        var lastRefillTime = bucket.lastRefillTime;
        var elapsedTime = now - lastRefillTime;
        var newTokensToBeAdded =(int) elapsedTime / rate;
        bucket.tokens = Math.min(config.maxRequests, bucket.tokens + newTokensToBeAdded);
        bucket.lastRefillTime += newTokensToBeAdded * rate;
    }
}

class FixedWindow{
    int requestCount;
    long startTime;

    FixedWindow(int requestCount, long startTime){
        this.requestCount = requestCount;
        this.startTime = startTime;
    }
}

class FixedWindowRateLimiter extends RateLimiter {
    private final Map<String, FixedWindow> requestCount = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(RateLimitingConfig config) {
        super(config, RateLimiterType.FIXED_WINDOW);
    }

    public boolean allowRequest(String userId) {
        AtomicBoolean allowed = new AtomicBoolean(false);
        long now = System.currentTimeMillis() / 1000;

        requestCount.compute(userId, (id, window) -> {
            int currentReqWindow = (int) now / config.windowInSeconds;

            if(window == null){
                window = new FixedWindow(0, now);
            }
            long lastReqWindow = window.startTime;
            if(lastReqWindow != currentReqWindow){
                window = new FixedWindow(0, currentReqWindow);
            }
            if(window.requestCount < config.maxRequests){
                window.requestCount++;
                allowed.set(true);
            }
            return window;
        });
        return allowed.get();
    }
}

class SlidingWindowLogRateLimiter extends RateLimiter {

    private final Map<String, Queue<Long>> requestLog = new ConcurrentHashMap<>();

    public SlidingWindowLogRateLimiter(RateLimitingConfig config) {
        super(config, RateLimiterType.SLIDING_WINDOW_LOG);
    }

    @Override
    public boolean allowRequest(String userId) {
        AtomicBoolean allowed = new AtomicBoolean(false);
        long now = System.currentTimeMillis() / 1000;
        requestLog.compute(userId, (id, log) -> {
            if(log == null){
                log = new ArrayDeque<>();
            }

            while(log.size() > 0 && (now - log.peek()) > config.windowInSeconds){
                log.poll();
            }

            if(log.size() < config.maxRequests){
                log.add(now);
                allowed.set(true);
            }
            return log;
        });
        return allowed.get();
    }

}

class RateLimiterFactory {

    private final Map<UserTier, RateLimiter> limiters = new HashMap<>();

    public RateLimiterFactory() {
        limiters.put( UserTier.FREE_TIER, new TokenBucketRateLimiter(new RateLimitingConfig(10, 60)));
        limiters.put( UserTier.PREMIUM_TIER, new SlidingWindowLogRateLimiter(new RateLimitingConfig(100, 60)));
    }

    public RateLimiter getRateLimiter(UserTier tier) {
        return limiters.get(tier);
    }
}

class RateLimiterService{

    private final RateLimiterFactory factory = new RateLimiterFactory();

    public boolean allowRequest(User user){
        var limiter = factory.getRateLimiter(user.tier);
        if(limiter == null){
            throw new IllegalArgumentException("Rate limiter not configured for given tier");
        }
        return limiter.allowRequest(user.userId);
    }

}



public class RateLimiterImpl{

    public static void main(String[] args) throws Exception{
        var rateLimiterService = new RateLimiterService();
        User free = new User("free_user_id", UserTier.FREE_TIER);
        User premium = new User("premium_user_id", UserTier.PREMIUM_TIER);

        System.out.println("=== Free User Requests ===");
        for (int i = 1; i <= 15; i++) {
            boolean allowed = rateLimiterService.allowRequest(free);
            System.out.println("Request " + i + " for Free User: " + (allowed ? "ALLOWED" : "BLOCKED"));
            Thread.sleep(100); // simulate delay between requests
        }

        System.out.println("\n=== Premium User Requests ===");
        for (int i = 1; i <= 120; i++) {
            boolean allowed = rateLimiterService.allowRequest(premium);
            System.out.println("Request " + i + " for Premium User: " + (allowed ? "ALLOWED" : "BLOCKED"));
            Thread.sleep(100);
        }
    }
}
