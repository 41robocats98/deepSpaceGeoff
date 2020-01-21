package frc.robot;

import edu.wpi.first.wpilibj.Timer;

/**
 * hab3Sequence
 */
public class habSequence {
    
    public static int step = 0;
    public static long prevMillis;
    public static boolean gameIsOver = false;
    public static void habClimb(int crutchMillisDown, int footWinchEnc,int crutchMillisUp){
        i2cStuff.sendI2C();
        shuffleBoardValues.putShuffleBoardValues();
        switch(step){
            case 0:
                Robot.mData=6;
                Timer.delay(.25);
                step = 1;
            break;
            case 1:
                if (Robot.footEnc.get()> footWinchEnc){
                    Robot.footWinch.set(-1);
                }
                else{
                    Robot.footWinch.set(-.3);
                    step = 2;                                                            
                }
            break;
            
            case 2:
                if(Robot.proxFront.get() == false){
                    Robot.footWheel.set(1);
                    Robot.drive.arcadeDrive(0.45, 0);
                }
                else{
                    Robot.footWheel.set(0);
                    Robot.drive.arcadeDrive(0, 0);
                    step = 3;
                    prevMillis = System.currentTimeMillis();
                }                
            break;

            case 3:
                if(System.currentTimeMillis() < prevMillis+crutchMillisDown || Robot.crutchLimit.get() == true){
                    Robot.crutchLift.set(-1);
                }
                else{
                    Robot.crutchLift.set(0);
                    step = 4;
                }
            break;

            case 4:
                if(Robot.footLimit.get() == true){
                    Robot.footWinch.set(1);
                }
                else{
                    Robot.footWinch.set(0);                    
                    step = 5;
                }
            break;

            case 5:
                if(Robot.proxBack.get() == false){
                    Robot.drive.arcadeDrive(.5, 0);
                }
                else{
                    Robot.drive.arcadeDrive(0, 0);
                    step = 6;
                    prevMillis = System.currentTimeMillis();
                }
            break;       
            
            case 6:
                if(System.currentTimeMillis()<prevMillis +crutchMillisUp || Robot.crutchLimit.get() ==true){
                    Robot.crutchLift.set(1);
                }
                else{
                    Robot.crutchLift.set(0);
                    prevMillis = System.currentTimeMillis();
                    step = 7;
                }
            break;

            case 7:
                Robot.mData=2;
                i2cStuff.sendI2C();
                gameIsOver = true;
                if(System.currentTimeMillis()<prevMillis+250){
                    Robot.drive.arcadeDrive(.4, 0);
                }
                else{
                    Robot.drive.arcadeDrive(0, 0);
                    
                }
            break;                                                                                                                                                                                         
        }     
    }
}