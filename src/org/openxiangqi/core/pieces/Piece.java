/*
 * OpenXiangqi -- Chinese chess implemented in Java
 * Copyright (C) 2013  Sebastian Pipping <sebastian@pipping.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openxiangqi.core.pieces;

import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.BoardLocation;
import org.openxiangqi.core.geometry.PlayerRelativeLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove;

public interface Piece {

	public enum PieceType {
		ADVISER, CANNON, CHARIOT, ELEPHANT, HORSE, KING, PAWN,
	}

	public final PieceType[] ALL_PIECE_TYPES = { PieceType.ADVISER,
			PieceType.CANNON, PieceType.CHARIOT, PieceType.ELEPHANT,
			PieceType.HORSE, PieceType.KING, PieceType.PAWN };

	public PieceType getPieceType();

	public void movePlayerRelative(Board board, PlayerRelativeMove move)
			throws EmptyBoardIllegalMoveException, OffTheBoardException;

	public Player getPlayer();

	public void setPlayer(Player player);

	public PlayerRelativeLocation getPlayerRelativeLocation();

	public void setPlayerRelativeLocation(
			PlayerRelativeLocation playerRelativeLocation);

	public BoardLocation cloneBoardLocation();

}