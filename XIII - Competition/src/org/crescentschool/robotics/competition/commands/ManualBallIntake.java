/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.crescentschool.robotics.competition.*;
import org.crescentschool.robotics.competition.subsystems.Intake;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;

/**
 *
 * @author ian
 */
public class ManualBallIntake extends Command {
    Intake intake = Intake.getInstance();
    double speed = 1;
    OI oi = OI.getInstance();
    
    public ManualBallIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Buttons.isPressed(InputConstants.kCirclebutton, oi.getOperator())){
            intake.setInbotForward(speed);
        } if(Buttons.isReleased(InputConstants.kCirclebutton, oi.getOperator())){
            intake.setInbotForward(0);
        } if(Buttons.isPressed(InputConstants.kSquarebutton, oi.getOperator())){
            intake.setInbotForward(-speed);
        } if(Buttons.isReleased(InputConstants.kSquarebutton, oi.getOperator())){
            intake.setInbotForward(0);
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
