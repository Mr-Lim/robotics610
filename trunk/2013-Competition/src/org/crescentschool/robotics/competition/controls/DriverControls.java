/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import java.io.IOException;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.commands.*;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.subsystems.Pneumatics;

/**
 *
 * @author robotics
 */
public class DriverControls extends Command {

    Pneumatics pneumatics = Pneumatics.getInstance();
    OI oi = OI.getInstance();
    Joystick driver = oi.getDriver();
    public static int driveMode = 0;
    // 0 = Kaj, 1 = Hang, 2 = Position, 3 = auto turn

    protected void initialize() {
        Scheduler.getInstance().add(new KajDrive());
    }

    protected void execute() {

        if (getDriveMode() != 0 && driver.getRawButton(InputConstants.r1Button)) {
            Scheduler.getInstance().add(new KajDrive());
            pneumatics.setPowerTakeOff(false);
            setDriveMode(0);
        }




        if (getDriveMode() != 1 && driver.getRawButton(InputConstants.l2Button) && driver.getRawButton(InputConstants.r2Button)) {
            pneumatics.setPowerTakeOff(true);
            setDriveMode(1);
        }

        if (getDriveMode() != 2 && driver.getRawAxis(InputConstants.dPadY) < -0.2) {
            Scheduler.getInstance().add(new PositionControl(true, 3.2, true, 3.2));
            setDriveMode(2);
        }
        if (getDriveMode() != 2 && driver.getRawAxis(InputConstants.dPadY) > 0.2) {
            Scheduler.getInstance().add(new PositionControl(true, -3.2, true, -3.2));
            setDriveMode(2);
        }
        
        if (driver.getRawAxis(InputConstants.dPadX)>0.2){
            Scheduler.getInstance().add(new PositionControl(true, 4, true, 0));
            setDriveMode(2);
        }
        if (driver.getRawAxis(InputConstants.dPadX)<-0.2){
            Scheduler.getInstance().add(new PositionControl(true,0, true, 4));
            setDriveMode(2);
        }
        pneumatics.postUp(driver.getRawButton(InputConstants.l1Button));

        //System.out.println("DCon");
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    public Joystick getDriver() {
        return OI.getInstance().getDriver();
    }

    /**
     * @return the driveMode
     */
    public static int getDriveMode() {
        return driveMode;
    }

    /**
     * @param driveMode the driveMode to set
     */
    public static void setDriveMode(int driveMode2) {
        driveMode = driveMode2;
    }
    
}