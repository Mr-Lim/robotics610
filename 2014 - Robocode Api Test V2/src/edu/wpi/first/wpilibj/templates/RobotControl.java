/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author matthewtory
 */
public class RobotControl {

    Talon frontLeft, midLeft, rearLeft;
    Talon frontRight, midRight, rearRight;
    Encoder leftEnc, rightEnc;
    Gyro driveGyro;

    private RobotMain parent;

    private int ticks = 0;
    private int iCap = 1000;
    private int iCount = 0;
    private boolean finished = false;

    public RobotControl(RobotMain parent) {
        this.parent = parent;

        frontLeft = new Talon(6);
        midLeft = new Talon(5);
        rearLeft = new Talon(4);
        frontRight = new Talon(3);
        midRight = new Talon(2);
        rearRight = new Talon(1);

        leftEnc = new Encoder(2, 1);
        rightEnc = new Encoder(4, 3);

        driveGyro = new Gyro(1);
        driveGyro.setSensitivity(Constants.GYRO_SENSITIVITY);
        starts();
    }

    public void setTurnLeft(int degrees) {

        //Local Variables
        double minSpeed = 0.35;
        double minSpeed2 = 0.15;
        double maxSpeed = 0.6;
        double speed;

        double halfDegrees = degrees / 3;
        double sG = driveGyro.getAngle();
        double angle = sG;

        while (angle < sG + degrees) {  //Turn loop
            angle = driveGyro.getAngle();

            /*
             Speed scales based on the percentage of the half turn complete
             Min. Speed <= speed <= Max. Speed
             */
            if (angle < sG + halfDegrees) { //First half of turn
                speed = minSpeed + ((angle - sG) / (halfDegrees) * (maxSpeed - minSpeed));
                if (speed < minSpeed) { //Min. speed restriction
                    speed = minSpeed;
                }
            } else {    //Second half of turn
                speed = maxSpeed - ((angle - (sG - ((angle - sG) - degrees))) / (degrees)) * (maxSpeed - minSpeed2);
                if (speed < minSpeed2) {    //Min. speed restriction
                    speed = minSpeed2;
                }
            }
            if (speed > maxSpeed) { //Max. speed restriction
                speed = maxSpeed;
            }
            setLeft(speed);
            setRight(-speed);
        }

        while(angle >=(sG+degrees)+1){
            System.out.println("Overturn Left");
            speed = minSpeed;
            setLeft(-speed);
            setRight(speed);
        }
        setStop(false); //Stop at end of turn
    }

    public void setTurnRight(int degrees) {

        //Local Variables
        double minSpeed = 0.35;
        double minSpeed2 = 0.15;
        double maxSpeed = 0.6;
        double speed;

        double halfDegrees = degrees / 3;
        double sG = driveGyro.getAngle();
        double angle = sG;

        /*
         Speed scales based on the percentage of the half turn complete
         Min. Speed <= speed <= Max. Speed
         */
        while (angle > sG - degrees) {  //First half of turn
            angle = driveGyro.getAngle();
            if (angle > sG - halfDegrees) {
                speed = minSpeed - ((angle - sG) / (halfDegrees) * (maxSpeed - minSpeed));
                if (speed < minSpeed) { //Min. speed restriction
                    speed = minSpeed;
                }
            } else {  //Second half of turn
                speed = maxSpeed + ((angle - (sG - ((angle - sG) + degrees))) / (degrees)) * (maxSpeed - minSpeed2);
                if (speed < minSpeed2) {    //Min. speed restriction
                    speed = minSpeed2;
                }
            }
            if (speed > maxSpeed) { //Max. speed restriction
                speed = maxSpeed;
            }
            setLeft(-speed);
            setRight(speed);
        }

        //-45<=(45-90)-1
        while(angle<=(sG-degrees)-1){
            System.out.println("Overturn Right");
            speed = minSpeed*2;
            setLeft(speed);
            setRight(-speed);
        }
   
        setStop(false); //Stop at end of turn
    }

