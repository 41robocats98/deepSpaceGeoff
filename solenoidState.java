package frc.robot;

/**
 * solenoidState
 */

public class solenoidState{
    
    public static void headIn(){
       Robot.head1.set(true);
       Robot.head2.set(false);
    }
    public static void headOut(){
        Robot.head1.set(false);
        Robot.head2.set(true);
    }

    public static void headOpen(){
        Robot.brake1.set(true);
        Robot.brake2.set(false);
    }
    public static void headClose(){
        Robot.brake1.set(false);
        Robot.brake2.set(true);
    }
    
}