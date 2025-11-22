package org.firstinspires.ftc.teamcode.LimeLight;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.NewYear.Chassis;

/**
 * Description of LimeLightDemoTeleop.
 *
 * @author Dennis O'Brien
 * @date 11/17/2025
 */
@TeleOp(name="LimeLightDemoTeleop", group="Demo")
public class LimeLightDemoTeleop extends OpMode {

    double target_height;
    double camera_height  = 13.1875;  // 13 3/16
    double camera_angle   = 0.1;

    Chassis ch;
    private Limelight3A limelight;
    double goalRange;
    int pipeline = 1;
    String location = "";

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void init() {

        ch = new Chassis(hardwareMap);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(pipeline);
        limelight.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void init_loop() {
        if (gamepad1.xWasPressed()) {
            location = "Home";
            target_height = 25;
        } else if (gamepad1.bWasPressed()) {
            location = "8628";
            target_height = 29.5;
        }

        telemetry.addLine(String.format("Target Height = %5.2f, Camera Height = %5.2f",target_height,camera_height));
        telemetry.addLine(String.format("Camera Angle = %4.1f, Pipeline = %d\n",camera_angle,pipeline));
        telemetry.addLine("Press X for Home, and B for 8628");
        telemetry.addLine(String.format("Location: %s",location));
        telemetry.update();
     }

    @SuppressLint("DefaultLocale")
    @Override
    public void loop() {
        double tx, ty;

        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                tx = result.getTx(); // How far left or right the target is (degrees)
                ty = result.getTy(); // How far up or down the target is (degrees)

                Pose3D botpose = result.getBotpose();
                if (botpose!=null) {
                    double x = botpose.getPosition().x;
                    double y = botpose.getPosition().y;
                    goalRange = (target_height - camera_height) / (Math.tan(Math.toRadians(ty)+Math.toRadians(camera_angle)));

                    telemetry.addLine(String.format("botx = %4.2f", x));
                    telemetry.addLine(String.format("boty = %4.2f", y));
                    telemetry.addLine(String.format("denom = %6.4f",(Math.tan(Math.toRadians(ty)+Math.toRadians(camera_angle)))));

                }
                telemetry.addLine(String.format("tx = %4.2f", tx));
                telemetry.addLine(String.format("ty = %4.2f", ty));
                telemetry.addData("Range", goalRange);
                if (botpose!=null) {
                    telemetry.addData("Botpose", botpose.toString());
                }
            } else {
                telemetry.addLine("No valid result");
            }
        } else {
            telemetry.addLine("No result");
        }
        telemetry.update();

        ch.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }
}