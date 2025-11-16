package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Description of WheelTestAll.
 *
 * @author Phil Pettis
 * @date 9/1/2025
 */
@Autonomous(name = "Wheel Test All", group="Util")
public class WheelTestAll extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Ready to test wheels");
        telemetry.addData("Info", "Each wheel will spin forward 2s, then backward 2s");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Test Left Front wheel
            testWheel(leftFront, "Left Front");

            // Test Right Front wheel
            testWheel(rightFront, "Right Front");

            // Test Left Back wheel
            testWheel(leftBack, "Left Back");

            // Test Right Back wheel
            testWheel(rightBack, "Right Back");

            // All tests complete
            telemetry.addData("Status", "All wheel tests complete!");
            telemetry.update();
        }
    }

    private void testWheel(DcMotor motor, String wheelName) {
        if (!opModeIsActive()) return;

        // Forward for 2 seconds
        telemetry.addData("Testing", wheelName + " - Forward");
        telemetry.update();
        motor.setPower(0.5);
        sleep(2000);

        // Stop briefly
        motor.setPower(0);
        sleep(500);

        // Backward for 2 seconds
        telemetry.addData("Testing", wheelName + " - Backward");
        telemetry.update();
        motor.setPower(-0.5);
        sleep(2000);

        // Stop
        motor.setPower(0);

        // Brief pause between wheels
        telemetry.addData("Completed", wheelName);
        telemetry.update();
        sleep(1000);
    }
}