// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class GameState {

    private static final GameState INSTANCE = new GameState();

    public enum GamePiece {
        CONE,
        CUBE;
    }

    private GamePiece m_gamePieceDesired = GamePiece.CUBE;
    private boolean m_gamePieceHeld = false;

    private GameState() {
    }

    public static GameState getInstance() {
        return INSTANCE;
    }

    public GamePiece getGamePieceDesired() {
        return m_gamePieceDesired;
    }

    public void setGamePieceDesired(final GamePiece piece) {
        m_gamePieceDesired = piece == null ? GamePiece.CUBE : piece;
    }

    public boolean isGamePieceHeld() {
        return m_gamePieceHeld;
    }

    public void setGamePieceHeld(boolean pieceHeld) {
        m_gamePieceHeld = pieceHeld;
    }

}
