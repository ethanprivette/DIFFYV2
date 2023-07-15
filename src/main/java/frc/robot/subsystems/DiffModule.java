// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUMotor;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.SwerveModuleOptimizer;

public class DiffModule extends SubsystemBase {

  private NoUMotor m_motor1;
  private NoUMotor m_motor2;

  private Rotation2d m_staticAngle;

  private double m_moduleAngle;
  private double m_driveSpeed;

  private double m_desiredSpeed;
  private double m_desiredAngle;

  private double m_topVoltage;
  private double m_botVoltage;

  private double m_offset;

  private double m_driveOutput;
  private double m_turnOutput;

  private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(
      Constants.kPModuleTurningController,
      0.0,
      0.0,
      new Constraints(Constants.kMaxAngularVelocity,
          Constants.kMaxAngularAcceleration));

  private final PIDController m_drivePIDController = new PIDController(
      Constants.kPModuleDriveController,
      0.0,
      0.0);

  private final SimpleMotorFeedforward m_driveFeedForward = new SimpleMotorFeedforward(
      Constants.ks,
      Constants.kv,
      Constants.ka);

  /**
   * Creates a new DiffModule with two motors,
   * 
   */
  public DiffModule(int motor1Port, int motor2Port, Rotation2d staticAngle, boolean motor1Invert,
      boolean motor2Invert) {
    m_motor1 = new NoUMotor(motor1Port);
    m_motor2 = new NoUMotor(motor2Port);
    m_staticAngle = staticAngle;

    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);

    m_motor1.setInverted(motor1Invert);
    m_motor2.setInverted(motor2Invert);
  }

  public SwerveModuleState getState() {
    double topMotorSpeed = m_motor1.get();
    double bottomMotorSpeed = m_motor2.get();

    m_driveSpeed = getDriveSpeed(topMotorSpeed, bottomMotorSpeed);
    m_moduleAngle = getAngle();

    return new SwerveModuleState(m_driveSpeed, Rotation2d.fromDegrees(m_moduleAngle));
  }

  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(
      distanceDriven(), new Rotation2d(getAngle()));
  }

  public double getAngle() { // TODO: GET CURRENT ANGLE
    return 0.0;
  }

  public double getDriveSpeed(double topMotorSpeed, double bottomMotorSpeed) {
    double speed = ((topMotorSpeed - bottomMotorSpeed) / 2) *
        (10.0 / 2048.0) * 
        Constants.kDriveGearRatio *
        (Constants.kDriveWheelDiameterMeters * Math.PI);

    return speed;
  }

  public double distanceDriven() {
    return 0.0; //TODO: math
  }

  public void setVoltage(double driveMaxScale) {
    m_motor1.set(m_topVoltage * driveMaxScale / Constants.kDriveMaxVoltage);
    m_motor2.set(m_botVoltage * driveMaxScale / Constants.kDriveMaxVoltage);
  }

  public double setDesiredState(SwerveModuleState desiredState) {
    SwerveModuleState state = SwerveModuleOptimizer.customOptomize(desiredState,
        Rotation2d.fromDegrees(getAngle()), m_staticAngle);

    m_desiredSpeed = state.speedMetersPerSecond;
    m_desiredAngle = state.angle.getDegrees();

    m_turnOutput = m_turningPIDController.calculate((m_moduleAngle / 180 * Math.PI),
        (m_desiredAngle / 180 * Math.PI));
    m_turnOutput = MathUtil.clamp(m_turnOutput, -Constants.kMaxTurnOutput, Constants.kMaxTurnOutput);

    m_desiredSpeed *= Math.abs(Math.cos(m_turningPIDController.getPositionError()));

    m_driveOutput = m_drivePIDController.calculate(m_driveSpeed, m_desiredSpeed);

    double driveFF = m_driveFeedForward.calculate(m_desiredSpeed);

    m_topVoltage = m_driveOutput + driveFF + Constants.kDriveMaxVoltage * m_turnOutput;

    m_botVoltage = -m_driveOutput - driveFF + Constants.kDriveMaxVoltage * m_turnOutput;

    return Math.max(Math.abs(m_topVoltage), Math.abs(m_botVoltage));
  }

  @Override
  public void periodic() {

  }
}
