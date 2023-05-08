// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class ModuleState {

    private static final ModuleState INSTANCE = new ModuleState();


    public enum DrivePoses {
        forward,
        left,
        right;
    }

    private DrivePoses m_ModuleState = DrivePoses.forward;

    public static ModuleState getInstance() {
        return INSTANCE;
    }

    public ModuleState() {
        m_ModuleState = DrivePoses.forward;
    }

    public DrivePoses getState() {
        return m_ModuleState;
    }

    public void setModuleState(DrivePoses state) {
        m_ModuleState = state;
    }

    public boolean checkZero() {
        if (getState() == null) {
            return false;
        }
        return getState() == DrivePoses.forward;
    }
}
