package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewYear.Chassis;

/**
 * Description of BasicTeleop.
 *
 * @author Dennis O'Brien
 * @date 11/17/2025
 */
@TeleOp(name="BasicTeleop", group="Demo")
public class BasicTeleop extends OpMode
{
    Chassis ch;

    @Override
    public void init() {
        telemetry.addLine("Status: Initialized");
        telemetry.update();

        ch = new Chassis(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        telemetry.addLine("");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        ch.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {}
}
