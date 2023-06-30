// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.TurnModule;

public class DiffModule extends SubsystemBase {

  private final NoUMotor m_motor1;
  private final NoUMotor m_motor2;
  private final double m_steerOffset;

  /** Creates a new DiffModule with two motors,
   * 
  */
  public DiffModule(int motor1Port, int motor2Port, double steerOffset, boolean motor1Invert, boolean motor2Invert) {
    m_motor1 = new NoUMotor(motor1Port);
    m_motor2 = new NoUMotor(motor2Port);
    m_steerOffset = steerOffset;

    m_motor1.setInverted(motor1Invert);
    m_motor2.setInverted(motor2Invert);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(
      10 * m_motor1.get(),
      new Rotation2d());
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d()); // TODO: GET CURRENT ANGLE

    new TurnModule(state.angle.getRadians());

    m_motor1.set(state.speedMetersPerSecond / Constants.MAX_VELOCITY_METERS_PER_SEC);
    m_motor2.set(-state.speedMetersPerSecond / Constants.MAX_VELOCITY_METERS_PER_SEC);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
