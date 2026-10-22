package first.robot.mechanisms;

import static org.wpilib.units.Units.Seconds;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import first.robot.simulation.SingleFlywheelSim;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.framework.RobotBase;
import org.wpilib.hardware.bus.CANPort;

public class IntakeLauncher implements Mechanism {
  private final TalonFX motor = new TalonFX(4, new CANBus(CANPort.CAN_S0));

  public IntakeLauncher() {
    setDefaultCommand(idle());
  }

  public Command shoot() {
    return run(coroutine -> {
          coroutine.wait(Seconds.of(5));
          while (true) {
            motor.setThrottle(0.75);
            coroutine.yield();
          }
        })
        .named("Shoot");
  }

  public Command intake() {
    return run(coroutine -> {
          while (true) {
            motor.setThrottle(0.8);
            coroutine.yield();
          }
        })
        .named("Intake");
  }

  public Command outtake() {
    return run(coroutine -> {
          while (true) {
            motor.setThrottle(-0.8);
            coroutine.yield();
          }
        })
        .named("Outtake");
  }

  public Command idle() {
    return run(coroutine -> {
          while (true) {
            motor.setThrottle(0.0);
            coroutine.yield();
          }
        })
        .named("Idle");
  }

  private final SingleFlywheelSim sim = SingleFlywheelSim.forIntakeLauncher(motor);

  public void periodic() { // Update the simulation
    if (RobotBase.isSimulation()) {
      sim.periodic();
    }
  }
}
