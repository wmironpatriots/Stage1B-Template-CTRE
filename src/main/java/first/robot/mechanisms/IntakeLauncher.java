import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import first.robot.simulation.SingleFlywheelSim;
import org.wpilib.framework.RobotBase;

public class IntakeLauncher implements Mechanism {
    private TalonFX motor = new TalonFX(4, CANBus.systemcore(0));
}

public Command intake(){
     return run(coroutine -> {
        while(true){
            motor.setThrottle(0.8);
            coroutine.yield();
        }
    }).named("Intake");
}

public Command outtake(){
     return run(coroutine -> {
        while(true){
            motor.setThrottle(-0.8);
            coroutine.yield();
        }
    }).named("outtake");
}

public Command shoot() {
    return run(coroutine -> {
        coroutine.yieldFor(2);
    }
        while(true){
        motor.setThrottle(0.9);
        coroutine.yield();
    }).named("Shoot");
}

private final SingleFlywheelSim sim =
  SingleFlywheelSim.forIntakeLauncher(motor);

public void periodic(){
    if (RobotBase.isSimulation()) {
        sim.periodic();
    }   
}