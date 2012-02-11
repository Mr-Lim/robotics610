/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.crescentschool.robotics.competition.Buttons;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.subsystems.Turret;

/**
 *
 * @author Warfa
 */
public class TurretControl extends Command {

    Turret turret = Turret.getInstance();
    OI oi = OI.getInstance();
    double tPos = 0;

    public TurretControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(turret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       // OI.printToDS(0, "Turret SetPoint: " + turret.getPosSet());
       // OI.printToDS(1, "Turret Position: " + turret.getPos());

        if (Buttons.isPressed(InputConstants.kYButton, oi.getDriver())) {
            tPos += 5;
            turret.setX(tPos);
        }
        if (Buttons.isPressed(InputConstants.kAButton, oi.getDriver())) {
            tPos -= 5;
            turret.setX(tPos);
        }
        if (Buttons.isPressed(InputConstants.kR1Button, oi.getDriver())) {
            turret.incTurretP(1);
        } else if (Buttons.isPressed(InputConstants.kL1Button, oi.getDriver())) {
            turret.decTurretP(-1);
        } else if (Buttons.isPressed(InputConstants.kR2Button, oi.getDriver())) {
            turret.incTurretI(0.001);
        } else if (Buttons.isPressed(InputConstants.kL2Button, oi.getDriver())) {
            turret.decTurretI(-0.001);
        }
        if (Buttons.isPressed(InputConstants.kStartButton, oi.getDriver())) {
            turret.resetPID();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}