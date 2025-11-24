package org.firstinspires.ftc.teamcode.OpModes;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.NewYear.Chassis;

/**
 * Description of BasicTeleop.
 *
 * @author Dennis O'Brien
 * @date 11/21/2025
 */
@TeleOp(name="RotateTeleop", group="Demo")
public class RotateTeleop extends OpMode
{
    Chassis ch;
    IMU imu;

    boolean moveIt = false;

    double current, target;
    double yButton = 0;
    double xButton = 90;
    double aButton = 180;
    double bButton = 270;

    @SuppressLint("DefaultLocale")
    @Override
    public void init() {
        telemetry.addLine("Status: Initialized");
        telemetry.update();

        ch = new Chassis(hardwareMap);

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        imu.resetYaw();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void loop() {
        current = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        telemetry.addLine(String.format("Current: %5.1f\n",current));
        if (!moveIt) {
            telemetry.addLine(String.format("Y: angle = %5.1f",yButton));
            telemetry.addLine(String.format("X: angle = %5.1f",xButton));
            telemetry.addLine(String.format("A: angle = %5.1f",aButton));
            telemetry.addLine(String.format("B: angle = %5.1f",bButton));
            telemetry.update();
        }

        if (gamepad1.start) {
            imu.resetYaw();
            current = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            telemetry.addLine(String.format("Reset Angle: %5.1f",current));
            telemetry.update();
        }

        if (gamepad1.xWasReleased()) {
            target = xButton;
            moveIt = true;
        } else if (gamepad1.yWasReleased()) {
            target = yButton;
            moveIt = true;
        } else if (gamepad1.bWasReleased()) {
            target = bButton;
            moveIt = true;
        } else if (gamepad1.aWasReleased()) {
            target = aButton;
            moveIt = true;
        }

        if (moveIt) {
            rotateTo(target);
            current = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            if (current<0) current += 360;
            telemetry.update();
            moveIt = false;
        }

        ch.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {}

    private void rotateTo(double targetAngle) {
        double Kp = 0.2;  // Proportional gain (tune this)
        double Kd = 0.005;  // derivative gain
        double minPower = 0.3;
        double maxPower = 0.5;
        double tolerance = 2.0;// degrees
        double lastError = 0;
        double derivative;
        double currentAngle, error, turnPower;

        long lastTime = System.nanoTime();

        while (true) {
            currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

            error = targetAngle - currentAngle;
            error = (error + 540) % 360 - 180; // Wrap error to [-180, 180] range

            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1e9;
            lastTime = now;

            derivative = (error - lastError) / deltaTime;
            lastError = error;

            if (Math.abs(error) < tolerance) break;

            turnPower = Kp * error + Kd * derivative;

            // Enforce minimum power
            if (Math.abs(turnPower) < minPower) {
                turnPower = Math.signum(turnPower) * minPower;
            }
            // Clamp maximum power
            turnPower = Math.max(-maxPower, Math.min(maxPower, turnPower));

            ch.frontLeftDrive.setPower(-turnPower);
            ch.backLeftDrive.setPower(-turnPower);
            ch.frontRightDrive.setPower(turnPower);
            ch.backRightDrive.setPower(turnPower);
        }

        ch.stopMotors();
    }
}
