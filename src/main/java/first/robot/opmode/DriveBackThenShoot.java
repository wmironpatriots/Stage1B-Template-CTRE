package first.robot.opmode;

import org.wpilib.command3.Command;
import org.wpilib.command3.Scheduler;
import org.wpilib.opmode.PeriodicOpMode;

import first.robot.Robot;

class DriveBackThenShoot extends PeriodicOpMode {
  private final Robot robot;

  public DriveBackThenShoot(Robot robot) {
    this.robot = robot;
  }

  public Command autoCommand(){
        return Command.noRequirements(coroutine -> {
          while (true) {
            coroutine.await(robot.drivetrain.arcadeDrive(.2,0).withTimeout(Seconds.of(4)));
            coroutine.awaitAll(robot.intakeLauncher.shoot(), robot.feeder.feed())
            coroutine.yield();
          }
        })
        .named("autoCommand");
    }

  @Override
  public void start() {
     Scheduler.getDefault().schedule(autoCommand());
}
}
