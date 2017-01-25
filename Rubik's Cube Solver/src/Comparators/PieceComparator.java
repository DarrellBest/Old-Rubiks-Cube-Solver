package Comparators;
/*
 * Darrell Best
 * 11/26/2015
 * 
 * Used to keep pieces in the correct order in the list
 * 
 * The index of the piece + 1 is the position of the piece on the cube
 */

import java.util.Comparator;

import Interfaces.PieceInterface;

public class PieceComparator implements Comparator<PieceInterface> {

	public PieceComparator() {
		// Do nothing
	}

	@Override
	public int compare(PieceInterface one, PieceInterface two) {
		int retVal = 0;

		if (one.getPosition() < two.getPosition())
			retVal = -1;
		if (one.getPosition() > two.getPosition())
			retVal = 1;

		return retVal;
	}

}
