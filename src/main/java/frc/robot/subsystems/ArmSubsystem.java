// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUServo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

  private KnownArmPlacement m_lastPlacement = KnownArmPlacement.STOWED;

  private NoUServo m_shoulderServo = new NoUServo(1);
  private NoUServo m_elbowServo = new NoUServo(2);

  public enum KnownArmPlacement {

    STOWED(101.0, -58.0),
    SCORE_PRE(108.0, 5.7),
    SUBSTATION_HALFWAY(108.0, 3.0),
    CONE_HIGH(64.0, 36.0),
    CONE_MID(92.5, 11.5),
    CONE_LOW(90.0, -53.0),
    CUBE_HIGH(56.0, 26.0),
    CUBE_MID(91.0, -1.0),
    CUBE_LOW(90.0, -53.0);

    public final double m_shoulderAngle;
    public final double m_elbowAngle;

    private KnownArmPlacement(double shoulderAngle, double elbowAngle) {
      m_shoulderAngle = shoulderAngle;
      m_elbowAngle = elbowAngle;
    }
  }

  public ArmSubsystem() {
  }

  public void setKnownArmPlacement(final KnownArmPlacement placement) {
    setShoulderPosition(placement.m_shoulderAngle);
    setElbowPosition(placement.m_elbowAngle);

    m_lastPlacement = placement;
  }

  public KnownArmPlacement getArmPlacement() {
    return m_lastPlacement;
  }

  public void nudgeShoulderForward() {
    setShoulderPosition(m_lastPlacement.m_shoulderAngle + Constants.SHOULDERNUDGEDEGREES);
  }

  public void nudgeShoulderBackward() {
    setShoulderPosition(m_lastPlacement.m_shoulderAngle - Constants.SHOULDERNUDGEDEGREES);
  }

  public void nudgeElbowUp() {
    setElbowPosition(m_lastPlacement.m_elbowAngle + Constants.ELBOWNUDGEDEGREES);
  }

  public void nudgeElbowDown() {
    setElbowPosition(m_lastPlacement.m_elbowAngle - Constants.ELBOWNUDGEDEGREES);
  }

  public void setShoulderPosition(double targetPos) {
    m_shoulderServo.setAngle(targetPos);
  }

  public void setElbowPosition(double targetPos) {
    m_elbowServo.setAngle(targetPos);
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("Arm State", m_lastPlacement.toString());
  }
}
