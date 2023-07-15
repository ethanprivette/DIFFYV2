// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.helpers;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Add your docs here. */
public class SwerveModuleOptimizer {

    public static SwerveModuleState customOptomize(
            SwerveModuleState desiredState, Rotation2d currentAngle,
            Rotation2d staticAngle) {

        if (Math.abs(desiredState.speedMetersPerSecond) < 0.01) {
            desiredState = new SwerveModuleState(0, staticAngle);
        }
        return SwerveModuleState.optimize(desiredState, currentAngle);
    }

}
