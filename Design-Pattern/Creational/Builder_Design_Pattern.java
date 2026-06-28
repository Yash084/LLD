/*
======================== BUILDER DESIGN PATTERN ========================

🔹 What is Builder Pattern?
A creational design pattern used to construct complex objects step-by-step.
It avoids telescoping constructors and improves readability.

🔹 Why use it?
- When a class has many fields (especially optional ones)
- To make object creation readable and flexible
- To create immutable objects (fields can be final)
- To avoid constructor overloading explosion

🔹 Core Idea:
- Separate object construction from representation
- Use a static inner Builder class
- Use method chaining to set fields
- Call build() to create the final object

🔹 Structure:
1. Main class:
   - Fields are usually final (immutable)
   - Private constructor that takes Builder as input

2. Builder class:
   - Same fields as main class
   - Methods to set fields (return Builder for chaining)
   - build() method returns final object

🔹 Flow:
User u = new User.Builder()
                .name("Yash")
                .email("abc@gmail.com")
                .build();

→ Builder collects values
→ build() calls new User(builder)
→ Object is created

🔹 Advantages:
✔ Readable and clean object creation
✔ Handles optional parameters easily
✔ Encourages immutability
✔ Scalable and maintainable

🔹 Optional Improvements:
- Add validation inside build()
- Add static builder() method for cleaner usage
- Make Builder fields private (encapsulation)

🔹 Interview One-liner:
"Builder pattern is used to construct objects step-by-step using a fluent API,
avoiding telescoping constructors and enabling readable, maintainable code."

=======================================================================
*/

class User {
	final String name;
	final String email;
	final String roll;

	private User(Builder builder) {
		this.name = builder.name;
		this.email = builder.email;
		this.roll = builder.roll;
	}

	public static class Builder {
		String name;
		String email;
		String roll;

		public Builder() {
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder roll(String roll) {
			this.roll = roll;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}
}

public class Main
{
	public static void main(String[] args) {
	    User u = new User.Builder().name("Yash").build();
        System.out.println(u.name);
	}
}
