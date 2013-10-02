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
import org.openxiangqi.core.exceptions.NotImplementedException;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.BoardLocation;
import org.openxiangqi.core.geometry.PlayerRelativeLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;

public class Piece {

	private Player player;
	protected PlayerRelativeLocation playerRelativeLocation;

	public char getHtLauAbbreviation() {
		throw new NotImplementedException();
	}

	public void movePlayerRelative(Board board, PlayerRelativeMove move)
			throws EmptyBoardIllegalMoveException, OffTheBoardException {
		BoardLocation before = cloneBoardLocation();
		PlayerRelativeLocation afterPlayerRelativeLocation = calculateMoveTarget(move);
		BoardLocation afterBoardLocation = afterPlayerRelativeLocation
				.toBoardLocation(getPlayer());

		// TODO check rules

		board.applyUncheckedMove(before, afterBoardLocation);

		this.playerRelativeLocation = afterPlayerRelativeLocation;
	}

	protected PlayerRelativeLocation calculateMoveTarget(PlayerRelativeMove move)
			throws EmptyBoardIllegalMoveException, OffTheBoardException {
		throw new NotImplementedException();
	}

	protected PlayerRelativeLocation calculateMoveTargetStraight(
			PlayerRelativeMove move, int maximumDistX, int maximumDistY)
			throws EmptyBoardIllegalMoveException, OffTheBoardException {
		int addX = 0;
		int addY = 0;

		if (move.getDirection() == Direction.FORWARD) {
			addY = move.getParameter();
		} else if (move.getDirection() == Direction.BACKWARD) {
			addY = -move.getParameter();
		} else if (move.getDirection() == Direction.HORIZONTAL) {
			final int pastX = playerRelativeLocation.getHorizontal();
			final int futureX = move.getParameter();
			addX = futureX - pastX;
		} else {
			assert false;
		}

		if ((Math.abs(addX) > maximumDistX) || (Math.abs(addY) > maximumDistY)) {
			throw new EmptyBoardIllegalMoveException();
		}

		return playerRelativeLocation.cloneAndMove(addX, addY);
	}

	protected PlayerRelativeLocation calculateMoveTargetCross(
			PlayerRelativeMove move, int fixDist)
			throws EmptyBoardIllegalMoveException, OffTheBoardException {
		final int pastX = playerRelativeLocation.getHorizontal();
		final int futureX = move.getParameter();

		int addY = 0;
		int addX = futureX - pastX;

		if (move.getDirection() == Direction.FORWARD) {
			addY = fixDist;
		} else if (move.getDirection() == Direction.BACKWARD) {
			addY = -fixDist;
		} else {
			assert false;
		}

		if (Math.abs(addX) != fixDist) {
			throw new EmptyBoardIllegalMoveException();
		}

		return playerRelativeLocation.cloneAndMove(addX, addY);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerRelativeLocation getPlayerRelativeLocation() {
		return playerRelativeLocation;
	}

	public void setPlayerRelativeLocation(
			PlayerRelativeLocation playerRelativeLocation) {
		this.playerRelativeLocation = playerRelativeLocation;
	}

	public BoardLocation cloneBoardLocation() {
		return getPlayerRelativeLocation().toBoardLocation(getPlayer());
	}
}
