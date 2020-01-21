package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * shuffleBoardValues
 */
public class shuffleBoardValues {
    public static ShuffleboardTab elevator = Shuffleboard.getTab("Elevator");
    public static ShuffleboardTab climbing = Shuffleboard.getTab("Climbing");

    public static NetworkTableEntry shuffleElevatorEncoder = Shuffleboard.getTab("Elevator")
    .add("Elevator Encoder",1)
    .getEntry();
    public static NetworkTableEntry shuffleElevatorLimit = Shuffleboard.getTab("Elevator")
    .add("Elevator Limit",true)
    .getEntry();
    public static NetworkTableEntry shuffleLimeLightTarget = Shuffleboard.getTab("Elevator")
    .add("Target",0)
    .getEntry();
    public static NetworkTableEntry shuffleGyroPitch = Shuffleboard.getTab("Elevator")
    .add("Pitch",0)
    .getEntry();
    public static NetworkTableEntry shuffleGyroRoll = Shuffleboard.getTab("Elevator")
    .add("Roll",0)
    .getEntry();
    public static NetworkTableEntry shuffleGyroYaw = Shuffleboard.getTab("Elevator")
    .add("Yaw",0)
    .getEntry();

    public static NetworkTableEntry shuffleProxBack = Shuffleboard.getTab("Climbing")
    .add("Proximity Back",false)
    .getEntry();
    public static NetworkTableEntry shuffleProxFront = Shuffleboard.getTab("Climbing")
    .add("Proximity Front",false)
    .getEntry();
    public static NetworkTableEntry shuffleCrutchLimit = Shuffleboard.getTab("Climbing")
    .add("Crutch Limit",false)
    .getEntry();

    public static NetworkTableEntry shuffleCases = Shuffleboard.getTab("Climbing")
    .add("Cases",1)
    .getEntry();
    
    public static NetworkTableEntry shuffleClimbingElevatorValue = Shuffleboard.getTab("Climbing")
    .add("Elevator Encoder",1)
    .getEntry();

    public static NetworkTableEntry shuffleShoeLimit = Shuffleboard.getTab("Climbing")
    .add("Shoe Limit",false)
    .getEntry();

    public static NetworkTableEntry shuffleFootEncVal = Shuffleboard.getTab("Climbing")
    .add("Foot Encoder Value", 0)
    .getEntry();
    

    public static void putShuffleBoardValues(){
        shuffleElevatorEncoder.setDouble(Robot.winchEnc.getPosition());
        shuffleClimbingElevatorValue.setDouble(Robot.winchEnc.getPosition());
        shuffleProxBack.setBoolean(Robot.proxBack.get());
        shuffleProxFront.setBoolean(Robot.proxFront.get());
        shuffleCrutchLimit.setBoolean(Robot.crutchLimit.get());
        shuffleCases.setDouble(climbingSequence.cases);
        shuffleShoeLimit.setBoolean(Robot.footLimit.get());
        shuffleLimeLightTarget.setDouble(visionCoding.tv.getDouble(0.0));
        shuffleElevatorLimit.setBoolean(Robot.winchLimit.get());
        shuffleGyroPitch.setDouble(gyroCorrection.imu.getPitch());
        shuffleGyroYaw.setDouble(gyroCorrection.imu.getYaw());
        shuffleGyroRoll.setDouble(gyroCorrection.imu.getRoll());
        shuffleFootEncVal.setDouble(Robot.footEnc.get());
    }

    
}