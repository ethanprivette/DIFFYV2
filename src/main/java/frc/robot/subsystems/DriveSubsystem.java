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

  private final Timer timer1 = new Timer();
  private final Timer timer2 = new Timer();
  private final Timer timer3 = new Timer();
  private final Timer timer4 = new Timer();

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    m_rightModuleMotor1.setInverted(false);
    m_rightModuleMotor2.setInverted(false);
    m_leftModuleMotor1.setInverted(false);
    m_leftModuleMotor2.setInverted(false);
    timer1.reset();
    timer2.reset();
    timer3.reset();
    timer4.reset();
  }

  public static void runMotors(double speed1, double speed2) {
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

    if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && forward >= 0.15) {
      runMotors(-forward, -forward);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && forward >= 0.15) {
      timer1.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer1.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer1.stop();
        timer1.reset();
      }

    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && forward >= 0.15) {
      timer1.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer1.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer1.stop();
        timer1.reset();
      }
      
    } else {
      runMotors(0, 0);
      timer1.reset();
    }

    // if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && forward <= -0.15) {
    //   runMotors(-forward, -forward);
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && forward <= -0.15) {
    //   timer2.start();

    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer2.get() >= Constants.MOTOR90TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer2.stop();
    //     timer2.reset();
    //   }

    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && forward <= -0.15) {
    //   timer2.start();

    //   runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

    //   if (timer2.get() >= Constants.MOTOR90TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer2.stop();
    //     timer2.reset();
    //   }
      
    // } else {
    //   runMotors(0, 0);
    //   timer2.reset();
    // }

    // LEFT
    // if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && lateral >= 0.15) {
    //   runMotors(lateral, lateral);
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && lateral >= 0.5) {
    //   runMotors(lateral, lateral);
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && lateral >= 0.15) {
    //   timer.start();

    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer.get() >= Constants.MOTOR90TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.left);
    //     timer.stop();
    //     timer.reset();
    //   }
      
    // } else {
    //   runMotors(0.0, 0.0);
    //   timer.reset();
    // }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Backward Timer", timer1.get());
    SmartDashboard.putNumber("Forward Timer", timer2.get());
  }
}
