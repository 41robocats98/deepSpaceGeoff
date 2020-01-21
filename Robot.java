package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Robot extends TimedRobot {

  public static Joystick ps4 = new Joystick(0);
  public static Joystick logi = new Joystick(1);

  public static WPI_TalonSRX crutchLift = new WPI_TalonSRX(1);
  public static WPI_TalonSRX hawk = new WPI_TalonSRX(2);
  public static WPI_TalonSRX ballActuator = new WPI_TalonSRX(3);
  public static WPI_TalonSRX footWheel = new WPI_TalonSRX(5);
  public static WPI_TalonSRX footWinch = new WPI_TalonSRX(4);
  public static Encoder footEnc = new Encoder(8, 9);

  public static CANSparkMax frontRight = new CANSparkMax(4,MotorType.kBrushless);
  public static CANEncoder frontRightEnc = new CANEncoder(frontRight);
  public static CANSparkMax backRight = new CANSparkMax(5,MotorType.kBrushless);
  public static CANEncoder backRightEnc = new CANEncoder(backRight);

  public static CANSparkMax frontLeft = new CANSparkMax(1,MotorType.kBrushless);
  public static CANEncoder frontLeftEnc = new CANEncoder(frontLeft);
  public static CANSparkMax backLeft = new CANSparkMax(2,MotorType.kBrushless);  
  public static CANEncoder backLeftEnc = new CANEncoder(backLeft);

  public static SpeedControllerGroup rightArcade = new SpeedControllerGroup(frontRight, backRight);
  public static SpeedControllerGroup leftArcade = new SpeedControllerGroup(frontLeft, backLeft);

  public static DifferentialDrive drive = new DifferentialDrive(leftArcade, rightArcade);

  public static CANSparkMax winch = new CANSparkMax(3,MotorType.kBrushless);
  public static CANEncoder winchEnc = new CANEncoder(winch);
  public static CANPIDController winchPID = new CANPIDController(winch);

  public static DigitalInput proxFront = new DigitalInput(3);
  public static DigitalInput proxBack = new DigitalInput(2);
  public static DigitalInput crutchLimit = new DigitalInput(0);
  public static DigitalInput footLimit = new DigitalInput(4);
  public static DigitalInput winchLimit = new DigitalInput(5);
  public static DigitalInput headLimit = new DigitalInput(6);


  public static Solenoid head1 = new Solenoid(6);
  public static Solenoid head2 = new Solenoid(7);
  public static Solenoid brake1 = new Solenoid(4);
  public static Solenoid brake2 = new Solenoid(5);

  double winchCurrentPos;
  public static int mData;

  public static boolean headState = false;


  @Override

  public void robotInit() {
    footWheel.setInverted(false);
    crutchLift.setInverted(true);
    footWinch.setInverted(false);
    ballActuator.setInverted(true);
    double ramprate = .25;
    backLeft.setOpenLoopRampRate(ramprate);
    frontLeft.setOpenLoopRampRate(ramprate);
    backRight.setOpenLoopRampRate(ramprate);
    frontRight.setOpenLoopRampRate(ramprate);
    backLeft.setInverted(false);
    frontLeft.setInverted(false);
    backRight.setInverted(false);
    frontRight.setInverted(false);
    solenoidState.headIn();
    solenoidState.headOpen();
    climbingSequence.cases = 1;
    winch.setClosedLoopRampRate(.25);
    winchEnc.setPosition(0);
    winchEnc.setPositionConversionFactor(1);
  }

  @Override
  public void robotPeriodic() {
    
    
    if(logi.getRawAxis(2)>0){
      mData = 7;
    }
    else{
      mData = 8;
    }
    if (footLimit.get()==false){
      footEnc.reset();
    }
    i2cStuff.sendI2C();
    gyroCorrection.antiTip();
    i2cStuff.sendI2C();
    System.out.println(gyroCorrection.imu.getPitch());
    shuffleBoardValues.putShuffleBoardValues();
    drive.arcadeDrive(-1*ps4.getRawAxis(ButtonAssignments.DrivingForwardAxis), ButtonAssignments.DrivingTurningMultiplier * ps4.getRawAxis(ButtonAssignments.DrivingTurningAxis));
    
    while(ps4.getRawButton(ButtonAssignments.visionMain)){
      visionCoding.visionMain();
    }

    while(ps4.getRawButton(ButtonAssignments.visionRight)){
      visionCoding.visionRight();

    }

    while(ps4.getRawButton(ButtonAssignments.visionLeft)){
      visionCoding.visionLeft();
    }

    if(ps4.getRawButton(ButtonAssignments.visionLeft) == false &&
    ps4.getRawButton(ButtonAssignments.visionMain) == false &&
    ps4.getRawButton(ButtonAssignments.visionRight) == false){
      visionCoding.ledMode.setNumber(visionCoding.forceOff);
      visionCoding.camMode.setNumber(visionCoding.DrivingCamera);
    }

    while(ps4.getRawButton(ButtonAssignments.climbButton)){
      climbingSequence.resetMillis = System.currentTimeMillis();
      climbingSequence.endGame();
    }
    if(ps4.getRawButtonReleased(ButtonAssignments.climbButton)){
      climbingSequence.resetMillis = System.currentTimeMillis();
      
    }

    if(ps4.getRawButtonPressed(ButtonAssignments.climbButton) && //check if the climb button is false
    climbingSequence.cases != 90 && // check if the case is not 90
    climbingSequence.resetMillis + 5000<System.currentTimeMillis()){//check to see if the time has past 5 seconds
      climbingSequence.cases = 90;

      climbingSequence.boolEndGame = false;

    }
//new Climbing Sequences
    while(ps4.getRawButton(ButtonAssignments.hab2Button)){
      habSequence.habClimb(250, -2400,250);
    }
    while(ps4.getRawButton(ButtonAssignments.hab3Button)){
      habSequence.habClimb(1250, -5800,2750);
    }
//moving the foot winch manually
    if(ps4.getRawButton(ButtonAssignments.footWinchButton)){
      footWinch.set(1);
    }
    else if(ps4.getRawButton(ButtonAssignments.shoeHookButton)){
      footWinch.set(-1);
    }
    else{
      footWinch.set(0);
    }

    //Manual Winch
    if(logi.getRawAxis(ButtonAssignments.winchAxis)>.3){
      winch.set(logi.getRawAxis(ButtonAssignments.winchAxis));
    }
    else if(logi.getRawAxis(ButtonAssignments.winchAxis)<-.3){
      winch.set(logi.getRawAxis(ButtonAssignments.winchAxis));
    }
    else{
      winch.set(0);
    }

    //Automatic Winch
    //Rocket 1 Cargo
    while(logi.getRawButton(ButtonAssignments.rocket1)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)){
        rocketSelector.elevatorValue(rocketSelector.rocket1CargoHeight);
    }
    //Rocket 1 Hatch
    while(logi.getRawButton(ButtonAssignments.rocket1)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)==false){
        rocketSelector.elevatorValue(rocketSelector.rocket1HatchHeight);

    }
    //Rocket 2 Cargo
    while(logi.getRawButton(ButtonAssignments.rocket2)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)){
        solenoidState.headOut();
        rocketSelector.elevatorValue(rocketSelector.rocket2CargoHeight);
    }
    //Rocket 2 Hatch
    while(logi.getRawButton(ButtonAssignments.rocket2)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)==false){
        rocketSelector.elevatorValue(rocketSelector.rocket2HatchHeight);
    }
    //Rocket 3 Cargo
    while(logi.getRawButton(ButtonAssignments.rocket3)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)){
        solenoidState.headOut();
        rocketSelector.elevatorValue(rocketSelector.rocket3CargoHeight);
    }
    //Rocket 3 Hatch
    while(logi.getRawButton(ButtonAssignments.rocket3)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)==false){
        solenoidState.headOut();
        rocketSelector.elevatorValue(rocketSelector.rocket3HatchHeight);
    }
    //CargoShip Cargo
    while(logi.getRawButton(ButtonAssignments.cargo)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)){
        rocketSelector.elevatorValue(rocketSelector.cargoShipCargo);
    }
    //CargoShip Hatch
    while(logi.getRawButton(ButtonAssignments.cargo)&&
      logi.getRawButton(ButtonAssignments.rocketToggle)==false){
        rocketSelector.elevatorValue(rocketSelector.cargoShipHatch);
    }
    while(logi.getRawButton(ButtonAssignments.pickUpHatch)&&
    logi.getRawButton(ButtonAssignments.rocketToggle)){
      rocketSelector.elevatorValue(rocketSelector.zeroHeight);
    }

    if(logi.getRawButtonPressed(ButtonAssignments.pickUpHatch)&&
    logi.getRawButton(ButtonAssignments.rocketToggle) == false){
      winchCurrentPos = winchEnc.getPosition();
    }

    while(logi.getRawButton(ButtonAssignments.pickUpHatch)&&
    logi.getRawButton(ButtonAssignments.rocketToggle) == false){
      rocketSelector.elevatorValue(winchCurrentPos+10);
    }

    
    //actuating the head
    if(winchEnc.getPosition() >= 90 && winchEnc.getPosition() <= 120 
      || logi.getRawButton(ButtonAssignments.hookActuator)){
      solenoidState.headOut();
    }
    else if(winchEnc.getPosition() < 90 || winchEnc.getPosition()>120){
      solenoidState.headIn();
    }

    //opening the hatch
    if(logi.getRawButton(ButtonAssignments.headPiston)){
      solenoidState.headOpen();
    }
    else if(headLimit.get() == true){
      solenoidState.headClose();
    }
    else{
      solenoidState.headOpen();
    }
    
    //Getting the ball IN
    if(logi.getRawButtonPressed(ButtonAssignments.ballIn)){
      ballActuator.set(1);
    }
    if(logi.getRawButtonReleased(ButtonAssignments.ballIn)){
      ballActuator.set(0);
    }

    //Spitting the ball OUT
    if(logi.getRawButtonPressed(ButtonAssignments.ballOut)){
      ballActuator.set(-.8);
    }
    if(logi.getRawButtonReleased(ButtonAssignments.ballOut)){
      ballActuator.set(0);
    }

    //reseting the encode for the limit switch
    if(winchLimit.get()==true){
      ///if its below the zero it turns off the winch to prevent slack
      if(winchEnc.getPosition()<0){

        winch.set(0);
      }
      //setting the position to 0 
      winchEnc.setPosition(0);
      //preventing the pid from going below
      winchPID.setOutputRange(0, 1);
    }
    else{
      //setting the pid to normal
      winchPID.setOutputRange(-1, 1);
    }

    //manual ncrutch controls
    if(ps4.getRawButton(5)){
      crutchLift.set(1);
    }
    else if(ps4.getRawButton(6)){
      crutchLift.set(-.5);
    }
    else{
      crutchLift.set(0);
    }

    //manual foot wheel controls
    if(ps4.getRawButton(4)){
      footWheel.set(1);
    }




    else{
      footWheel.set(0);
    }
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testPeriodic() {
  }
}
