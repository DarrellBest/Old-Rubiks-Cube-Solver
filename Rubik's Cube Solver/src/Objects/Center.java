package Objects;
/*
 * Darrell Best
 * 11/26/2015
 * 
 * Used to represent a center piece on the cube.
 * 
 */

import java.util.ArrayList;

import Interfaces.PieceInterface;

public class Center implements PieceInterface {

	private Sticker sideOne;
	private String type;
	private int position;

	public Center(Sticker one, String type, int position) {

		this.sideOne = one;
		this.type = type;
		this.position = position;
	}

	@Override
	public ArrayList<Sticker> getStickers() {
		ArrayList<Sticker> retVal = new ArrayList<Sticker>();
		retVal.add(sideOne);
		return retVal;
	}

	@Override
	public void setStickers(ArrayList<Sticker> stickers) {
		this.sideOne.setColor(stickers.get(0).getColor());
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		String retVal = new String(this.position + "[" + this.type + ":");
		retVal = retVal.concat(sideOne.toString() + "]");
		return retVal;
	}

}
