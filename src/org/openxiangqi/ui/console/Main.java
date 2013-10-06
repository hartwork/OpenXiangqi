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
package org.openxiangqi.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openxiangqi.core.Game;
import org.openxiangqi.core.Side;
import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.exceptions.NoSuchPieceException;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.BoardLocation;
import org.openxiangqi.core.notations.HtLauNotationParser;
import org.openxiangqi.core.pieces.Piece;

public class Main {
	
	private static void clearDisplayAbove() {
		System.out.write((byte)'\u001b');
		System.out.print("[1J");
	}

	private static void moveCursorUpperLeft() {
		System.out.write((byte)'\u001b');
		System.out.print("[0;0H");
	}
	
	public static void main(String[] args) {
		Game game = new Game();

		String command = null;
		while (true) {
			clearDisplayAbove();
			moveCursorUpperLeft();

			Exception storedException = null;

			if (command != null) {
				try {
					game.perform(command);
				} catch (NoSuchPieceException e) {
					storedException = e;
				} catch (OffTheBoardException e) {
					storedException = e;
				} catch (MalformedNotation e) {
					storedException = e;
				} catch (EmptyBoardIllegalMoveException e) {
					storedException = e;
				}
			}
			
			dumpState(game);

			if (storedException != null) {
				complain(storedException);
			} else {
				extraSpace();
			}

			extraSpace();

			try {
				command = readCommand("move> ");
			} catch (IOException e) {
				break;
			}

			extraSpace();
		}
	}

	static void extraSpace() {
		System.out.println();
	}

	private static void dumpState(Game game) {
		Piece[][] remake = new Piece[Board.HORIZONTAL_COUNT][Board.VERTICAL_COUNT];
		for (Piece piece : game.getBoard()) {
			BoardLocation boardLocation = piece.cloneBoardLocation();
			remake[boardLocation.getHorizontal()][boardLocation.getVertical()] = piece;
		}

		HtLauNotationParser notationParser = new HtLauNotationParser();

		for (int y = Board.VERTICAL_COUNT - 1; y >= 0; y--) {
			System.out.print("  ");
			for (int x = 0; x < Board.HORIZONTAL_COUNT; x++) {
				Piece piece = remake[x][y];
				char display = ' ';
				if (piece != null) {
					char abbreviation = notationParser
							.lookUpPieceAbbreviation(piece.getPieceType());
					display = (piece.getPlayer() == Player.BLUE_NORTH) ? Character
							.toLowerCase(abbreviation) : abbreviation;
				}
				System.out.print(display + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Next turn: " + Side.playerName(game.getPlayer()));
	}

	private static void complain(Exception e) {
		System.out.println("ERROR: " + e);
	}

	private static String readCommand(String prompt) throws IOException {
		System.out.print(prompt);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
}
