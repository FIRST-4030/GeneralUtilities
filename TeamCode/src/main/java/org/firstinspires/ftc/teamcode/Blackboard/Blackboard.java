package org.firstinspires.ftc.teamcode.Blackboard;

/**
 * Description of Blackboard.
 *
 * @author Edson James
 * @date 11/14/2025
 */
public class Blackboard {
    public enum Alliance {
        RED,
        BLUE,
        UNKNOWN,
    }
    public static Alliance alliance = Alliance.UNKNOWN;

    public static String getAllianceAsString() {
        switch (alliance) {
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case UNKNOWN:
                return "Unknown";
        }
        return "Undefined";
    }
}
