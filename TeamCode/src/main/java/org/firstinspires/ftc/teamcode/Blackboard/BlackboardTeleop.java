package org.firstinspires.ftc.teamcode.Blackboard;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Description of BlackboardTeleop.
 *
 * @author Edson James
 * @date 11/14/2025
 */
@TeleOp(name="BlackboardTeleop", group="Blackboard")
public class BlackboardTeleop extends OpMode
{
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Alliance", Blackboard.getAllianceAsString());
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Alliance", Blackboard.getAllianceAsString());
        telemetry.addLine("HOLD RB AND Press X to override alliance to BLUE");
        telemetry.addLine("HOLD RB AND Press B to override alliance to RED");
        telemetry.update();

        if (gamepad1.xWasPressed() && gamepad1.right_bumper) {
            Blackboard.alliance = Blackboard.Alliance.BLUE;
        } else if (gamepad1.bWasPressed() && gamepad1.right_bumper) {
            Blackboard.alliance = Blackboard.Alliance.RED;
        }
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
