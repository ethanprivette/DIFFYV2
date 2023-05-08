// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUMotor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  private final NoUMotor m_intakeMotor = new NoUMotor(Constants.INTAKEMOTOR);
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    m_intakeMotor.setInverted(false);
  }

  public void setIntakeSpeed(double speed) {
    m_intakeMotor.set(speed);
  }

  public void stop() {
    m_intakeMotor.stopMotor();
  }

// TODO: come back to this with encoders
  // public boolean isStalled() {
  //   return Math.abs(m_intakeMotor.)
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
