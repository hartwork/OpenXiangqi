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

import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.geometry.PlayerRelativeLocation.LooseVerticalLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;
import org.openxiangqi.core.notations.Notation.HorizontalConfiguration;
import org.openxiangqi.core.pieces.Piece.PieceType;

public abstract class NotationParserBase implements NotationParser {

	public enum Strictness {
		STRICT, LOOSE
	}

	public abstract Notation parse(String notation, Strictness strictness)
			throws MalformedNotation;

	protected Notation assemble(String pieceAbbreviationFirst,
			String horizontalLocationString,
			String relativeVerticalLocationString,
			String pieceAbbreviationSecond, String directionString,
			String parameterString) {
		HorizontalConfiguration horizontalConfiguration = (relativeVerticalLocationString != null) ? HorizontalConfiguration.SAME_VERTICAL_LINE
				: HorizontalConfiguration.DIFFERENT_VERTICAL_LINES;

		char directionChar = Character.toLowerCase(directionString.charAt(0));
		Direction directionEnum = lookUpDirection(directionChar);
		int parameter = Integer.parseInt(parameterString);

		char pieceAbbreviation = (char) -1;
		int playerRelativeHorizontalLocation = -1;
		LooseVerticalLocation playerRelativeVerticalLocationEnum = null;
		if (horizontalConfiguration == HorizontalConfiguration.SAME_VERTICAL_LINE) {
			pieceAbbreviation = Character.toUpperCase(pieceAbbreviationSecond
					.charAt(0));
			char playerRelativeVerticalLocationChar = Character
					.toLowerCase(relativeVerticalLocationString.charAt(0));
			playerRelativeVerticalLocationEnum = lookUpVerticalLocation(

			playerRelativeVerticalLocationChar);
		} else {
			pieceAbbreviation = Character.toUpperCase(pieceAbbreviationFirst
					.charAt(0));
			playerRelativeHorizontalLocation = Integer
					.parseInt(horizontalLocationString);
		}

		PieceType pieceType = lookUpPieceType(pieceAbbreviation);

		return new Notation(pieceType, horizontalConfiguration,
				playerRelativeHorizontalLocation,
				playerRelativeVerticalLocationEnum, directionEnum, parameter);
	}

	protected abstract LooseVerticalLocation lookUpVerticalLocation(
			char playerRelativeVerticalLocationChar);

	protected abstract Direction lookUpDirection(char directionChar);

	public abstract PieceType lookUpPieceType(char pieceAbbreviation);

	public abstract char lookUpPieceAbbreviation(PieceType pieceType);
}
