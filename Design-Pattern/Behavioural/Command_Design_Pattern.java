/*
===============================
COMMAND DESIGN PATTERN NOTES
===============================

Definition:
-----------
Command Design Pattern converts a request into an object.

Instead of directly calling methods on objects,
we encapsulate the request/action inside a command object.

This helps in:
- Decoupling sender and receiver
- Adding undo/redo support
- Extending functionality easily
- Following Open/Closed Principle


---------------------------------------------------
REAL WORLD EXAMPLE
---------------------------------------------------

Remote Control:
---------------
Button Press ---> Command ---> Device Action

Example:
- Remote does NOT know how Light works
- Remote only knows:
      execute()
      undo()

So we can add:
- Light
- Fan
- AC
- TV
without modifying RemoteControl.


---------------------------------------------------
PATTERN PARTICIPANTS
---------------------------------------------------

1. Client
----------
Creates command objects and assigns them.

In our code:
    Main class


2. Invoker
-----------
Triggers commands.

In our code:
    RemoteControl

It does NOT know actual business logic.


3. Command Interface
--------------------
Common interface for all commands.

In our code:
    interface Command

Methods:
    execute()
    undo()


4. Concrete Commands
--------------------
Actual command implementations.

In our code:
    LightCommand
    FanCommand

These classes connect:
    Invoker ---> Receiver


5. Receiver
------------
Actual object performing work.

In our code:
    Light
    Fan


---------------------------------------------------
FLOW OF EXECUTION
---------------------------------------------------

Main
  ↓
RemoteControl.pressButton()
  ↓
Command.execute()
  ↓
Receiver action happens


Example:
---------
obj.pressButton(0)

RemoteControl
    ↓
LightCommand.execute()
    ↓
Light.on()


---------------------------------------------------
ADVANTAGES
---------------------------------------------------

1. Decouples sender and receiver

2. Easy to extend
   Add new commands without modifying existing code

3. Undo/Redo support


---------------------------------------------------
OPEN CLOSED PRINCIPLE
---------------------------------------------------

Open for extension
Closed for modification

We can add:
- TVCommand
- ACCommand
- MusicCommand

without changing RemoteControl.


---------------------------------------------------
CURRENT DESIGN IN THIS CODE
---------------------------------------------------

Invoker:
    RemoteControl

Receiver:
    Light, Fan

Command Interface:
    Command

Concrete Commands:
    LightCommand
    FanCommand

Client:
    Main





---------------------------------------------------
INTERVIEW ONE-LINER
---------------------------------------------------

Command Pattern encapsulates a request as an object,
thereby allowing parameterization of clients with
queues, requests, and operations.

*/

import java.util.*;

interface Appliances {

	public void on();
	public void off();
}


class Light implements Appliances {

	@Override
	public void on() {
		System.out.println("Light is on now.");
	}

	@Override
	public void off() {
		System.out.println("light is off now.");
	}
}

class Fan implements Appliances {

	@Override
	public void on() {
		System.out.println("Fan is on now.");
	}

	@Override
	public void off() {
		System.out.println("Fan is off now.");
	}
}




interface Command {
	public void execute();
	public void undo();
}

class LightCommand implements Command {

	private Light light;

	public LightCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}

	@Override
	public void undo() {
		light.off();
	}
}

class FanCommand implements Command {

	private Fan fan;

	public FanCommand(Fan fan) {
		this.fan = fan;
	}

	@Override
	public void execute() {
		fan.on();
	}

	@Override
	public void undo() {
		fan.off();
	}
}


class RemoteControl {

	private List<Command> commands = new ArrayList<>();

	private List<Boolean> pressed = new ArrayList<>();

	void addCommand(Command cmd) {
		commands.add(cmd);
		pressed.add(false);
	}

	void setCommand(int ind, Command cmd) {
		if (ind < 0 || ind >= commands.size()) {
			System.out.println("Invalid button index.");
			return;
		}
		commands.set(ind, cmd);
		pressed.set(ind, false);
	}

	void pressButton(int ind) {
		if (ind < 0 || ind >= commands.size()) {
			System.out.println("Invalid button index.");
			return;
		}
		Command command = commands.get(ind);
		if (!pressed.get(ind)) {
			command.execute();
			pressed.set(ind, true);

		} else {
			command.undo();
			pressed.set(ind, false);
		}
	}

}

public class Main
{
	public static void main(String[] args) {
		RemoteControl obj = new RemoteControl();
		obj.addCommand(new LightCommand(new Light()));
		obj.pressButton(0);
		obj.pressButton(2);
	}
}
