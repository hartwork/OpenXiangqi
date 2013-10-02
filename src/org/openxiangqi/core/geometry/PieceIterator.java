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

import java.util.Iterator;

import org.openxiangqi.core.exceptions.NotImplementedException;
import org.openxiangqi.core.pieces.Piece;

public class PieceIterator implements Iterator<Piece> {
	private Piece[][] board;
	private int horizontal;
	private int vertical;

	public PieceIterator(Piece[][] board) {
		this.board = board;
		horizontal = 0;
		vertical = 0;
	}

	public boolean hasNext() {
		for (int y = vertical; y < Board.VERTICAL_COUNT; y++) {
			for (int x = (y == vertical) ? horizontal : 0; x < Board.HORIZONTAL_COUNT; x++) {
				if (board[x][y] != null) {
					horizontal = x;
					vertical = y;
					return true;
				}
			}
		}
		return false;
	}

	public Piece next() {
		Piece piece = board[horizontal][vertical];

		// Advance for next time
		if (horizontal < Board.HORIZONTAL_COUNT - 1) {
			horizontal++;
		} else {
			horizontal = 0;
			vertical++;
		}

		return piece;
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}

}