    public void setStop(boolean brake) {
        setLeft(0);
        setRight(0);
    }
    public void setForward(int encTicks){
        int lastEnc = getEncoders();
        while(getEncoders()<lastEnc+encTicks){
            System.out.println("enc:"+getEncoders()+", lc"+lastEnc+encTicks);
            setForward(0.5);
        }
        setStop(false);
    }
    public void setForward(double speed) {
        setLeft(speed);
        setRight(speed);
    }
    public void setForward3(int targetInches){
        //Get the left and right values on the encoders
        
        double p = 0.02;
        double i = 0.0008;
        
        double leftInches = toInches(leftEnc.get());
        double rightInches = toInches(rightEnc.get());
        double leftSpeed = (targetInches - leftInches) * p;
        double rightSpeed = (targetInches - rightInches) * p;

        if (leftSpeed > 0.05) {
            if (iCount < iCap) {
                iCount++;
            }
        } else if (leftSpeed < -.05) {
            if (iCount > -iCap) {
                iCount--;
            }
        }
        double encoderError = Math.abs(targetInches-(leftInches+rightInches)/2.0);
        if(encoderError<0.1){
            finished = true;
            iCount=0;
        }
        double gyroError = Math.abs(driveGyro.getAngle());
        if (driveGyro.getAngle() < -0.5) {

            rightSpeed -= gyroError * 0.05;

            leftSpeed += gyroError * 0.05;

        } else if (driveGyro.getAngle() > 0.5) {
            rightSpeed += gyroError * 0.05;
            leftSpeed -= gyroError * 0.05;

        }
        
        leftSpeed += i * iCount;
        rightSpeed += i * iCount;
        setLeft(leftSpeed);
        setRight(rightSpeed);
    }
    public void setForward2(int inches, double speed) {
        resetEncoders();

        double minSpeed = 0.2;
        double maxSpeed = 0.5;
        int halfInches = inches / 2;

        while (getEncoders() < inches * Constants.TICK_PER_INCH) {
            if (speed < maxSpeed) {
                if (getEncoders() < halfInches * Constants.TICK_PER_INCH) {
                    speed = (getEncoders() / (halfInches * Constants.TICK_PER_INCH)) * maxSpeed;
                } else {
                    speed = 1 - (getEncoders() / (inches * Constants.TICK_PER_INCH)) * maxSpeed;
                }
            }
        }
    }

    public void setForward(int inches, double speed) {
        resetEncoders();

        //Max Encoder Ticks = Wheel Circumference x Ticks Per Inch
        //360 = 18*20
        while (getEncoders() < inches * Constants.TICK_PER_INCH) {
            setForward(speed);
        }
        setStop(false);
    }

    public void setForward(int inches, double speed, Gyro driveGyro, int lastEnc, boolean correction) {

        double lSpeed = speed;
        double rSpeed = speed;

        int maxDif = 10;

        driveGyro.reset();
        double startAngle = driveGyro.getAngle();
        while (getEncoders() - lastEnc < inches * Constants.INCH_PER_TICK) {
            setLeft(lSpeed);
            setRight(rSpeed);
            if (correction) {
                double angle = driveGyro.getAngle();
                if (angle > startAngle + maxDif) {
                    lSpeed--;
                } else if (angle < startAngle - maxDif) {
                    rSpeed--;
                }
            }
        }
        setStop(false);
    }

    public void setForward(int inches, double speed, boolean correction) {
        resetEncoders();

        double lSpeed = speed;
        double rSpeed = speed;

        int mD = 50;

        while (getEncoders() < inches * Constants.TICK_PER_INCH) {
            int lD = leftEnc.getRaw() - rightEnc.getRaw();
            int rD = rightEnc.getRaw() - leftEnc.getRaw();

            setLeft(lSpeed);
            setRight(rSpeed);

            if (correction) {
                if (lD > mD) {
                    lSpeed--;
                } else {
                    lSpeed = speed;
                }
                if (rD > mD) {
                    rSpeed--;
                } else {
                    rSpeed = speed;
                }
            }
        }
        setStop(false);
    }

    public void setLeft(double speed) {
        frontLeft.set(speed);
        midLeft.set(speed);
        rearLeft.set(speed);
    }

    public void setRight(double speed) {
        frontRight.set(speed);
        midRight.set(speed);
        rearRight.set(speed);
    }

    public void printSensors() {

        if (ticks > 60) {
            System.out.println("Left: " + leftEnc.getRaw()
                    + ", Right: " + rightEnc.getRaw()
                    + ", Gyro: " + driveGyro.getAngle() + ", Stick" + parent.getJoystick().getRawAxis(2));
            System.out.println(frontLeft.get());
            ticks = 0;
        }

        ticks++;
    }

    public void resetEncoders() {
        leftEnc.reset();
        rightEnc.reset();
    }

    public void starts() {
        leftEnc.start();
        rightEnc.start();
        driveGyro.reset();
    }

    public void stop() {
        resetEncoders();
    }

    public int getEncoders() {
        return (Math.abs(leftEnc.get()) + Math.abs(rightEnc.get())) / 2;
    }
    private double toInches(int encCount) {
        return ((int) (encCount / 10.24 * Math.PI * 6 + 0.5)) / 100.0*3.8;
    }
}
