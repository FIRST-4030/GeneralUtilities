## TeamCode Module

This module contains a group of generic utilities that span all years.
<p>
This repository aso contains code snippets that can be copied and
pasted into future projects to help expedite the creation of a project
at the beginning od a school year</p>

## Blackboard
This set of classes show how to transfer information between an autonomous 
and teleop opmodes. Classes include,
<ul>
<li><i>Blackboard</i> - common class used to store data to be shared</li>
</ul>

## LimeLight

## New Year
### Chassis
This class enforces the creation of a common set of drive motors and their
associated properties. Methods include,
<ul>
<li><i>drive</i> - takes the inputs from the joysticks to move the robot around</li>
<li><i>setSpeed</i> - changes the maximum speed tha the motors can run. Reduce this 
value when setting up for an outreach event.</li>
<li><i>setPower</i> - sets the maximum power of all motors</li>
</ul>

## Utilities
### Gamepad_F310_Buttons

### ServoTester
This opmode allows you the ability to set a starting point for a servo 
and then increment its movement up/down by 5%. Merely, change the 
name of the servo (<b>DEVICE_NAME</b>) at the top of the file before beginning

### WheelTest
This opmode will drive each individual wheel forward for 2 seconds and then 
in reverse for 2 seconds. This is a great tool to ensure that you have wired up
the motors to the proper pors on the ControlHub.
