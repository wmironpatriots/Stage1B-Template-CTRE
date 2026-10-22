/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.Robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.driverstation.XboxController;
import org.wpilib.opmode.Autonomous;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.units.Units;

class DriveStraight extends PeriodicOpMode {
  private final Robot robot;

  public DriveStraight(Robot robot) {
    this.robot = robot;
  }

  @Override
  public void start() {
    // scheduler an arcadeDrive command with a timeout of 4 seconds
    Scheduler.getDefault()
        .schedule(
          robot.drivetrain.arcadeDrive(() -> 0.5, () -> 0.0).withTimeout(Units.Seconds.of(4.0)));
  }
}