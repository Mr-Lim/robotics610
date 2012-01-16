/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.constants;

/**
 *
 * @author Warfa
 */
public class PIDConstants {
    public static final boolean pidTuneMode = true;
    
    public static final double posP = 500;
    public static final double posI = 500;
    public static final double posD = 0;
    public static final double speedP = -0.0020;
    public static final double speedI = 0.006;
    public static final double speedD = 0;
    public static final double gyroP = 0;
    
    // Dimensions in inches
    public static final double wheelCircumference = 4 * Math.PI;
    
    // Speeds in feet/second
    public static final double maxDriveSpeed = 10;
}