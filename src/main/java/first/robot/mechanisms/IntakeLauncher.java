package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.framework.RobotBase;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;

import first.robot.simulation.SingleFlywheelSim;

import static org.wpilib.units.Units.Seconds;

public class IntakeLauncher implements Mechanism {
    public final TalonFX motor = new TalonFX(4, CANBus.systemcore(4));


    public Command shoot(){
        return run(coroutine -> {
          while (true) {
            coroutine.wait(Seconds.of(5));
            motor.setThrottle(0.9);
            coroutine.yield();
          }
        })
        .named("shoot");
    }
    public Command intake(){
        return run(coroutine -> {
          while (true) {
            motor.setThrottle(0.8);
            coroutine.yield();
          }
        })
        .named("intake");
    }
    public Command outtake(){
        return run(coroutine -> {
          while (true) {
            motor.setThrottle(-0.8);
            coroutine.yield();
          }
        })
        .named("outtake");
    }
    public IntakeLauncher() {
        setDefaultCommand(idle());
    }

  public Command idle() {
    return run(coroutine -> {
          while (true) {
            motor.setThrottle(0);
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
