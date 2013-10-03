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
import org.openxiangqi.core.notations.Notation.HorizontalConfiguration;

public class HtLauNotationParser {
	private static String REGEX_PATTERN = "^((([CSKNMPR])([1-9]))|(([fr])([CSNMPR])))([fbh])([1-9])$";

	public enum Strictness {
		STRICT, LOOSE
	}

	public Notation parse(String notation, Strictness strictness)
			throws MalformedNotation {
		Pattern p = Pattern.compile(REGEX_PATTERN, (strictness == Strictness.STRICT) ? 0
				: Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(notation);

		if (!m.matches()) {
			throw new MalformedNotation();
		}

		HorizontalConfiguration horizontalConfiguration = (m.group(3) == null) ? HorizontalConfiguration.SAME_VERTICAL_LINE
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
			} else if (playerRelativeVerticalLocationChar == 'r') {
				playerRelativeVerticalLocationEnum = LooseVerticalLocation.REAR;
			} else {
				assert false;
			}
		} else {
			pieceAbbreviation = Character.toUpperCase(m.group(3).charAt(0));
			playerRelativeHorizontalLocation = Integer.parseInt(m.group(4));
		}

		return new Notation(pieceAbbreviation, horizontalConfiguration,
				playerRelativeHorizontalLocation,
				playerRelativeVerticalLocationEnum, directionEnum, parameter);
	}

}
