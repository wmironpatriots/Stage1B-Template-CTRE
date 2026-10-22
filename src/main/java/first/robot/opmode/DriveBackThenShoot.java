/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.Robot;
import first.robot.mechanisms.Drivetrain;
import first.robot.mechanisms.Feeder;
import first.robot.mechanisms.IntakeLauncher;

import org.wpilib.command3.Command;
import org.wpilib.driverstation.XboxController;
import org.wpilib.opmode.Autonomous;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.units.Units;


class DriveBackThenShoot extends PeriodicOpMode {
  private final Robot robot;
  private final Drivetrain drivetrain;
  private final IntakeLauncher intakeLauncher; 
  private final Feeder feeder;

  public DriveBackThenShoot(Robot robot) {
    this.robot = robot;
    this.drivetrain = robot.drivetrain;
    this.intakeLauncher = robot.intakeLauncher;
    this.feeder = robot.feeder;
  }

  private Command autoCommand() {
    return Command.noRequirements(
            coroutine -> {
              coroutine.await(robot.drivetrain.arcadeDrive(() -> -0.2, () -> 0.0).withTimeout(Units.Seconds.of(2)));
              coroutine.awaitAll(robot.intakeLauncher.shoot(), robot.feeder.feed());
            })
        .named("Drive Back Then Shoot");
  }

  @Override
  public void start() {
  }
}