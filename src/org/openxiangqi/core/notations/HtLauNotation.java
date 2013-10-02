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
package org.openxiangqi.core.notations;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.exceptions.NoSuchPieceException;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.PlayerRelativeMove;
import org.openxiangqi.core.geometry.PlayerRelativeLocation.LooseVerticalLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;
import org.openxiangqi.core.pieces.Piece;

public class HtLauNotation {
	private static String REGEX_PATTERN = "^((([CSKNMPR])([1-9]))|(([fb])([CSNMPR])))([fbh])([1-9])$";

	private enum HorizontalConfiguration {
		SAME_VERTICAL_LINE, DIFFERENT_VERTICAL_LINES,
	}

	private char pieceAbbreviation;
	private HorizontalConfiguration horizontalConfiguration;
	private int playerRelativeHorizontalLocation;
	private LooseVerticalLocation playerRelativeVerticalLocation;

	private Direction direction;
	private int parameter;

	public static HtLauNotation parse(String notation) throws MalformedNotation {
		Pattern p = Pattern.compile(REGEX_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(notation);

		if (!m.matches()) {
			throw new MalformedNotation();
		}

		HorizontalConfiguration horizontalConfiguration = m.group(3).isEmpty() ? HorizontalConfiguration.SAME_VERTICAL_LINE
				: HorizontalConfiguration.DIFFERENT_VERTICAL_LINES;

		char directionChar = Character.toLowerCase(m.group(8).charAt(0));
		Direction directionEnum = null;
		if (directionChar == 'f') {
			directionEnum = Direction.FORWARD;
		} else if (directionChar == 'b') {
			directionEnum = Direction.BACKWARD;
		} else if (directionChar == 'h') {
			directionEnum = Direction.HORIZONTAL;
		} else {
			assert false;
		}
		int parameter = Integer.parseInt(m.group(9));

		char pieceAbbreviation = (char) -1;
		int playerRelativeHorizontalLocation = -1;
		LooseVerticalLocation playerRelativeVerticalLocationEnum = null;
		if (horizontalConfiguration == HorizontalConfiguration.SAME_VERTICAL_LINE) {
			pieceAbbreviation = Character.toUpperCase(m.group(7).charAt(0));
			char playerRelativeVerticalLocationChar = Character.toLowerCase(m
					.group(6).charAt(0));
			if (playerRelativeVerticalLocationChar == 'f') {
				playerRelativeVerticalLocationEnum = LooseVerticalLocation.FRONT;
			} else if (playerRelativeVerticalLocationChar == 'f') {
				playerRelativeVerticalLocationEnum = LooseVerticalLocation.REAR;
			} else {
				assert false;
			}
		} else {
			pieceAbbreviation = Character.toUpperCase(m.group(3).charAt(0));
			playerRelativeHorizontalLocation = Integer.parseInt(m.group(4));
		}

		return new HtLauNotation(pieceAbbreviation, horizontalConfiguration,
				playerRelativeHorizontalLocation,
				playerRelativeVerticalLocationEnum, directionEnum, parameter);
	}

	private HtLauNotation(char pieceAbbreviation,
			HorizontalConfiguration horizontalConfiguration,
			int playerRelativeHorizontalLocation,
			LooseVerticalLocation playerRelativeVerticalLocation,
			Direction direction, int parameter) {
		this.pieceAbbreviation = pieceAbbreviation;
		this.horizontalConfiguration = horizontalConfiguration;
		this.playerRelativeHorizontalLocation = playerRelativeHorizontalLocation;
		this.playerRelativeVerticalLocation = playerRelativeVerticalLocation;
		this.direction = direction;
		this.parameter = parameter;
	}

	private Piece findPieceByHorizontalLocation(Board board,
			char pieceAbbreviation, int playerRelativeHorizontalLocation,
			Player player) throws NoSuchPieceException {
		for (Piece piece : board) {
			if ((piece.getHtLauAbbreviation() == pieceAbbreviation)
					&& (piece.getPlayer() == player)
					&& (piece.getPlayerRelativeLocation().getHorizontal() == playerRelativeHorizontalLocation)) {
				return piece;
			}
		}
		throw new NoSuchPieceException();
	}

	private Piece findPieceByVerticalLocation(Board board,
			char pieceAbbreviation,
			LooseVerticalLocation playerRelativeVerticalLocation, Player player)
			throws NoSuchPieceException {
		int resIndex = -1;
		int resVertical = -1;
		ArrayList<Piece> candidates = new ArrayList<Piece>();
		for (Piece piece : board) {
			if ((piece.getHtLauAbbreviation() == pieceAbbreviation)
					&& (piece.getPlayer() == player)) {
				candidates.add(piece);

				// Best candidate yet?
				if (candidates.isEmpty()) {
					resIndex = 0;
					resVertical = piece.getPlayerRelativeLocation()
							.getVertical();
				} else {
					final int prevVertical = resVertical;
					final int curVertical = piece.getPlayerRelativeLocation()
							.getVertical();

					if (((playerRelativeVerticalLocation == LooseVerticalLocation.FRONT) && (curVertical > prevVertical))
							|| ((playerRelativeVerticalLocation == LooseVerticalLocation.FRONT) && (curVertical < prevVertical))) {
						resIndex = candidates.size() - 1;
						resVertical = curVertical;
					}
				}
			}
		}

		if (candidates.isEmpty()) {
			throw new NoSuchPieceException();
		}

		return candidates.get(resIndex);
	}

	public void apply(Board board, Player player) throws NoSuchPieceException,
			OffTheBoardException, EmptyBoardIllegalMoveException {
		Piece piece = null;
		if (horizontalConfiguration == HorizontalConfiguration.SAME_VERTICAL_LINE) {
			piece = findPieceByVerticalLocation(board, pieceAbbreviation,
					playerRelativeVerticalLocation, player);
		} else {
			piece = findPieceByHorizontalLocation(board, pieceAbbreviation,
					playerRelativeHorizontalLocation, player);
		}

		PlayerRelativeMove move = new PlayerRelativeMove(direction, parameter);
		piece.movePlayerRelative(board, move);
	}
}
