package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.generalUtilities.Blackboard;

/**
 * Description of BlackboardAuto.
 *
 * @author Edson James
 * @date 11/14/2025
 */
@Autonomous(name="BlackboardAuto", group="Blackboard")
public class BlackboardAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        while (opModeInInit()) {
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Alliance", Blackboard.getAlliance());
            telemetry.addLine("Press X to set alliance to BLUE");
            telemetry.addLine("Press B to set alliance to RED");
            telemetry.update();

            if (gamepad1.xWasPressed()) {
                Blackboard.setAlliance(Blackboard.Alliance.BLUE);
            } else if (gamepad1.bWasPressed()) {
                Blackboard.setAlliance(Blackboard.Alliance.RED);
            }
        }

        // Wait for the game to start (driver presses START)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Alliance", Blackboard.getAlliance());
            telemetry.update();
            sleep(3000);
        }
    }
}
