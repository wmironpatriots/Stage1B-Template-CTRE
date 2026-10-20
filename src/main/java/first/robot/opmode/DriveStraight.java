
package first.robot.opmode;

import org.wpilib.command3.Scheduler;
import org.wpilib.opmode.PeriodicOpMode;

import first.robot.Robot;

class DriveStraight extends PeriodicOpMode {
  private final Robot robot;

  public DriveStraight(Robot robot) {
    this.robot = robot;
  }

  @Override
  public void start() {
    Scheduler.getDefault().schedule(robot.drivetrain.arcadeDrive(.5, .5).withTimeout(Seconds.of(4)));
  });
    
}