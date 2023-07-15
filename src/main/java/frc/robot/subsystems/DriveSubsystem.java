// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  private final DiffModule[] m_modules;
  private final SwerveModuleState[] m_lastStates;

  private final DiffModule m_frontModule = new DiffModule(
    Constants.FRONTMODULEMOTOR1,
    Constants.FRONTMODULEMOTOR2,
    Constants.FRONTMODULESTATICANGLE,
    Constants.FRONTMODULEMOTOR1INVERT,
    Constants.FRONTMODULEMOTOR2INVERT);
  private final DiffModule m_LeftModule = new DiffModule(
    Constants.LEFTMODULEMOTOR1,
    Constants.LEFTMODULEMOTOR2,
    Constants.LEFTMODULESTATICANGLE,
    Constants.FRONTMODULEMOTOR1INVERT,
    Constants.FRONTMODULEMOTOR2INVERT);
  private final DiffModule m_rightModule = new DiffModule(
    Constants.FRONTMODULEMOTOR1,
    Constants.FRONTMODULEMOTOR2,
    Constants.FRONTMODULESTATICANGLE,
    Constants.FRONTMODULEMOTOR1INVERT,
    Constants.RIGHTMODULEMOTOR2INVERT);

  private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(Constants.DRIVE_KINEMATICS, getRotation2d(), getModulePositions());

  // /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    m_modules = new DiffModule[] { m_LeftModule, m_rightModule, m_frontModule };
    m_lastStates = new SwerveModuleState[m_modules.length];
  }

  // public static void runMotors(double speed1, double speed2) {
  //   m_rightModuleMotor1.set(speed1);
  //   m_rightModuleMotor2.set(speed2);
  //   m_leftModuleMotor1.set(speed1);
  //   m_leftModuleMotor2.set(speed2);
  // }

  /**
   * Drives motors based on joystick input
   * @param xSpeed        Speed of the robot in the X direction (forward).
   * @param ySpeed        Speed of the robot in the Y direction (sideways).
   * @param rot           Angular rate of the robot.
   * @param fieldRelative Whether provided X and Y speeds are field relative.
   */
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    var swervemodulestates = Constants.DRIVE_KINEMATICS.toSwerveModuleStates(
      fieldRelative
        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, Rotation2d.fromDegrees(45))
        : new ChassisSpeeds(xSpeed, ySpeed, rot));
    setModuleStates(swervemodulestates);
  }

  public void setModuleStates(SwerveModuleState[] states) {
    SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.MAX_VELOCITY_METERS_PER_SEC);

    double driveMaxVolts = 0;

    for (int i = 0; i < m_modules.length; i++) {
      driveMaxVolts = Math.max(driveMaxVolts, m_modules[i].setDesiredState(states[i]));
    }

    double driveMaxScale = 1;

    if (driveMaxVolts > Constants.kDriveMaxVoltage) {
      driveMaxScale = Constants.kDriveMaxVoltage / driveMaxVolts;
    }

    for (DiffModule module : m_modules) {
        module.setVoltage(driveMaxScale);
    }
  }

  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(0.0); // imu value
  }

  public void updateOdometry() {
    m_odometry.update(
      Rotation2d.fromDegrees(0.0), getModulePositions());
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d position) {
    m_odometry.resetPosition(getRotation2d(), getModulePositions(), position);
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
      m_frontModule.getPosition(),
      m_LeftModule.getPosition(),
      m_rightModule.getPosition()
    };
  }

  public void stop() {
    drive(0.0, 0.0, 0.0, false);
  }

  @Override
  public void periodic() {
    for (int i = 0; i < m_modules.length; i++) {
      m_lastStates[i] = m_modules[i].getState();
    }
  }
}
