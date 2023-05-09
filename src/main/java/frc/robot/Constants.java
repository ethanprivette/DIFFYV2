// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int PRIMARYCONTROLLERPORT = 0;
    public static final int SECONDARYCONTROLLERPORT = 1;

    public static final int RIGHTMODULEMOTOR1 = 1;
    public static final int RIGHTMODULEMOTOR2 = 2;

    public static final int LEFTMODULEMOTOR1 = 3;
    public static final int LEFTMODULEMOTOR2 = 4;

    public static final int INTAKEMOTOR = 5;

    public static final double MOTORDRIVESPEED = 0.75;
    public static final double MOTORTURNSPEED = 0.9;
    public static final double MOTOR90TURNTIME = 1.0; //3.4
    public static final double MOTOR180TURNTIME = 0.64;

    public static final double SHOULDERNUDGEDEGREES = 2.0;
    public static final double ELBOWNUDGEDEGREES = 2.0;
}
