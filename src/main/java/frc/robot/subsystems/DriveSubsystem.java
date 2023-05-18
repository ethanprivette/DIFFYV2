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

  public static void runMotors(double speed1, double speed2) {
    m_rightModuleMotor1.set(speed1);
    m_rightModuleMotor2.set(speed2);
    m_leftModuleMotor1.set(speed1);
    m_leftModuleMotor2.set(speed2);
  }

  /**
   * Drives motors based on ModuleState
   * @param throttle
   * @param lateral
   * @param rot
   */
  public void drive(double throttle, double lateral, double rot) {

    if (throttle >= 0.15) {
      switch (ModuleState.getInstance().getState().name()) {
        case "forward":
          runMotors(-throttle, -throttle);
          break;

        case "left":
          timer.start();

          runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

          if (timer.get() >= Constants.MOTOR90TURNTIME) {
            ModuleState.getInstance().setModuleState(DrivePoses.forward);
            timer.stop();
            timer.reset();
          }
          break;

        case "right":
          timer.start();

          runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

          if (timer.get() >= Constants.MOTOR90TURNTIME) {
            ModuleState.getInstance().setModuleState(DrivePoses.forward);
            timer.stop();
            timer.reset();
          }
          break;

        case "turnL":
          timer.start();

          runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

          if (timer.get() >= Constants.MOTOR45TURNTIME) {
            ModuleState.getInstance().setModuleState(DrivePoses.forward);
            timer.stop();
            timer.reset();
          }
          break;

        case "TurnR":
          timer.start();

          runMotors(Constants.MOTORTURNSPEED, Constants.MOTORTURNSPEED);

          if (timer.get() >= Constants.MOTOR45TURNTIME) {
            ModuleState.getInstance().setModuleState(DrivePoses.forward);
            timer.stop();
            timer.reset();
          }
          break;

        default:
          break;
      }
    }

    // // BACKWARDS
    // if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && throttle >= 0.15) {
    //   runMotors(-throttle, -throttle);
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && throttle >= 0.15) {
    //   timer.start();

    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer.get() >= Constants.MOTOR90TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer.stop();
    //     timer.reset();
    //   }

    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && throttle >= 0.15) {
    //   timer.start();

    //   runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

    //   if (timer.get() >= Constants.MOTOR90TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer.stop();
    //     timer.reset();
    //   }
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && throttle >= 0.15) {
    //   timer.start();

    //   runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

    //   if (timer.get() >= Constants.MOTOR45TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer.stop();
    //     timer.reset();
    //   }
    // } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && throttle >= 0.15) {
    //   timer.start();

    //   runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

    //   if (timer.get() >= Constants.MOTOR45TURNTIME) {
    //     ModuleState.getInstance().setModuleState(DrivePoses.forward);
    //     timer.stop();
    //     timer.reset();
    //   }
      
    // FORWARDS
    if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && throttle <= -0.15) {
      runMotors(-throttle, -throttle);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && throttle <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer.stop();
        timer.reset();
      }

    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && throttle <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && throttle <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && throttle <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.forward);
        timer.stop();
        timer.reset();
      }
    }

    // LEFT
    else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && lateral <= -0.15) {
      runMotors(lateral, lateral);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && lateral <= -0.15) {
      runMotors(lateral, lateral);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && lateral <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.left);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && lateral <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.left);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && lateral <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME + Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.left);
        timer.stop();
        timer.reset();
      }
    }
    
    // RIGHT
    else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && lateral >= 0.15) {
      runMotors(lateral, lateral);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && lateral >= 0.15) {
      runMotors(lateral, lateral);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && lateral >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.right);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && lateral >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME + Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.right);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && lateral >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.right);
        timer.stop();
        timer.reset();
      }
    }
    
    // TURN LEFT
    else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && rot <= -0.15) {
      runMotors(rot, rot);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && rot <= -0.15) {
      timer.start();
      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnL);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && rot <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME + Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnL);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && rot <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnL);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && rot <= -0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnL);
        timer.stop();
        timer.reset();
      }
    }

    // TURN RIGHT
    else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnR.name()) && rot >= 0.15) {
      runMotors(rot, rot);
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.right.name()) && rot >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORDRIVESPEED, Constants.MOTORTURNSPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnR);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.forward.name()) && rot >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnR);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.turnL.name()) && rot >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnR);
        timer.stop();
        timer.reset();
      }
    } else if (ModuleState.getInstance().getState().name().equals(DrivePoses.left.name()) && rot >= 0.15) {
      timer.start();

      runMotors(Constants.MOTORTURNSPEED, Constants.MOTORDRIVESPEED);

      if (timer.get() >= Constants.MOTOR90TURNTIME + Constants.MOTOR45TURNTIME) {
        ModuleState.getInstance().setModuleState(DrivePoses.turnR);
        timer.stop();
        timer.reset();
      }
    }
    // reset motors and timers when nothing is pressed
    else {
      runMotors(0.0, 0.0);
      timer.reset();
    }
  }

  public void stop() {
    runMotors(0.0, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Backward Timer", timer.get());
    SmartDashboard.putNumber("Forward Timer", timer.get());
    SmartDashboard.putNumber("Left Timer", timer.get());
    SmartDashboard.putNumber("Right Timer", timer.get());
  }
}
