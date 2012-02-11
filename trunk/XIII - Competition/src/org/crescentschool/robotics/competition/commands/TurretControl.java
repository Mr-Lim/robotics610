/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.Command;
import org.crescentschool.robotics.competition.Buttons;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.constants.PotConstants;
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

        if (Buttons.isPressed(InputConstants.kYButton, oi.getOperator())) {
            turret.incTurretP(1);
        } else if (Buttons.isPressed(InputConstants.kXButton, oi.getOperator())) {
            turret.decTurretP(1);
        } else if (Buttons.isPressed(InputConstants.kBButton, oi.getOperator())) {
            turret.incTurretI(0.001);
        } else if (Buttons.isPressed(InputConstants.kAButton, oi.getOperator())) {
            turret.decTurretI(0.001);
        }
  //      if (Math.abs(oi.getOperator().getRawAxis(InputConstants.kRightXAxis)) > 0.1) {
            turret.incPosition(PotConstants.turretInc * MathUtils.pow(oi.getOperator().getRawAxis(InputConstants.kRightXAxis), 3));
  //      }
        System.out.println("Turret Set: " + turret.getPosSet() + " Pos: " + turret.getPos());
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
