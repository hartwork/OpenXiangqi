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
package org.openxiangqi.core.geometry;

import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;

public class PlayerRelativeLocation {
	public enum LooseVerticalLocation {
		FRONT, REAR,
	}

	private int horizontal;
	private int vertical;

	public PlayerRelativeLocation(int horizontal, int vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	public BoardLocation toBoardLocation(Player player) {
		if (player == Player.RED_SOUTH) {
			return new BoardLocation(Board.HORIZONTAL_COUNT - horizontal,
					vertical - 1);
		} else if (player == Player.BLUE_NORTH) {
			return new BoardLocation(horizontal - 1, Board.VERTICAL_COUNT
					- vertical);
		} else {
			assert false;
			return null;
		}
	}

	public int getHorizontal() {
		return horizontal;
	}

	public int getVertical() {
		return vertical;
	}

	public PlayerRelativeLocation move(int distX, int distY)
			throws OffTheBoardException {
		final int futureX = horizontal + distX;
		final int futureY = vertical + distY;

		if ((futureX < 1) || (futureX > Board.HORIZONTAL_COUNT)
				|| (futureY < 1) || (futureY > Board.VERTICAL_COUNT)) {
			throw new OffTheBoardException();
		}

		horizontal = futureX;
		vertical = futureY;
		return this;
	}

	public PlayerRelativeLocation cloneAndMove(int distX, int distY)
			throws OffTheBoardException {
		return new PlayerRelativeLocation(horizontal, vertical).move(distX,
				distY);
	}

	public boolean isInsidePalace() {
		return ((horizontal >= 4) && (horizontal <= 6) && (vertical <= 3));
	}

	public boolean isAcrossTheRiver() {
		return (vertical > 5);
	}

}
