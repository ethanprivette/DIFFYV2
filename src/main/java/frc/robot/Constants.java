// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int PRIMARYCONTROLLERPORT = 0;
    public static final int SECONDARYCONTROLLERPORT = 1;

    public final static double kDriveMaxVoltage = 10.0;
    public final static double kMaxCurrent = 40.0;

    // public final static double kPModuleTurningController = 0 / Math.PI;
    public final static double kPModuleTurningController = 0.8 / Math.PI;

    public final static double kPModuleDriveController = 0.025;
    // public final static double kPModuleDriveController = 0.01;

    public final static double kMaxTurnOutput = 0.5;

    public final static double ks = 0.8091;
    public final static double kv = 2.4154;
    public final static double ka = 0.22344;
    // public final static double ks = 0.2;
    // public final static double kv = 0.012;
    // public final static double ka = 0.1;

    public final static double kDriveGearRatio = 7;
    public final static double kTurnGearRatio = 28;

    public final static double kDriveWheelDiameterMeters = 0.1016; // 4 inches

    public final static double kMaxAngularVelocity = Math.PI * 100;
    public final static double kMaxAngularAcceleration = Math.PI * 2 * 100;

    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.53;
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.53;

    public static final double MAX_VELOCITY_METERS_PER_SEC = 5.0;

    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SEC = 4 * Math.PI;

    public static final SwerveDriveKinematics DRIVE_KINEMATICS = new SwerveDriveKinematics(
            // Front
            new Translation2d((Math.sqrt(3) * DRIVETRAIN_TRACKWIDTH_METERS) / 4,
                    DRIVETRAIN_TRACKWIDTH_METERS / 2),
            // Back left
            new Translation2d((Math.sqrt(3) * DRIVETRAIN_TRACKWIDTH_METERS) / 4,
                    -DRIVETRAIN_TRACKWIDTH_METERS / 2),
            // Back right
            new Translation2d(-(Math.sqrt(3) * DRIVETRAIN_TRACKWIDTH_METERS) / 4, 0));

    public static final int FRONTMODULEMOTOR1 = 1;
    public static final int FRONTMODULEMOTOR2 = 2;
    public static final boolean FRONTMODULEMOTOR1INVERT = false;
    public static final boolean FRONTMODULEMOTOR2INVERT = false;
    public static final double FRONTMODULESTEEROFFSET = 0.0;
    public static final Rotation2d FRONTMODULESTATICANGLE = Rotation2d.fromDegrees(-45);

    public static final int RIGHTMODULEMOTOR1 = 3;
    public static final int RIGHTMODULEMOTOR2 = 4;
    public static final boolean RIGHTMODULEMOTOR1INVERT = false;
    public static final boolean RIGHTMODULEMOTOR2INVERT = false;
    public static final double RIGHTMODULESTEEROFFSET = 0.0;
    public static final Rotation2d RIGHTMODULESTATICANGLE = Rotation2d.fromDegrees(45);

    public static final int LEFTMODULEMOTOR1 = 5;
    public static final int LEFTMODULEMOTOR2 = 6;
    public static final boolean LEFTMODULEMOTOR1INVERT = false;
    public static final boolean LEFTMODULEMOTOR2INVERT = false;
    public static final double LEFTMODULESTEEROFFSET = 0.0;
    public static final Rotation2d LEFTMODULESTATICANGLE = Rotation2d.fromDegrees(90);

    public static final int INTAKEMOTOR = 7;

    public static final double MOTORDRIVESPEED = 0.75;
    public static final double MOTORTURNSPEED = 0.9;

    public static final double MOTOR45TURNTIME = 0.17;
    public static final double MOTOR90TURNTIME = 1.0; // .34
    public static final double MOTOR180TURNTIME = 0.64;

    public static final double SHOULDERNUDGEDEGREES = 2.0;
    public static final double ELBOWNUDGEDEGREES = 2.0;
}
