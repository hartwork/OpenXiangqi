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

public class BoardLocation {
	public BoardLocation(int horizontal, int vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	private int horizontal;
	private int vertical;

	public int getHorizontal() {
		return horizontal;
	}

	public int getVertical() {
		return vertical;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BoardLocation) {
			BoardLocation other = (BoardLocation) o;
			return (other.horizontal == horizontal)
					&& (other.vertical == vertical);
		}
		return false;

	}

}
