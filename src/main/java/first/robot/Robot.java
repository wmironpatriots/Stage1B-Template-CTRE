/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot;

import org.wpilib.framework.OpModeRobot;
import first.robot.mechanisms.Feeder;
import first.robot.mechanisms.IntakeLauncher;

/**
 * The methods in this class are called automatically as described in the OpModeRobot documentation.
 * OpMode classes anywhere in the package (or sub-packages) where this class is located are
 * automatically registered to display in the Driver Station. If you change the name of this class
 * or the package after creating this project, you must also update the Main.java file in the
 * project.
 */
public class Robot extends OpModeRobot {
  private final Feeder feeder = new Feeder();
  private final IntakeLauncher intakeLauncher = new IntakeLauncher();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {}

  @Override
  public void simulationPeriodic() {
    feeder.periodic();
    intakeLauncher.periodic();
    Scheduler.getDefault().run();
  }
}
