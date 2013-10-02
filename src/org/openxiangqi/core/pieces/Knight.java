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

import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.exceptions.rules.PieceMustNotMoveHorizontally;
import org.openxiangqi.core.geometry.PlayerRelativeLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;

public class Knight extends PieceBase {
	public char getHtLauAbbreviation() {
		return 'N';
	}

	public char getWxfAbbreviation() {
		return 'H';
	}

	protected PlayerRelativeLocation calculateMoveTarget(PlayerRelativeMove move)
			throws OffTheBoardException, EmptyBoardIllegalMoveException {
		if (move.getDirection() == Direction.HORIZONTAL) {
			throw new PieceMustNotMoveHorizontally();
		}

		final int pastX = playerRelativeLocation.getHorizontal();
		final int futureX = move.getParameter();

		final int addX = futureX - pastX;
		final int distX = Math.abs(addX);
		if ((distX < 1) || (distX > 2)) {
			throw new EmptyBoardIllegalMoveException();
		}

		final int distY = (distX == 1) ? 2 : 1;
		final int addY = (move.getDirection() == Direction.FORWARD) ? distY
				: -distY;

		return playerRelativeLocation.cloneAndMove(addX, addY);
	}
}
