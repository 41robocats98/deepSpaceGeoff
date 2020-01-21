package frc.robot;

import com.revrobotics.ControlType;

/**
 * rocketSelector
 */

public class rocketSelector {
    public static final int rocket1CargoHeight = 64;
    public static final int rocket1HatchHeight = 13;
    public static final int rocket2CargoHeight = 135;
    public static final int rocket2HatchHeight = 88;
    public static final int rocket3CargoHeight = 205;
    public static final int rocket3HatchHeight = 158;
    public static final int cargoShipCargo = 95;
    public static final int cargoShipHatch = 82;

    public static final int hookInHeight = 190;
    public static final int footDownHeight = 26;
    public static final int liftingUpHeight = 50;

    public static final int maximumHeight = 214;
    
    public static final int zeroHeight = 0;
    public static final int pickUpStation = 95;

    public static final double kP = 0.1;
    public static final double kI = 0.0;
    public static final double kD = 0.001;

    public static final double val = 1;
    
    public static void elevatorValue(double encDistance){
        // solenoidState.headOut();

        Robot.mData=3;
        i2cStuff.sendI2C();

        // if(Robot.logi.getRawButton(ButtonAssignments.hookActuator)&& climbingSequence.boolEndGame==false){
        //     solenoidState.headOut();
        //   }
        //   else{
        //     solenoidState.headIn();
        //   }
        Robot.winchPID.setOutputRange(-val,val);
        System.out.println(encDistance);
        shuffleBoardValues.putShuffleBoardValues();
        Robot.winchPID.setP(kP);
        Robot.winchPID.setI(kI);
        Robot.winchPID.setD(kD);
        Robot.drive.arcadeDrive(-Robot.ps4.getRawAxis(ButtonAssignments.DrivingForwardAxis), ButtonAssignments.DrivingTurningMultiplier * Robot.ps4.getRawAxis(ButtonAssignments.DrivingTurningAxis));
        Robot.winchPID.setReference(encDistance, ControlType.kPosition);
    }
}