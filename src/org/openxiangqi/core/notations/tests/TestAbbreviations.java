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

import static org.junit.Assert.*;

import org.junit.Test;
import org.openxiangqi.core.notations.HtLauNotationParser;
import org.openxiangqi.core.notations.NotationParser;
import org.openxiangqi.core.notations.WxfNotationParser;
import org.openxiangqi.core.pieces.Piece;
import org.openxiangqi.core.pieces.Piece.PieceType;

public class TestAbbreviations {

	@Test
	public void test() {
		NotationParser[] notationParsers = new NotationParser[2];
		notationParsers[0] = new HtLauNotationParser();
		notationParsers[1] = new WxfNotationParser();

		for (NotationParser notationParser : notationParsers) {
			for (PieceType expectedPieceType : Piece.ALL_PIECE_TYPES) {
				char abbreviation = notationParser.lookUpPieceAbbreviation(expectedPieceType);
				PieceType receivedPiecetype = notationParser.lookUpPieceType(abbreviation);
				assertEquals(receivedPiecetype, expectedPieceType);
			}
		}
	}

}
