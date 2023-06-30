// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  private static final NoUMotor m_rightModuleMotor1 = new NoUMotor(Constants.RIGHTMODULEMOTOR1);
  private static final NoUMotor m_rightModuleMotor2 = new NoUMotor(Constants.RIGHTMODULEMOTOR2);

  private static final NoUMotor m_leftModuleMotor1 = new NoUMotor(Constants.LEFTMODULEMOTOR1);
  private static final NoUMotor m_leftModuleMotor2 = new NoUMotor(Constants.LEFTMODULEMOTOR2);

  private final Timer timer = new Timer();

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    m_rightModuleMotor1.setInverted(false);
    m_rightModuleMotor2.setInverted(false);
    m_leftModuleMotor1.setInverted(false);
    m_leftModuleMotor2.setInverted(false);
  }

  public static void runMotors(double speed1, double speed2) {
    m_rightModuleMotor1.set(speed1);
    m_rightModuleMotor2.set(speed2);
    m_leftModuleMotor1.set(speed1);
    m_leftModuleMotor2.set(speed2);
  }

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
    SwerveDriveKinematics.desaturateWheelSpeeds(swervemodulestates, Constants.MAX_VELOCITY_METERS_PER_SEC);
  }

  public void setModuleState(SwerveModuleState[] states) {
    
  } 

  public void stop() {
    runMotors(0.0, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Backward Timer", timer.get());
    SmartDashboard.putNumber("Forward Timer", timer.get());
    SmartDashboard.putNumber("Left Timer", timer.get());
    SmartDashboard.putNumber("Right Timer", timer.get());
  }
}
