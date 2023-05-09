// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.ModuleState;
import frc.robot.ModuleState.DrivePoses;
import frc.robot.subsystems.DriveSubsystem;

public class TurnModules90 extends CommandBase {

  private DriveSubsystem m_driveSubsystem;
  private Timer timer = new Timer();

  /** Creates a new TurnModules90. */
  public TurnModules90(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveSubsystem = driveSubsystem;

    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer.start();
    m_driveSubsystem.runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);
    if (timer.hasElapsed(Constants.MOTOR90TURNTIME)) {
      ModuleState.getInstance().setModuleState(DrivePoses.forward);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Interrupted", interrupted);
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (ModuleState.getInstance().getState().toString().equals(DrivePoses.forward.toString()));
  }
}
