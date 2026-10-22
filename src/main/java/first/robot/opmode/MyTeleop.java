/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.*;
import first.robot.mechanisms.Feeder;
import first.robot.mechanisms.IntakeLauncher;
import org.wpilib.command3.Trigger;
import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;

@Teleop
public class MyTeleop extends PeriodicOpMode {
  private final Robot robot;
  private final CommandXboxController xbox;
  public IntakeLauncher intakeLauncher = new IntakeLauncher();
  public Feeder feeder = new Feeder();

  /** The Robot instance is passed into the opmode via the constructor. */
  public MyTeleop(Robot robot) {
    this.robot = robot;
    this.xbox = new CommandXboxController(0);
    robot.drivetrain.setDefaultCommand(
        robot.drivetrain.arcadeDrive(
            () -> -xbox.getLeftY(), () -> -xbox.getRightX())); 
  }

  @Override
  public void periodic() {
    intakeLauncher.periodic();
    feeder.periodic();
    /* Called periodically (set time interval) while the robot is enabled. */
    Trigger leftBumper = xbox.leftBumper();
    Trigger rightBumper = xbox.rightBumper();
    Trigger aButton = xbox.a();
    leftBumper.whileTrue(intakeLauncher.intake()).whileTrue(feeder.intake());
    rightBumper.whileTrue(intakeLauncher.shoot()).whileTrue(feeder.feed());
    aButton.whileTrue(intakeLauncher.outtake()).whileTrue(feeder.outtake());
  }
}
