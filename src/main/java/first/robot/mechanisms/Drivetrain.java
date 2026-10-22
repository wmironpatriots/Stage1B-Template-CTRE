package first.robot.mechanisms;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import first.robot.simulation.DrivetrainSim;
import first.robot.simulation.FuelSim;

import java.util.function.DoubleSupplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.drive.DifferentialDrive;
import org.wpilib.framework.RobotBase;
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.imu.OnboardIMU;
import org.wpilib.hardware.imu.OnboardIMU.MountOrientation;

public class Drivetrain implements Mechanism {

  // motor controllers and sensors would be declared here
  TalonFX leftLeader = new TalonFX(0, new CANBus(CANPort.CAN_S0));
  TalonFX leftFollower = new TalonFX(1, new CANBus(CANPort.CAN_S0));
  TalonFX rightLeader = new TalonFX(2, new CANBus(CANPort.CAN_S0));
  TalonFX rightFollower = new TalonFX(3, new CANBus(CANPort.CAN_S0));

  public final DifferentialDrive differentialDrive =
      new DifferentialDrive(leftLeader::setThrottle, rightLeader::setThrottle);
  private final OnboardIMU imu = new OnboardIMU(MountOrientation.FLAT);
  private final DrivetrainSim drivetrainSim = new DrivetrainSim(leftLeader, rightLeader);

  public Drivetrain() {
    TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    leftConfig.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    leftLeader.getConfigurator().apply(leftConfig);
    leftFollower.getConfigurator().apply(leftConfig);
    leftFollower.setControl(new Follower(leftLeader.getDeviceID(), MotorAlignmentValue.Aligned));
    TalonFXConfiguration rightConfig = new TalonFXConfiguration();
    rightConfig.MotorOutput.withInverted(InvertedValue.CounterClockwise_Positive);
    rightLeader.getConfigurator().apply(rightConfig);
    rightFollower.getConfigurator().apply(rightConfig);
    rightFollower.setControl(new Follower(rightLeader.getDeviceID(), MotorAlignmentValue.Aligned));
    setDefaultCommand(idle());
  }

  //idle command for the drivetrain
    public Command idle() {
        return run(coroutine -> {
            while (true) {
                differentialDrive.arcadeDrive(0, 0);
                coroutine.yield();
            }
            })
            .named("Idle");
    }

    //handle arcadedrive doublesupplier for the drivetrain
    public Command arcadeDrive(DoubleSupplier forward, DoubleSupplier rotation) {
        return run(coroutine -> {
            while (true) {
                differentialDrive.arcadeDrive(forward.getAsDouble(), rotation.getAsDouble());
                coroutine.yield();
            }
            })
            .named("Arcade Drive");
    }

  public void periodic() {
    if (RobotBase.isSimulation()) {
      // Update the simulation
      drivetrainSim.periodic();
      FuelSim.periodic();
    }
  }
}
