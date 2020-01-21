package frc.robot;

/**
 * climbingSequence
 */
public class climbingSequence {
    public static Integer cases = 1;
    public static Long prevMillis = 0L;
    public static boolean endGameBoolean = false;
    public static Long resetMillis = 0L;
    public static Boolean boolEndGame = false;

    public static void endGame() {

        shuffleBoardValues.putShuffleBoardValues();
        if (endGameBoolean == false) {
            switch (cases) {
            // wait 1 second to prevent accidentily pushing the button
            case (90):
                if (prevMillis + 1000 < System.currentTimeMillis()) {
                    cases = 1;
                }
                break;

            case (1):
                if (Robot.winchEnc.getPosition() < rocketSelector.hookInHeight) {
                    // putting elevator up
                    // release the footWinch
                    // opening brake
                    Robot.winch.set(.5);
                    Robot.footWinch.set(-0.5);
                } else {
                    // stop putting up elevator
                    boolEndGame = true;
                    solenoidState.headIn();
                    Robot.winch.set(0);
                    Robot.footWinch.set(0);
                    cases = 2;
                    prevMillis = System.currentTimeMillis();
                }
                break;

            case (2):
                if (prevMillis + 500 > System.currentTimeMillis()) {
                    // lock down the footWinch
                } else {
                    cases = 3;
                }
                break;

            case (3):
                if (Robot.winchEnc.getPosition() > rocketSelector.footDownHeight) {
                    Robot.winch.set(-0.5);
                    Robot.footWinch.set(-.7);

                    // put elevator down till height
                    // open back brake
                } else {
                    cases = 10;
                    // stop putting elevator down
                    // close back brake
                    Robot.footWinch.set(0);
                    Robot.winch.set(0);
                    prevMillis = System.currentTimeMillis();
                }
                break;

            case (10):
                if (System.currentTimeMillis() < prevMillis + 2000 && Robot.crutchLimit.get() == true) {
                    Robot.crutchLift.set(-1);
                    // crutch down
                } else {
                    // Robot.crutchLift.set(-1);
                    cases = 4;
                }
                break;

            case (4):
                if (Robot.proxFront.get() == false) {
                    // driving forward with foot wheel till the front prox sensor triggers
                    Robot.footWheel.set(.3);
                    Robot.drive.arcadeDrive(.5, 0);
                } else {
                    // stop driving
                    Robot.footWheel.set(0);
                    cases = 5;
                }

                break;

            case (5):
                if (Robot.footLimit.get() == true) {
                    if (rocketSelector.liftingUpHeight < Robot.winchEnc.getPosition()) {
                        Robot.winch.set(1);
                    } else {
                        Robot.winch.set(0);
                    }
                    // lifting up foot

                    Robot.footWinch.set(.5);
                } else {
                    cases = 15;
                    // stop lifting foot
                    Robot.footWinch.set(0);
                }
                break;

            case (15):
                if (Robot.winchEnc.getPosition() > rocketSelector.footDownHeight) {
                    Robot.winch.set(-1);
                } else {
                    Robot.winch.set(0);
                    cases = 6;
                }
                break;

            case (6):
                if (Robot.proxBack.get() == false) {
                    // drive arcade drive forward till back prox sensor
                    Robot.drive.arcadeDrive(0.6, 0);
                } else {
                    // stop driving arcade
                    Robot.drive.arcadeDrive(0, 0);
                    cases = 7;
                    prevMillis = System.currentTimeMillis();
                }
                break;

            case (7):
                if (System.currentTimeMillis() < prevMillis + 2000 && Robot.crutchLimit.get() == true) {
                    // lift up rear post
                    Robot.crutchLift.set(1);
                } else {
                    // stop lifting up rear post
                    Robot.crutchLift.set(0);
                    cases = 8;
                    prevMillis = System.currentTimeMillis();
                }
                break;

            case (8):
                if (System.currentTimeMillis() < prevMillis + 250) {

                    Robot.drive.arcadeDrive(0.3, 0);
                } else {
                    endGameBoolean = true;
                }
                break;
            default:
            }
        }
    }

}