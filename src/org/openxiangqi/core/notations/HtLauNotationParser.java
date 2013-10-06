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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.geometry.PlayerRelativeLocation.LooseVerticalLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;
import org.openxiangqi.core.pieces.Piece.PieceType;

public class HtLauNotationParser extends NotationParserBase {
	private static String REGEX_PATTERN = "^(([CSKNMPR])([1-9])|([fr])([CSNMPR]))([fbh])([1-9])$";

	public Notation parse(String notation, Strictness strictness)
			throws MalformedNotation {
		Pattern p = Pattern.compile(REGEX_PATTERN,
				(strictness == Strictness.STRICT) ? 0
						: Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(notation);

		if (!m.matches()) {
			throw new MalformedNotation();
		}

		String pieceAbbreviationFirst = m.group(2);
		String horizontalLocationString = m.group(3);
		String relativeVerticalLocationString = m.group(4);
		String pieceAbbreviationSecond = m.group(5);
		String directionString = m.group(6);
		String parameterString = m.group(7);

		return assemble(pieceAbbreviationFirst, horizontalLocationString,
				relativeVerticalLocationString, pieceAbbreviationSecond,
				directionString, parameterString);
	}

	protected LooseVerticalLocation lookUpVerticalLocation(
			char playerRelativeVerticalLocationChar) {
		LooseVerticalLocation playerRelativeVerticalLocationEnum = null;
		if (playerRelativeVerticalLocationChar == 'f') {
			playerRelativeVerticalLocationEnum = LooseVerticalLocation.FRONT;
		} else if (playerRelativeVerticalLocationChar == 'r') {
			playerRelativeVerticalLocationEnum = LooseVerticalLocation.REAR;
		} else {
			assert false;
		}
		return playerRelativeVerticalLocationEnum;
	}

	protected Direction lookUpDirection(char directionChar) {
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
		return directionEnum;
	}

	protected PieceType lookUpPieceType(char pieceAbbreviation) {
		switch (pieceAbbreviation) {
		case 'C':
			return PieceType.CANNON;
		case 'K':
			return PieceType.KING;
		case 'M':
			return PieceType.ELEPHANT;
		case 'N':
			return PieceType.HORSE;
		case 'P':
			return PieceType.PAWN;
		case 'R':
			return PieceType.ROOK;
		case 'S':
			return PieceType.ADVISER;
		default:
			assert false;
		}
		return null;
	}

}
