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
import frc.robot.RobotContainer;
import frc.robot.ModuleState.DrivePoses;
import frc.robot.Commands.TurnModules90;

public class DriveSubsystem extends SubsystemBase {

  private static final NoUMotor m_rightModuleMotor1 = new NoUMotor(Constants.RIGHTMODULEMOTOR1);
  private static final NoUMotor m_rightModuleMotor2 = new NoUMotor(Constants.RIGHTMODULEMOTOR2);

  private static final NoUMotor m_leftModuleMotor1 = new NoUMotor(Constants.LEFTMODULEMOTOR1);
  private static final NoUMotor m_leftModuleMotor2 = new NoUMotor(Constants.LEFTMODULEMOTOR2);

  private final RobotContainer m_robotContainer = new RobotContainer();

  private final Timer timer = new Timer();

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    m_rightModuleMotor1.setInverted(false);
    m_rightModuleMotor2.setInverted(false);
    m_leftModuleMotor1.setInverted(false);
    m_leftModuleMotor2.setInverted(false);
    timer.reset();
  }

  public void runMotors(double speed1, double speed2) {
    m_rightModuleMotor1.set(speed1);
    m_rightModuleMotor2.set(speed2);
    m_leftModuleMotor1.set(speed1);
    m_leftModuleMotor2.set(speed2);
  }

  /**
   * Drives motors based on ModuleState
   * @param forward
   * @param lateral
   * @param rot
   */
  public void drive(double forward, double lateral, double rot) {

    // Forward driving logic
    if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && forward <= -0.15) {
      runMotors(forward, forward);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && forward <= -0.15) {
      m_robotContainer.turnModules90();
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && forward >= -0.15) {
      m_robotContainer.turnModules90();
    } else {
      runMotors(0, 0);
    }

    // // Backwards drive logic
    // if (forward >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.forward) {
    //   runMotors(forward, forward);
    // } else if (forward >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.left) {
    //   timer.reset();
    //   timer.start();
    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer.get() >= (Constants.MOTOR90TURNTIME)) {
    //     new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.forward));
    //     runMotors(forward, forward);
    //     timer.stop();
    //     timer.reset();
    //   }
    // } else if (forward >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.right) {
    //   timer.reset();
    //   timer.start();
    //   runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

    //   if (timer.get() >= (Constants.MOTOR90TURNTIME)) {
    //     new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.forward));
    //     runMotors(forward, forward);
    //     timer.stop();
    //     timer.reset();
    //   }
    // }

    // // Left drive logic
    // if (lateral <= -0.15 && ModuleState.getInstance().getState() == DrivePoses.left) {
    //   runMotors(lateral, lateral);
    // } else if (lateral <= -0.15 && ModuleState.getInstance().getState() == DrivePoses.right) {
    //   runMotors(lateral, lateral);
    // } else if (lateral <= -0.15 && ModuleState.getInstance().getState() == DrivePoses.forward) {
    //   timer.reset();
    //   timer.start();
    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer.get() >= (Constants.MOTOR90TURNTIME)) {
    //     new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.left));
    //     timer.stop();
    //     timer.reset();
    //     runMotors(lateral, lateral);
    //   }
    // } 

    // // Right drive logic
    // if (lateral >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.right) {
    //   runMotors(lateral, lateral);
    // }  else if (lateral >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.left) {
    //   runMotors(lateral, lateral);
    // } else if (lateral >= 0.15 && ModuleState.getInstance().getState() == DrivePoses.forward) {
    //   timer.reset();
    //   timer.start();
    //   runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

    //   if (timer.get() >= (Constants.MOTOR90TURNTIME)) {
    //     new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.right));
    //     timer.stop();
    //     timer.reset();
    //     runMotors(lateral, lateral);
    //   }
    // }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Drive Timer", timer.get());
  }
}
