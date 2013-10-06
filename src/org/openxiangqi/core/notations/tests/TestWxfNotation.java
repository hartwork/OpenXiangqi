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
package org.openxiangqi.core.notations.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openxiangqi.core.exceptions.MalformedNotation;
import org.openxiangqi.core.geometry.PlayerRelativeLocation.LooseVerticalLocation;
import org.openxiangqi.core.geometry.PlayerRelativeMove.Direction;
import org.openxiangqi.core.notations.WxfNotationParser;
import org.openxiangqi.core.notations.Notation;
import org.openxiangqi.core.notations.Notation.HorizontalConfiguration;
import org.openxiangqi.core.notations.NotationParserBase.Strictness;
import org.openxiangqi.core.pieces.Piece.PieceType;

public class TestWxfNotation {

	@Test
	public void testStraightValidForward() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R1+2",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.DIFFERENT_VERTICAL_LINES, 1, null,
				Direction.FORWARD, 2);
		assertEquals(receivedNotation, expectedNotation);
	}

	@Test
	public void testStraightValidBackward() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R3-4",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.DIFFERENT_VERTICAL_LINES, 3, null,
				Direction.BACKWARD, 4);
		assertEquals(receivedNotation, expectedNotation);
	}

	@Test
	public void testStraightValidHorizontalDot() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R5.6",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.DIFFERENT_VERTICAL_LINES, 5, null,
				Direction.HORIZONTAL, 6);
		assertEquals(receivedNotation, expectedNotation);
	}

	@Test
	public void testStraightValidHorizontalEqual() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R5=6",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.DIFFERENT_VERTICAL_LINES, 5, null,
				Direction.HORIZONTAL, 6);
		assertEquals(receivedNotation, expectedNotation);
	}

	@Test
	public void testSameVerticalLineFront() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R+.7",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.SAME_VERTICAL_LINE, -1,
				LooseVerticalLocation.FRONT, Direction.HORIZONTAL, 7);
		assertEquals(receivedNotation, expectedNotation);
	}

	@Test
	public void testSameVerticalLineRear() throws MalformedNotation {
		Notation receivedNotation = new WxfNotationParser().parse("R-.8",
				Strictness.STRICT);
		Notation expectedNotation = new Notation(PieceType.CHARIOT,
				HorizontalConfiguration.SAME_VERTICAL_LINE, -1,
				LooseVerticalLocation.REAR, Direction.HORIZONTAL, 8);
		assertEquals(receivedNotation, expectedNotation);
	}

}
