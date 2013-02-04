/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.constants.KinectConstants;
import org.crescentschool.robotics.competition.subsystems.Pneumatics;
import org.crescentschool.robotics.competition.subsystems.Shooter;

/**
 *
 * @author robotics
 */
public class OperatorControls extends Command {

    OI oi = OI.getInstance();
    Joystick operator = oi.getOperator();
    Shooter shooter = Shooter.getInstance();
    Pneumatics pneumatics = Pneumatics.getInstance();;
    int nearSpeed = KinectConstants.baseNearShooterRPM;
    int farSpeed = KinectConstants.baseFarShooterRPM;
    boolean upPosition = true;

    protected void initialize() {

    }

    protected void execute() {
        /*
         if(getOperator().getRawButton(InputConstants.r2Button)){
         Scheduler.getInstance().add(new Shoot());
         }
         if(getOperator().getRawButton(InputConstants.l2Button)){
         Scheduler.getInstance().add(new LockOn());
         }
         */

        if (operator.getRawButton(InputConstants.l2Button)) {
            shooter.setSpeed(farSpeed);
            shooter.setPID(0.01, 0, 0, 0.0019);
            pneumatics.setAngleUp(false);
            upPosition = false;
        } else if (operator.getRawButton(InputConstants.l1Button)) {
            shooter.setSpeed(nearSpeed);
            shooter.setPID(0.01, 0, 0, 0.0019);
            pneumatics.setAngleUp(true);
            upPosition = true;
        }
        //r1 reset
        if (operator.getRawButton(InputConstants.r1Button)) {
            nearSpeed = KinectConstants.baseNearShooterRPM;
            farSpeed = KinectConstants.baseNearShooterRPM;
        }
        //r2 shoot
        pneumatics.setFeeder(operator.getRawButton(InputConstants.r2Button));
        // rightY trim
        if (Math.abs(operator.getRawAxis(4)) > 0.1) {
            if (upPosition) {
                nearSpeed += operator.getRawAxis(4) * 4;
                shooter.setSpeed(nearSpeed);
                shooter.setPID(0.01, 0, 0, 0.0019);
            } else {
                farSpeed += operator.getRawAxis(4) * 4;
                shooter.setSpeed(farSpeed);
                shooter.setPID(0.01, 0, 0, 0.0019);
            }
        }

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    public Joystick getOperator() {
        return OI.getInstance().getOperator();
    }
}
