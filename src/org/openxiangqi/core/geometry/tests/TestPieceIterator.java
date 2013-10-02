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
package org.openxiangqi.core.geometry.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.BoardLocation;
import org.openxiangqi.core.pieces.Piece;

public class TestPieceIterator {

	private static int count(Board board) {
		int count = 0;
		for (Piece piece : board) {
			count++;
		}
		return count;
	}

	@Test
	public void test() {
		Board board = new Board();
		board.setUp();

		assertEquals(32, count(board));

		BoardLocation before = new BoardLocation(0, 0);
		BoardLocation after = new BoardLocation(0, 1);
		board.applyUncheckedMove(before, after);

		assertEquals(32, count(board));
	}

}
