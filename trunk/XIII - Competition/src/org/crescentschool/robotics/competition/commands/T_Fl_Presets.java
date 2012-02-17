/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.crescentschool.robotics.competition.Buttons;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.constants.PotConstants;
import org.crescentschool.robotics.competition.subsystems.Flipper;

/**
 *
 * @author Warfa, Mr. Lim
 */
public class T_Fl_Presets extends Command {

    OI oi = OI.getInstance();
    Flipper flipper = Flipper.getInstance();

    public T_Fl_Presets() {
        System.out.println(this.toString());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(flipper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        if (Buttons.isPressed(InputConstants.kR2Button, oi.getDriver())) {
            flipper.setFlippers(PotConstants.flipperBarrier);
        }
        if (Buttons.isPressed(InputConstants.kL2Button, oi.getDriver())) {
            flipper.setFlippers(PotConstants.flipperRetract);
        }
        if (Buttons.isPressed(InputConstants.kAButton, oi.getDriver())) {
            flipper.setFlippers(PotConstants.flipperBridgeAssist);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println(this + " canceled");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        System.out.println(this + " canceled");cancel();
    }
}
