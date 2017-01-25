package Interfaces;
/*
 * Darrell Best
 * 11/26/2015
 * 
 * Used to keep abstract different pieces as to use them all in the 
 * same array list.
 * 
 */

import java.util.ArrayList;

import Objects.Sticker;

public interface PieceInterface {
	// Returns the type of piece: Corner, Edge, or Center of type String
	public String getType();

	// Used to keep track of the location of the piece on the cube
	public void setPosition(int position);

	// Returns an int 1 - 26 to
	public int getPosition();

	// returns 1 2 or 3 stickers depending on the type
	public ArrayList<Sticker> getStickers();

	// sets 1 2 or 3 stickers depending on the type
	public void setStickers(ArrayList<Sticker> stickers);
}