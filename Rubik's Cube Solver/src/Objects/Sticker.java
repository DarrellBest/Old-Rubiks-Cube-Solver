package Objects;
/*
 *  Darrell Best
 *  11/22/2015
 *  
 *  Each piece has 1 to 30 of these objects. They are use to keep track
 *  of the orientation of the piece on the cube.
 * 
 */

public class Sticker {

	private String color;
	private String face;

	public Sticker(String color, String face) {
		this.color = color;
		this.face = face;
	}

	public String getColor() {
		return this.color;
	}

	public String getFace() {
		return this.face;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@Override
	public String toString() {
		String retVal = new String();
		retVal = retVal.concat(this.color);
		retVal = retVal.concat("<" + this.face + ">");
		return retVal;
	}

}
