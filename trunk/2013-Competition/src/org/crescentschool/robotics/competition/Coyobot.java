/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.crescentschool.robotics.competition;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.io.IOException;
import org.crescentschool.robotics.competition.controls.*;
import org.crescentschool.robotics.competition.constants.*;
import org.crescentschool.robotics.competition.subsystems.*;
import org.crescentschool.robotics.competition.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Coyobot extends IterativeRobot {

    Preferences constantsTable;
    Shooter shooter;
    DriveTrain driveTrain;
    Pneumatics pneumatics;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        shooterInit();
        //pneumatics = Pneumatics.getInstance();
        driveTrain = DriveTrain.getInstance();
        constantsTable = Preferences.getInstance();
       
            autonomousCommand = new TurnTest();
       
    }

    public void shooterInit() {
        /*
         * Jan 28, 2013
         * P: 0.01
         * I: 0.00001
         * D: 0.0001
         * ff: 0.0023
         */
        shooter = Shooter.getInstance();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        driveTrain.getGyro().reset();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        try {
            //SocketDrive.start();
          Scheduler.getInstance().add(new DriverControls());
          Scheduler.getInstance().add(new KinectDriveTest());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scheduler.getInstance().add(new OperatorControls());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        //positionTestTel shooterTeleop();
        shooterTeleop();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    /*
     public void positionTestTeleop() {

     Scheduler.getInstance().run();
     if (OI.getInstance().getDriver().getRawButton(5)) {
     driveTrain.setPID(constantsTable.getDouble("p", 0), constantsTable.getDouble("i", 0), constantsTable.getDouble("d", 0));
     driveTrain.setPosition(constantsTable.getDouble("setpoint", 0));
     }
     SmartDashboard.putNumber("Position", driveTrain.getPosition());

     }
     */

    public void shooterTeleop() {
        //Scheduler.getInstance().run();
        //shooter.getInstance().getPIDController().run();

        if (OI.getInstance().getOperator().getRawButton(InputConstants.r1Button)) {
            System.out.println("P: " + constantsTable.getDouble("p", 0) + " I: " + constantsTable.getDouble("i", 0) + " D: " + constantsTable.getDouble("d", 0));
            shooter.setPID(constantsTable.getDouble("p", 0), constantsTable.getDouble("i", 0), constantsTable.getDouble("d", 0), constantsTable.getDouble("ff", 0));
            shooter.setSpeed(constantsTable.getDouble("setpoint", 0));
        }

    }
}