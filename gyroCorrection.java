
package frc.robot;
import com.analog.adis16448.frc.ADIS16448_IMU;

public class gyroCorrection {
  public static final ADIS16448_IMU imu = new ADIS16448_IMU();
  

    public static void antiTip(){
        // while(imu.getPitch()<-80){
        //     Robot.drive.arcadeDrive(-0.3, 0);
        //     if(Robot.winchEnc.getPosition()>0){
        //         Robot.winch.set(-1);
        //     }
        //     else{
        //         Robot.winch.set(0);
        //     }
        // }
        // while (imu.getPitch()>-43){
        //     Robot.drive.arcadeDrive(0.3, 0);
        //     if(Robot.winchEnc.getPosition()>0){
        //         Robot.winch.set(-1);
        //     }
        //     else{
        //         Robot.winch.set(0);
        //     }
        // }

    }
    
}