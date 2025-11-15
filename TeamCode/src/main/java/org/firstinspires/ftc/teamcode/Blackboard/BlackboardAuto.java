package org.firstinspires.ftc.teamcode.Blackboard;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Description of BlackboardAuto.
 *
 * @author Edson James
 * @version 1.0
 * @date 11/14/2025
 */
@Autonomous(name="BlackboardAuto", group="Blackboard")
public class BlackboardAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        while (opModeInInit()) {
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Alliance", Blackboard.getAllianceAsString());
            telemetry.addLine("Press X to set alliance to BLUE");
            telemetry.addLine("Press B to set alliance to RED");
            telemetry.update();

            if (gamepad1.xWasPressed()) {
                Blackboard.alliance = Blackboard.Alliance.BLUE;
            } else if (gamepad1.bWasPressed()) {
                Blackboard.alliance = Blackboard.Alliance.RED;
            }
        }

        // Wait for the game to start (driver presses START)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Alliance", Blackboard.getAllianceAsString());
            telemetry.update();
            sleep(3000);
        }
    }
}
