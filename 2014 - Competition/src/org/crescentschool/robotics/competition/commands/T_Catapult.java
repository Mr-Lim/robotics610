/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.crescentschool.robotics.competition.OI;
import org.crescentschool.robotics.competition.constants.InputConstants;
import org.crescentschool.robotics.competition.subsystems.Camera;
import org.crescentschool.robotics.competition.subsystems.Intake;
import org.crescentschool.robotics.competition.subsystems.Catapult;
import org.crescentschool.robotics.competition.subsystems.Lights;

public class T_Catapult extends Command {

    Catapult shooter;
    Joystick driver;
    private OI oi;
    int fireCount = 0;
    boolean firing = false;
    int loadCount = 0;
    Intake intake;
    boolean truss = false;
    Joystick operator;
    boolean loaded = false;
    int loadedTime = 0;
    Camera camera;
    Preferences prefs;

    public T_Catapult() {
        System.out.println("Catapult");
        shooter = Catapult.getInstance();
        oi = OI.getInstance();
        driver = oi.getDriver();
        intake = Intake.getInstance();
        operator = oi.getOperator();
        camera = Camera.getInstance();
        prefs = Preferences.getInstance();
        requires(shooter);

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        requires(shooter);

        shooter.turnOnSensor();
        if (shooter.isLoading()) {
            SmartDashboard.putNumber("Catapult Sensor", 1);

        } else {
            SmartDashboard.putNumber("Catapult Sensor", -1);

        }
        if (!firing) {
//            System.out.println(shooter.isLoading() + " " + !loaded);
            if (shooter.isLoading() && !loaded) {

                shooter.setMain(-1);
                loadedTime = 0;
            } else {
                if (loadedTime < 1) {
                    loadedTime++;
                } else {
                    loaded = true;
                    shooter.setMain(0);

                    if (operator.getRawButton(InputConstants.r2Button)) {
                        Lights.getInstance().setPattern(Lights.SHOOT);
                        truss = false;

                        firing = true;
                        fireCount = 0;

                        if (!intake.getWristClosed()) {
                            fireCount = 10;
                        }
                        System.out.println("Shot from: " + camera.getUltrasonicInches());
                        requires(intake);
                    } else if (operator.getRawButton(InputConstants.r1Button)) {
                        Lights.getInstance().setPattern(Lights.SHOOT);

                        truss = true;
                        firing = true;
                        fireCount = 0;
                        if (!intake.getWristClosed()) {
                            fireCount = 10;
                        }
                        requires(intake);
                    }

                }
            }
        } else {
            requires(intake);
            intake.setWrist(false);
            shooter.setHardStop(truss);
            if (fireCount < prefs.getInt("eyebrowDelay", 10)) {
                shooter.setMain(0);
                fireCount++;
            } else if (!shooter.isLoading()) {

                fireCount++;
                loadCount = 0;
                shooter.setMain(-1);
            } else {

                shooter.setMain(0);
                if (loadCount > 20) {
                    firing = false;
                    loaded = false;
                } else {
                    loadCount++;
                }
                Lights.getInstance().setPattern(Lights.TELE);

            }
        }
//        System.out.println(shooter.isLoading());
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