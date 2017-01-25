package Objects;
/*
 * Darrell Best
 * 11/26/2015
 * 
 * Used to represent a corner piece on the cube.
 * 
 */

import java.util.ArrayList;

import Interfaces.PieceInterface;

public class Corner implements PieceInterface {

	private Sticker sideOne;
	private Sticker sideTwo;
	private Sticker sideThree;
	private String type;
	private int position;

	public Corner(Sticker one, Sticker two, Sticker three, String type,
			int position) {
		this.sideOne = one;
		this.sideTwo = two;
		this.sideThree = three;
		this.type = type;
		this.position = position;
	}

	@Override
	public ArrayList<Sticker> getStickers() {
		ArrayList<Sticker> retVal = new ArrayList<Sticker>();
		retVal.add(sideOne);
		retVal.add(sideTwo);
		retVal.add(sideThree);
		return retVal;
	}

	@Override
	public void setStickers(ArrayList<Sticker> stickers) {
		this.sideOne.setColor(stickers.get(0).getColor());
		this.sideTwo.setColor(stickers.get(1).getColor());
		this.sideThree.setColor(stickers.get(2).getColor());
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
		retVal = retVal.concat(sideOne.toString());
		retVal = retVal.concat(sideTwo.toString());
		retVal = retVal.concat(sideThree.toString() + "]");
		return retVal;
	}

}
