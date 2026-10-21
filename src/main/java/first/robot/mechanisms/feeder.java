import com.ctre.phoenix6.hardware.TalonFx;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class feeder implements Mechanism {
    private TalonFX motor = new TalonFX(5, new CANBus(CANPort.CAN_S0));
}
public Command feed(){
    return Commands.run(() -> motor.set(0.5), this);
}
public Command feed(){
    return Commands.run(() -> motor.set(0.3), this);
}
public Command feed(){
    return Commands.run(() -> motor.set(-0.3), this);
}
public Command idle(){
    return Commands.runOnce(() -> motor.set(0), this);
}
