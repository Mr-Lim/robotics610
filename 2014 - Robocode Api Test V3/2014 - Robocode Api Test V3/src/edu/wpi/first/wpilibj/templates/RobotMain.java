/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {

    boolean start = false;
    boolean reset = false;

    Joystick driver;

    RobotControl robot;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        driver = new Joystick(1);
        robot = new RobotControl(this);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        robot.resetEncoders();
    }

    public void teleopPeriodic() {
        if (driver.getRawButton(4) || driver.getRawButton(5)) {
            robot.setForward(.2, 12, false);
            robot.setTurnLeft(90);
            robot.setForward(.2, 12, false);
            robot.setTurnLeft(90);
            robot.setForward(.2, 12, false);
            robot.setTurnLeft(90);
            robot.setForward(.2, 12, false);
        }

        double yA = driver.getRawAxis(2);
        double xA = driver.getRawAxis(3);

        double rSpeed = yA - xA;
        double lSpeed = yA + xA;

        robot.setLeft(lSpeed);
        robot.setRight(rSpeed);

        if (driver.getRawButton(2)) {
            robot.setTurnLeft(45);
        }
        if (driver.getRawButton(3)) {
            robot.setTurnRight(45);
        }

        robot.printSensors();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }

    public Joystick getJoystick() {
        return driver;
    }
}
