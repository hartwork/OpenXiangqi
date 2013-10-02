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

import org.openxiangqi.core.Side;
import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.pieces.Cannon;
import org.openxiangqi.core.pieces.Adviser;
import org.openxiangqi.core.pieces.King;
import org.openxiangqi.core.pieces.Knight;
import org.openxiangqi.core.pieces.Elephant;
import org.openxiangqi.core.pieces.Pawn;
import org.openxiangqi.core.pieces.Piece;
import org.openxiangqi.core.pieces.Rook;

public class Board implements Iterable<Piece> {
	public static int HORIZONTAL_COUNT = 9;
	public static int VERTICAL_COUNT = 10;

	private Piece[][] board;

	public Board() {
		board = new Piece[HORIZONTAL_COUNT][VERTICAL_COUNT];
	}

	public void setUp() {
		Player[] players = { Player.RED_SOUTH, Player.BLUE_NORTH };
		for (Player player : players) {
			place(new Rook(), new PlayerRelativeLocation(1, 1), player);
			place(new Knight(), new PlayerRelativeLocation(2, 1), player);
			place(new Elephant(), new PlayerRelativeLocation(3, 1), player);
			place(new Adviser(), new PlayerRelativeLocation(4, 1), player);
			place(new King(), new PlayerRelativeLocation(5, 1), player);
			place(new Adviser(), new PlayerRelativeLocation(6, 1), player);
			place(new Elephant(), new PlayerRelativeLocation(7, 1), player);
			place(new Knight(), new PlayerRelativeLocation(8, 1), player);
			place(new Rook(), new PlayerRelativeLocation(9, 1), player);

			place(new Cannon(), new PlayerRelativeLocation(2, 3), player);
			place(new Cannon(), new PlayerRelativeLocation(8, 3), player);

			for (int x = 1; x <= HORIZONTAL_COUNT; x += 2) {
				place(new Pawn(), new PlayerRelativeLocation(x, 4), player);
			}
		}
	}

	private void place(Piece piece,
			PlayerRelativeLocation playerRelativeLocation, Player player) {
		BoardLocation boardLocation = playerRelativeLocation
				.toBoardLocation(player);
		board[boardLocation.getHorizontal()][boardLocation.getVertical()] = piece;
		piece.setPlayerRelativeLocation(playerRelativeLocation);
		piece.setPlayer(player);
	}

	public void applyUncheckedMove(BoardLocation before, BoardLocation after) {
		assert !after.equals(before);

		final int bx = before.getHorizontal();
		final int by = before.getVertical();
		final int ax = after.getHorizontal();
		final int ay = after.getVertical();

		assert board[bx][by] != null;

		board[ax][ay] = board[bx][by];
		board[bx][by] = null;

		assert board[ax][ay] != null;
		assert board[bx][by] == null;
	}

	@Override
	public Iterator<Piece> iterator() {
		return new PieceIterator(board);
	}

}
