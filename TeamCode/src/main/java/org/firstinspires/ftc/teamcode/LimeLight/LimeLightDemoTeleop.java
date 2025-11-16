package org.firstinspires.ftc.teamcode.LimeLight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewYear.Chassis;

@TeleOp(name="LimeLightDemoTeleop", group="Demo")
@Disabled
public class LimeLightDemoTeleop extends OpMode {

    Limelight3A limelight;

    Chassis ch;

    @Override
    public void init() {

        ch = new Chassis(hardwareMap);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);
        /*
         * Starts polling for data.
         */
        limelight.start();
    }

    @Override
    public void init_loop() {
//        pinpoint.odo.update();
//        pinpoint.odo.resetPosAndIMU();
//        pinpoint.odo.recalibrateIMU();
//
//        telemetry.addData("Initial X", "%.2f", pinpoint.odo.getPosX(DistanceUnit.INCH));
//        telemetry.addData("Initial Y", "%.2f", pinpoint.odo.getPosY(DistanceUnit.INCH));
//        telemetry.addData("Initial Heading (deg)", "%.1f", pinpoint.odo.getHeading(AngleUnit.DEGREES));
//        telemetry.addData("Status", pinpoint.odo.getDeviceStatus());
//        telemetry.update();
    }

    @Override
    public void start() {
        // Reset the position one more time when starting
    }

    @Override
    public void loop() {

        ch.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Limelight Stuff
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
    }
}