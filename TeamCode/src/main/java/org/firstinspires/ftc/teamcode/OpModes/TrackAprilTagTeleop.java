package org.firstinspires.ftc.teamcode.OpModes;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.AprilTag;
import org.firstinspires.ftc.teamcode.NewYear.Chassis;

/**
 * Description of TrackAprilTagTeleop.
 *
 * @author Dennis O'Brien
 * @date 11/22/2025
 */
@TeleOp(name="TrackAprilTagTeleop", group="Demo")
public class TrackAprilTagTeleop extends OpMode
{
    Chassis ch;
    IMU imu;

    ElapsedTime runtime = new ElapsedTime();

    AprilTag aprilTags;

    boolean targetInView;

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

        aprilTags = new AprilTag();
        aprilTags.initAprilTag(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
        aprilTags.scanField(telemetry);
        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        runtime.reset();
        imu.resetYaw();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void loop() {

        targetInView = aprilTags.runInLoop(telemetry,false );

        ch.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Report current field info
        telemetry.addLine(String.format("Target in view: %b",targetInView));
        telemetry.addLine(String.format("Current bearing: %5.2f",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)));

        if (targetInView) {
            telemetry.addLine(String.format("April Tag Bearing: %5.2f", aprilTags.getBearing()));
        }
        telemetry.update();
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
