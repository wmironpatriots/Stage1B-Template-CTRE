/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.Robot;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

@Teleop
public class MyTeleop extends PeriodicOpMode {
  private final Robot robot;

  /** The Robot instance is passed into the opmode via the constructor. */
  public MyTeleop(Robot robot) {
    this.robot = robot;
    private final CommandXboxController xbox = new CommandXboxController(0);
    xbox.leftBumper()
    .whileTrue(robot.intakeLauncher.intake());
    xbox.rightBumper()
    .whileTrue(robot.IntakeLauncher.shoot());
    xbox.a()
    .whileTrue(robot.feeder.outtake());
    private final CommandXboxController controller = new CommandXboxController(0);
  }

  @Override
  public void periodic() {
    /* Called periodically (set time interval) while the robot is enabled. */
  }
}
