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

import org.openxiangqi.core.Side.Player;
import org.openxiangqi.core.exceptions.NoSuchPieceException;
import org.openxiangqi.core.exceptions.rules.EmptyBoardIllegalMoveException;
import org.openxiangqi.core.exceptions.rules.OffTheBoardException;
import org.openxiangqi.core.geometry.Board;
import org.openxiangqi.core.geometry.PlayerRelativeLocation.LooseVerticalLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;
import org.openxiangqi.core.pieces.Piece;

public class Notation {

	public enum HorizontalConfiguration {
		SAME_VERTICAL_LINE, DIFFERENT_VERTICAL_LINES,
	}

	private char pieceAbbreviation;
	private HorizontalConfiguration horizontalConfiguration;
	private int playerRelativeHorizontalLocation;
	private LooseVerticalLocation playerRelativeVerticalLocation;
	private Direction direction;
	private int parameter;

	public Notation(char pieceAbbreviation,
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || !(obj instanceof Notation)) {
			return false;
		}
		Notation other = (Notation) obj;
		return (direction == other.direction)
				&& (horizontalConfiguration == other.horizontalConfiguration)
				&& (parameter == other.parameter)
				&& (pieceAbbreviation == other.pieceAbbreviation)
				&& (playerRelativeHorizontalLocation == other.playerRelativeHorizontalLocation)
				&& (playerRelativeVerticalLocation == other.playerRelativeVerticalLocation);
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