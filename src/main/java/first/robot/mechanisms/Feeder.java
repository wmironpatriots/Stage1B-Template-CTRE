package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.framework.RobotBase;
import org.wpilib.hardware.bus.CANPort;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;

import first.robot.simulation.SingleFlywheelSim;

public class Feeder implements Mechanism {
    public final TalonFX motor = new TalonFX(5, CANBus.systemcore(5));

    public Command feed(){
        return run(coroutine -> {
          while (true) {
            motor.setThrottle(0.75);
            coroutine.yield();
          }
        })
        .named("feed");
    }
    public Command intake(){
        return run(coroutine -> {
          while (true) {
            motor.setThrottle(-1);
            coroutine.yield();
          }
        })
        .named("intake");
    }
    public Command outtake(){
        return run(coroutine -> {
          while (true) {
            motor.setThrottle(1);
            coroutine.yield();
          }
        })
        .named("outtake");
    }
    public Feeder() {
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
  private final SingleFlywheelSim sim = SingleFlywheelSim.forFeeder(motor);

public void periodic() { // Update the simulation
  if (RobotBase.isSimulation()) {
    sim.periodic();
  }
}

}
