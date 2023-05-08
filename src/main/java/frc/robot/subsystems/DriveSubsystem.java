// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.NoULib.lib.NoUMotor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ModuleState;
import frc.robot.ModuleState.DrivePoses;

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

  public void runMotors(double speed1, double speed2) {
    m_rightModuleMotor1.set(speed1);
    m_rightModuleMotor2.set(speed2);
    m_leftModuleMotor1.set(speed1);
    m_rightModuleMotor2.set(speed2);
  }

  /**
   * Drives motors based on ModuleState
   * @param forward
   * @param lateral
   * @param rot
   */
  public void drive(double forward, double lateral, double rot) {

    // Forward driving logic
    if (forward >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.forward) {
      runMotors(forward, forward);
    } else if (forward >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.left) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        timer.stop();
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        runMotors(lateral, lateral);
      }
    } else if (forward >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.right) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        runMotors(lateral, lateral);
        timer.stop();
      }
    }

    // Backwards drive logic
    if (forward <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.forward) {
      runMotors(forward, forward);
    } else if (forward <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.left) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        runMotors(forward, forward);
        timer.stop();
      }
    } else if (forward <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.right) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        runMotors(forward, forward);
        timer.stop();
      }
    }

    // Left drive logic
    if (lateral >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.left) {
      runMotors(lateral, lateral);
    } else if (lateral >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.right) {
      runMotors(lateral, lateral);
    } else if (lateral >= 0.3 && ModuleState.getInstance().getState() == DrivePoses.forward) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        ModuleState.getInstance().setModuleState(DrivePoses.left);
        timer.stop();
        runMotors(lateral, lateral);
      }
    } 

    // Right drive logic
    if (lateral <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.right) {
      runMotors(lateral, lateral);
    }  else if (lateral <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.left) {
      runMotors(lateral, lateral);
    } else if (lateral <= 0.3 && ModuleState.getInstance().getState() == DrivePoses.forward) {
      timer.reset();
      timer.start();
      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
        ModuleState.getInstance().setModuleState(DrivePoses.right);
        timer.stop();
        runMotors(lateral, lateral);
      }
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Module State", ModuleState.getInstance().getState().toString());
    SmartDashboard.putNumber("Drive Timer", timer.get());
  }
}
