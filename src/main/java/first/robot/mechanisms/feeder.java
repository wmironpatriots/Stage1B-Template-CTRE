import com.ctre.phoenix6.hardware.TalonFx;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import first.robot.simulation.SingleFlywheelSim;
import org.wpilib.framework.RobotBase;

public class feeder implements Mechanism {
    private TalonFX motor = new TalonFX(5, new CANBus(CANPort.CAN_S0));
}
public Command feed(){
    return run(coroutine -> {
        while(true){
            motor.setThrottle(0.75);
            coroutine.yield();
        }
    }).named("Feed");
}
public Command intake(){
     return run(coroutine -> {
        while(true){
            motor.setThrottle(-1);
            coroutine.yield();
        }
    }).named("Intake");
}
    
public Command outtake(){
    return run(coroutine -> {
        while(true){
            motor.setThrottle(1);
            coroutine.yield();
        }
    }).named("Outtake");
}
    
public Command idle(){
    return run(coroutine -> {
        while(true){
            motor.setThrottle(0);
            coroutine.yield();
        }
    }).named("Idle");
}

public Feeder() {
    setDefaultCommand(idle());
}

private final SingleFlywheelSim sim =
  SingleFlywheelSim.forFeeder(motor);

public void periodic(){
    if (RobotBase.isSimulation()) {
        sim.periodic();
    }
}