package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.*;

/**
 * visionCoding
 */
public class visionCoding {
    public static final Integer forceOff = 1;
    public static final Integer forceBlink = 2;
    public static final Integer forceOn = 3;
  
    public static final Integer VisionProccesing = 0;
    public static final Integer DrivingCamera = 1;
  
    public static final Integer PIPMain = 1;
    public static final Integer sideBySide = 0;
    public static final Integer PIPSec = 2;

    public static final Integer mainPipe = 0;
    public static final Integer rightPipe = 2;
    public static final Integer leftPipe = 1;
    
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public static NetworkTableEntry tx = table.getEntry("tx");
    public static NetworkTableEntry tv = table.getEntry("tv");
    public static NetworkTableEntry ta = table.getEntry("ta");
    public static NetworkTableEntry ts = table.getEntry("ts");
    public static NetworkTableEntry tlong = table.getEntry("tlong");
    public static NetworkTableEntry tshort = table.getEntry("tshort");
  
    public static NetworkTableEntry ledMode = table.getEntry("ledMode");
    public static NetworkTableEntry camMode = table.getEntry("camMode");
    public static NetworkTableEntry stream = table.getEntry("stream");
    public static NetworkTableEntry pipeline = table.getEntry("pipeline");

    public static ShuffleboardTab Limit = Shuffleboard.getTab("Limit");
    public static NetworkTableEntry dashxOff = 
        Limit.add("xOffset",0.0)
        .getEntry();
    public static NetworkTableEntry dashTarget =
        Limit.add("Target",false)
        .getEntry();
    public static final double visionSpeed = .666;
    public static final double turnval = .4;


    public static void visionMain(){
        shuffleBoardValues.putShuffleBoardValues();

         double offsetx =  tx.getDouble(0.0);
         int intTarget = (int)tv.getDouble(0.0);
         boolean target= false;
        if(intTarget==1){target = true; }
        dashTarget.setBoolean(target);
        dashxOff.setDouble(offsetx);

        pipeline.setNumber(mainPipe);
        ledMode.setNumber(forceOn);
        camMode.setNumber(VisionProccesing);

        if(intTarget != 0){
            System.out.println(visionSpeed);
            if(offsetx>4){
                Robot.drive.arcadeDrive(visionSpeed, turnval);
            }
            else if(offsetx<-4){
                Robot.drive.arcadeDrive(visionSpeed, -turnval);
            }
            else{
                Robot.drive.arcadeDrive(visionSpeed, 0);
            }
        }
        else {
            Robot.drive.arcadeDrive(-Robot.ps4.getRawAxis(ButtonAssignments.DrivingForwardAxis), ButtonAssignments.DrivingTurningMultiplier * Robot.ps4.getRawAxis(ButtonAssignments.DrivingTurningAxis));
        }
    }
    
    public static void visionLeft(){
        shuffleBoardValues.putShuffleBoardValues();

        double offsetx =  tx.getDouble(0.0);
        int intTarget = (int)tv.getDouble(0.0);
        boolean target= false;
       if(intTarget==1){target = true; }
       dashTarget.setBoolean(target);
       dashxOff.setDouble(offsetx);

        pipeline.setNumber(leftPipe);
        ledMode.setNumber(forceOn);
        camMode.setNumber(VisionProccesing);
        intTarget = (int) tv.getDouble(0.0);
        
        if (intTarget != 0) {
            if (offsetx > 1.5) {
                Robot.drive.arcadeDrive(visionSpeed, turnval);
            } else if (offsetx < -1.5) {
                Robot.drive.arcadeDrive(visionSpeed, -turnval);
            } else {
                Robot.drive.arcadeDrive(visionSpeed, 0);
            }
        } else {
            Robot.drive.arcadeDrive(-Robot.ps4.getRawAxis(ButtonAssignments.DrivingForwardAxis), ButtonAssignments.DrivingTurningMultiplier * Robot.ps4.getRawAxis(ButtonAssignments.DrivingTurningAxis));
        }
    }

    public static void visionRight(){
        shuffleBoardValues.putShuffleBoardValues();

        double offsetx =  tx.getDouble(0.0);
        int intTarget = (int)tv.getDouble(0.0);
        boolean target= false;
       if(intTarget==1){target = true; }
       dashTarget.setBoolean(target);
       dashxOff.setDouble(offsetx);

        pipeline.setNumber(rightPipe);
        ledMode.setNumber(forceOn);
        camMode.setNumber(VisionProccesing);
        intTarget = (int) tv.getDouble(0.0);
        
        if (intTarget != 0) {
            if (offsetx > 1.5) {
                Robot.drive.arcadeDrive(.2, turnval);
            } else if (offsetx < -1.5) {
                Robot.drive.arcadeDrive(.2, -turnval);
            } else {
                Robot.drive.arcadeDrive(.2, 0);
            }
        } else {
            Robot.drive.arcadeDrive(-Robot.ps4.getRawAxis(ButtonAssignments.DrivingForwardAxis), ButtonAssignments.DrivingTurningMultiplier * Robot.ps4.getRawAxis(ButtonAssignments.DrivingTurningAxis));
        }
    }
    

}