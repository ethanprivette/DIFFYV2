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
import frc.robot.ModuleState.DrivePoses;
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
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ArmSubsystem m_armSubsystem = new ArmSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_primaryController = new CommandXboxController(Constants.PRIMARYCONTROLLERPORT);
  private final CommandXboxController m_secondaryController = new CommandXboxController(Constants.SECONDARYCONTROLLERPORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(new RunCommand(() -> m_driveSubsystem.drive(
      modifyAxis(m_primaryController.getLeftY()) * Constants.MAX_VELOCITY_METERS_PER_SEC,
      modifyAxis(m_primaryController.getLeftX()) * Constants.MAX_VELOCITY_METERS_PER_SEC,
      modifyAxis(m_primaryController.getRightX()) 
        * Constants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SEC,
      false),
      m_driveSubsystem));
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

    m_primaryController.x()
      .onTrue(new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.left)));

    m_primaryController.y()
      .onTrue(new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.forward)));

    m_primaryController.b()
      .onTrue(new InstantCommand(() -> ModuleState.getInstance().setModuleState(DrivePoses.right)));

    m_primaryController.povLeft()
      .whileTrue(new RunCommand(
        () -> m_driveSubsystem.drive(0.0, -1.0, 0.0, false),
        m_driveSubsystem));

    m_primaryController.povRight()
      .whileTrue(new RunCommand(
        () -> m_driveSubsystem.drive(0.0, 1, 0.0, false),
        m_driveSubsystem));     

    m_primaryController.povUp()
      .whileTrue(new RunCommand(
        () -> m_driveSubsystem.drive(1, 0.0, 0.0, false),
        m_driveSubsystem));

    m_primaryController.povDown()
      .whileTrue(new RunCommand(
        () -> m_driveSubsystem.drive(-1, 0.0, 0.0, false),
        m_driveSubsystem));

    // m_secondaryController.povRight().onTrue(new InstantCommand(m_armSubsystem::nudgeShoulderForward));
    // m_secondaryController.povLeft().onTrue(new InstantCommand(m_armSubsystem::nudgeShoulderBackward));
    // m_secondaryController.povUp().onTrue(new InstantCommand(m_armSubsystem::nudgeElbowUp));
    // m_secondaryController.povDown().onTrue(new InstantCommand(m_armSubsystem::nudgeElbowDown));

    m_secondaryController.start()
      .onTrue(new InstantCommand(() -> GameState.getInstance().setGamePieceDesired(GamePiece.CONE)));

    m_secondaryController.back()
      .onTrue(new InstantCommand(() -> GameState.getInstance().setGamePieceDesired(GamePiece.CUBE)));

    Trigger coneTrigger = new Trigger(
      () -> GameState.getInstance().getGamePieceDesired() == GamePiece.CONE);

    Trigger cubeTrigger = new Trigger(
      () -> GameState.getInstance().getGamePieceDesired() == GamePiece.CUBE);

    Trigger approachTrigger = new Trigger(
      () -> m_armSubsystem.getArmPlacement() == KnownArmPlacement.SCORE_PRE);

    m_secondaryController.axisLessThan(Axis.kLeftY.value, -0.5)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.SCORE_PRE)));

    m_secondaryController.rightBumper()
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.SUBSTATION_HALFWAY)));

    m_secondaryController.x()
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.SCORE_PRE))
      .andThen(new WaitCommand(1.0))
      .andThen(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.STOWED))));

    m_secondaryController.a().and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_LOW)));

    m_secondaryController.a().and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_LOW)));

    m_secondaryController.b().and(approachTrigger).and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_MID)));
    
    m_secondaryController.b().and(approachTrigger).and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_MID)));

    m_secondaryController.y().and(approachTrigger).and(cubeTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CUBE_HIGH)));

    m_secondaryController.y().and(approachTrigger).and(coneTrigger)
      .onTrue(new InstantCommand(() -> m_armSubsystem.setKnownArmPlacement(KnownArmPlacement.CONE_HIGH)));
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
