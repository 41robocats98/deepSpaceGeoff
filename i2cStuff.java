package frc.robot;

/**
 * i2cStuff
 */
import edu.wpi.first.wpilibj.I2C;

public class i2cStuff {

    private static int mLastSend;//creating an integer

    public static I2C mI2C = new I2C(I2C.Port.kOnboard, 8);//i2c using the onboard i2c and its talking to address 8

    public static void sendData(int data)//new function, needs to have a data point to send. 
    {
        mI2C.write(1, data);
        System.out.println("sending");
        System.out.println(data);
    }

    public static void sendI2C(){
        if(Robot.mData != mLastSend && habSequence.gameIsOver == false){
                sendData(Robot.mData);
            }
    
            mLastSend = Robot.mData;
    
      }
    
}