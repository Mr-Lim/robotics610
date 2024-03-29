/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crescentschool.robotics.competition.constants;

/**
 *
 * @author ianlo
 */
public class PIDConstants {

    public static double positionP = 0.02;
    public static double positionI = 0.0015;
    public static double gyroP = 0.016;
    public static double gyroI = 0.01;
    public static int gyroTurnTimeout = 2;
    public static int positionMoveTimeout = 3;
    public static int gyroPositionCheck = 100;
}
