package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BuildConfig;
import org.firstinspires.ftc.teamcode.ControlHub;

import java.io.FileNotFoundException;
import java.io.IOException;

// DO NOT disable this opmode
@TeleOp(name="ControlHubTeleop")
public class ControlHubTeleop extends OpMode {

    ControlHub controlHub;
    String[] controlHubs = { "Competition", "Demo"};
    String operation= "";

    public void init() {
        controlHub = new ControlHub();
        telemetry.addData("Compiled on:", BuildConfig.COMPILATION_DATE);
        telemetry.update();
    }

    public void loop() {
        boolean completed;
        telemetry.addLine("Initialize ControlHub:");
        telemetry.addLine("   A   - Create ControlHub file");
        telemetry.addLine("   B   - Delete ControlHub file");
        telemetry.addLine("   X   - Get ControlHub file name");
        telemetry.addLine("   Y   - Get Current ControlHub");
        telemetry.addLine(String.format("   LB - Define ControlHub (%s)",controlHubs[0]));
        telemetry.addLine(String.format("   RB - Define ControlHub (%s)",controlHubs[1]));

        if (gamepad1.aWasReleased()) {
            try {
                completed = controlHub.createControlHubFile();
                if (completed) {
                    operation = "File Created";
                } else {
                    operation = "File NOT Created";
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (gamepad1.bWasReleased()) {
            completed = controlHub.deleteControlHubFile();
            if (completed) {
                operation = "File Deleted";
            } else {
                operation = "File NOT Deleted";
            }

        } else if (gamepad1.xWasReleased()) {
            operation = "File=";
            operation = operation + controlHub.getControlHubFileName();

        } else if (gamepad1.yWasReleased()) {
            try {
                operation = "ControlHub=" + controlHub.getControlHub();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (gamepad1.leftBumperWasReleased()) {
            try {
                controlHub.intitializeControlHub(controlHubs[0]);
                operation = "ControlHub=" + controlHub.getControlHub();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (gamepad1.rightBumperWasReleased()) {
            try {
                controlHub.intitializeControlHub(controlHubs[1]);
                operation = "ControlHub=" + controlHub.getControlHub();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        telemetry.addData("Operation:",operation);
        telemetry.update();
    }
}