// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.GameState.GamePiece;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ArmSubsystem.KnownArmPlacement;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_DriveSubsystem = new DriveSubsystem();
  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_primaryController = new CommandXboxController(Constants.PRIMARYCONTROLLERPORT);
  private final CommandXboxController m_secondaryController = new CommandXboxController(Constants.SECONDARYCONTROLLERPORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    m_primaryController.axisGreaterThan(1, 0.5)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(modifyAxis(m_primaryController.getLeftX()), 0, 0), 
        m_DriveSubsystem));
    m_primaryController.axisLessThan(1, 0.5)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(modifyAxis(m_primaryController.getLeftX()), 0, 0), 
        m_DriveSubsystem));
    m_primaryController.axisGreaterThan(0, 0.5)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(0, modifyAxis(m_primaryController.getLeftY()), 0), 
        m_DriveSubsystem));
    m_primaryController.axisLessThan(0, 0.5)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(0, modifyAxis(m_primaryController.getLeftY()), 0),
        m_DriveSubsystem));
    m_primaryController.axisGreaterThan(3, .05)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(0, 0, modifyAxis(m_primaryController.getRightX())), 
        m_DriveSubsystem));
    m_primaryController.axisLessThan(3, 0.5)
      .whileTrue(new RunCommand(() -> m_DriveSubsystem.drive(0, 0, modifyAxis(m_primaryController.getRightX())),
        m_DriveSubsystem));

    m_primaryController.povLeft()
      .whileTrue(new RunCommand(
        () -> m_DriveSubsystem.drive(0, -1, 0),
        m_DriveSubsystem));

    m_primaryController.povRight()
      .whileTrue(new RunCommand(
        () -> m_DriveSubsystem.drive(0, 1, 0),
        m_DriveSubsystem));     

    m_primaryController.povUp()
      .whileTrue(new RunCommand(
        () -> m_DriveSubsystem.drive(1, 0, 0),
        m_DriveSubsystem));

    m_primaryController.povDown()
      .whileTrue(new RunCommand(
        () -> m_DriveSubsystem.drive(-1, 0, 0),
        m_DriveSubsystem));

    m_secondaryController.povRight().onTrue(new InstantCommand(m_ArmSubsystem::nudgeShoulderForward));
    m_secondaryController.povLeft().onTrue(new InstantCommand(m_ArmSubsystem::nudgeShoulderBackward));
    m_secondaryController.povUp().onTrue(new InstantCommand(m_ArmSubsystem::nudgeElbowUp));
    m_secondaryController.povDown().onTrue(new InstantCommand(m_ArmSubsystem::nudgeElbowDown));

    Trigger coneTrigger = new Trigger(
      () -> GameState.getInstance().getGamePieceDesired() == GamePiece.CONE);

    Trigger cubeTrigger = new Trigger(
      () -> GameState.getInstance().getGamePieceDesired() == GamePiece.CUBE);

    Trigger approachTrigger = new Trigger(
      () -> m_ArmSubsystem.getArmPlacement() == KnownArmPlacement.SCORE_PRE);

    m_secondaryController.axisLessThan(Axis.kLeftY.value, -0.5)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.SCORE_PRE)));

    m_secondaryController.x()
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.SCORE_PRE))
      .andThen(new WaitCommand(1.0))
      .andThen(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.STOWED))));

    m_secondaryController.a().and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_LOW)));

    m_secondaryController.a().and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_LOW)));

    m_secondaryController.b().and(approachTrigger).and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_MID)));
    
    m_secondaryController.b().and(approachTrigger).and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_MID)));

    m_secondaryController.y().and(approachTrigger).and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_HIGH)));

    m_secondaryController.y().and(approachTrigger).and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_ArmSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_HIGH)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
    // An example command will be run in autonomous
  }

  private static double modifyAxis(double value) {
    // Deadband
    value = MathUtil.applyDeadband(value, 0.05);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }
}