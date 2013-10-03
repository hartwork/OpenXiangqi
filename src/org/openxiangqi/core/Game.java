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
package org.openxiangqi.core;

import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.exceptions.NoSuchPieceException;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.notations.HtLauNotation;
import org.openxiangqi.core.notations.Notation;

public class Game {

	private Player player;
	private Board board;

	public Game() {
		player = Player.RED_SOUTH;
		board = new Board();
		board.setUp();
	}

	public void perform(String command) throws NoSuchPieceException,
			OffTheBoardException, MalformedNotation, EmptyBoardIllegalMoveException {
		Notation requestedMove = HtLauNotation.parse(command);
		requestedMove.apply(board, player);

		switchTurn();
	}

	private void switchTurn() {
		if (player == Player.RED_SOUTH) {
			player = Player.BLUE_NORTH;
		} else if (player == Player.BLUE_NORTH) {
			player = Player.RED_SOUTH;
		} else {
			assert false;
		}
	}

	public Board getBoard() {
		return board;
	}

	public Player getPlayer() {
		return player;
	}

}
