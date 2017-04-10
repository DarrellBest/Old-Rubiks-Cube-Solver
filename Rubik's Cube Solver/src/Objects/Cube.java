package Objects;

/*
 * Darrell Best
 * 5/3/2016
 * 
 * 3x3x3 rubiks cube represented by 26 pieces and operations that
 * can be performed on it.
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import Comparators.PieceComparator;
import Interfaces.PieceInterface;

public class Cube {

	private ArrayList<PieceInterface> pieces;
	private String solution;
	private Comparator<PieceInterface> comp;
	private int maxSteps;

	public Cube() {
		generate();
		setSolution();
	}

	public void reset() {
		pieces.clear();
		generate();
	}

	private void generate() {
		this.pieces = new ArrayList<>();
		this.comp = new PieceComparator();
		this.setMaxSteps(22);

		generateCenters();
		generateCorners();
		generateEdges();

		Collections.sort(pieces, comp);
	}

	// returns a string that represents the current state of the cubed
	public String getCurrentState() {
		String retVal = new String();
		for (PieceInterface piece : pieces) {
			retVal = retVal.concat(piece.toString());
		}
		return retVal;
	}

	public int getMaxSteps() {
		return maxSteps;
	}

	public String getSolution() {
		return this.solution;
	}

	public void setMaxSteps(int maxSteps) {
		this.maxSteps = maxSteps;
	}

	private void setSolution() {
		solution = getCurrentState();
	}

	private void setSolution(String solution) {
		this.solution = solution;
	}

	// Checks to see if the cube is solved
	public Boolean isSolved() {
		Boolean retVal = false;
		if (solution.compareTo(getCurrentState()) == 0)
			retVal = true;
		return retVal;
	}

	// puts moves string into a readable form
	public String formatString(String retVal) {
		StringBuilder temp = new StringBuilder();
		for (char c : retVal.toCharArray()) {
			if (Character.isLowerCase(c))
				temp.append(Character.toUpperCase(c) + "'");
			else
				temp.append(c + " ");
		}
		return temp.toString().trim();
	}

	// takes a string of moves then finds the inverse of those and returns it
	public String reverseMoves(String moves) {
		StringBuilder retVal = new StringBuilder();
		for (char move : moves.toCharArray()) {
			if (Character.isUpperCase(move))
				retVal.append(Character.toLowerCase(move));
			else
				retVal.append(Character.toUpperCase(move));
		}
		return retVal.reverse().toString();
	}

	public void performInverse(String moves) {
		performMoves(reverseMoves(moves));
	}

	// Translates a string of moves and performs each move in order
	// This can be performed using a cascading ifelse conditions, however I use
	// case statements for readability
	public void performMoves(String moves) {
		for (char move : moves.toCharArray()) {
			switch (move) {
			case 'F': // Moves the Front face clockwise, 45 degrees
				frontFaceclockwise();
				break;
			case 'f': // Moves the Front face counterclockwise, 45 degrees
				frontFaceCounterClockwise();
				break;
			case 'B': // Moves the Back face clockwise, 45 degrees
				backFaceClockwise();
				break;
			case 'b': // Moves the Back face counterclockwise, 45 degrees
				backFaceCounterClockwise();
				break;
			case 'L': // Moves the Left face clockwise, 45 degrees
				leftFaceClockwise();
				break;
			case 'l': // Moves the Left face counterclockwise, 45 degrees
				leftFaceCounterClockwise();
				break;
			case 'R': // Moves the Right face clockwise, 45 degrees
				rightFaceClockwise();
				break;
			case 'r': // Moves the Right face counterclockwise, 45 degrees
				rightFaceCounterClockwise();
				break;
			case 'U': // Moves the Upper face clockwise, 45 degrees
				upperFaceClockwise();
				break;
			case 'u': // Moves the Upper face counterclockwise, 45 degrees
				upperFaceCounterClockwise();
				break;
			case 'D': // Moves the Lower face clockwise, 45 degrees
				downFaceClockwise();
				break;
			case 'd': // Moves the Lower face counterclockwise, 45 degrees
				downFaceCounterClockwise();
				break;
			}
		}

	}

	public String randomize(int i) {
		String retVal = "";

		for (int j = 0; j < i; j++) {
			// Generate an int from 0 to 11
			int x = (int) Math.floor(Math.random() * 12);

			if (x == 0) {
				frontFaceclockwise();
				retVal = retVal.concat("F");
			} else if (x == 1) {
				frontFaceCounterClockwise();
				retVal = retVal.concat("f");
			} else if (x == 2) {
				backFaceClockwise();
				retVal = retVal.concat("B");
			} else if (x == 3) {
				backFaceCounterClockwise();
				retVal = retVal.concat("b");
			} else if (x == 4) {
				upperFaceClockwise();
				retVal = retVal.concat("U");
			} else if (x == 5) {
				upperFaceCounterClockwise();
				retVal = retVal.concat("u");
			} else if (x == 6) {
				downFaceClockwise();
				retVal = retVal.concat("D");
			} else if (x == 7) {
				downFaceCounterClockwise();
				retVal = retVal.concat("d");
			} else if (x == 8) {
				leftFaceClockwise();
				retVal = retVal.concat("L");
			} else if (x == 9) {
				leftFaceCounterClockwise();
				retVal = retVal.concat("l");
			} else if (x == 10) {
				rightFaceClockwise();
				retVal = retVal.concat("R");
			} else if (x == 11) {
				rightFaceCounterClockwise();
				retVal = retVal.concat("r");
			}
		}
		return retVal;
	}

	// Algorithms
	public void tPerm() {

	}

	// fixes orientation of pieces after they move
	private void updateStickers(ArrayList<PieceInterface> pieces, ArrayList<String> notations) {
		for (PieceInterface piece : pieces) {
			for (Sticker sticker : piece.getStickers()) {
				// Update face
				if (sticker.getFace().compareTo(notations.get(0)) == 0)
					sticker.setFace(notations.get(1));
				else if (sticker.getFace().compareTo(notations.get(1)) == 0)
					sticker.setFace(notations.get(2));
				else if (sticker.getFace().compareTo(notations.get(2)) == 0)
					sticker.setFace(notations.get(3));
				else if (sticker.getFace().compareTo(notations.get(3)) == 0)
					sticker.setFace(notations.get(0));
			}
		}
	}

	// Front clockwise rotation
	private int frontFaceclockwise() {
		ArrayList<Integer> frontFace = new ArrayList<>(Arrays.asList(1, 3, 9, 7, 2, 6, 8, 4));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("l", "u", "r", "d"));
		clockwise(frontFace, faces);
		return 1;
	}

	// Front counter clockwise
	private int frontFaceCounterClockwise() {
		ArrayList<Integer> frontFace = new ArrayList<>(Arrays.asList(1, 3, 9, 7, 2, 6, 8, 4));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("d", "r", "u", "l"));
		counterClockwise(frontFace, faces);
		return 1;
	}

	// back clockwise rotation
	private int backFaceClockwise() {
		ArrayList<Integer> backFace = new ArrayList<>(Arrays.asList(20, 18, 24, 26, 19, 21, 25, 23));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("r", "u", "l", "d"));
		clockwise(backFace, faces);
		return 1;
	}

	// back counter clockwise rotation
	private int backFaceCounterClockwise() {
		ArrayList<Integer> backFace = new ArrayList<>(Arrays.asList(20, 18, 24, 26, 19, 21, 25, 23));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("d", "l", "u", "r"));
		counterClockwise(backFace, faces);
		return 1;
	}

	// up clockwise rotation
	private int upperFaceClockwise() {
		ArrayList<Integer> upFace = new ArrayList<>(Arrays.asList(18, 20, 3, 1, 19, 12, 2, 10));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("r", "f", "l", "b"));
		clockwise(upFace, faces);
		return 1;
	}

	// up counter clockwise rotation
	private int upperFaceCounterClockwise() {
		ArrayList<Integer> upFace = new ArrayList<>(Arrays.asList(18, 20, 3, 1, 19, 12, 2, 10));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("b", "l", "f", "r"));
		counterClockwise(upFace, faces);
		return 1;
	}

	// down clockwise rotation
	private int downFaceClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(7, 9, 26, 24, 8, 17, 25, 15));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("r", "f", "l", "b"));
		clockwise(downFace, faces);
		return 1;
	}

	// down counter clockwise rotation
	private int downFaceCounterClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(7, 9, 26, 24, 8, 17, 25, 15));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("b", "l", "f", "r"));
		counterClockwise(downFace, faces);
		return 1;
	}

	// left clockwise rotation
	private int leftFaceClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(18, 1, 7, 24, 10, 4, 15, 21));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("u", "f", "d", "b"));
		clockwise(downFace, faces);
		return 1;
	}

	// left counter clockwise rotation
	private int leftFaceCounterClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(18, 1, 7, 24, 10, 4, 15, 21));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("b", "d", "f", "u"));
		counterClockwise(downFace, faces);
		return 1;
	}

	// right clockwise rotation
	private int rightFaceClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(3, 20, 26, 9, 12, 23, 17, 6));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("f", "u", "b", "d"));
		clockwise(downFace, faces);
		return 1;
	}

	// right counter clockwise rotation
	private int rightFaceCounterClockwise() {
		ArrayList<Integer> downFace = new ArrayList<>(Arrays.asList(3, 20, 26, 9, 12, 23, 17, 6));
		ArrayList<String> faces = new ArrayList<>(Arrays.asList("d", "b", "u", "f"));
		counterClockwise(downFace, faces);
		return 1;
	}

	// Clockwise rotation
	private void clockwise(ArrayList<Integer> positions, ArrayList<String> faces) {
		ArrayList<PieceInterface> corners, edges;
		PieceInterface cornerOne, cornerTwo, cornerThree, cornerFour;
		PieceInterface edgeOne, edgeTwo, edgeThree, edgeFour;

		// Get Corners and update rotated positions
		(cornerOne = pieces.get(positions.get(0) - 1)).setPosition(positions.get(1));
		(cornerTwo = pieces.get(positions.get(1) - 1)).setPosition(positions.get(2));
		(cornerThree = pieces.get(positions.get(2) - 1)).setPosition(positions.get(3));
		(cornerFour = pieces.get(positions.get(3) - 1)).setPosition(positions.get(0));

		// Setup list to update faces
		corners = new ArrayList<>(Arrays.asList(cornerOne, cornerTwo, cornerThree, cornerFour));

		// update the notations of stickers of the corners
		updateStickers(corners, faces);

		// Get edges and update rotated positions
		(edgeOne = pieces.get(positions.get(4) - 1)).setPosition(positions.get(5));
		(edgeTwo = pieces.get(positions.get(5) - 1)).setPosition(positions.get(6));
		(edgeThree = pieces.get(positions.get(6) - 1)).setPosition(positions.get(7));
		(edgeFour = pieces.get(positions.get(7) - 1)).setPosition(positions.get(4));

		// Setup list to update faces
		edges = new ArrayList<>(Arrays.asList(edgeOne, edgeTwo, edgeThree, edgeFour));

		// update the notations of stickers of the edges
		updateStickers(edges, faces);

		// adjust list to reflect new positions
		Collections.sort(pieces, comp);
	}

	// counter clockwise rotation
	private void counterClockwise(ArrayList<Integer> positions, ArrayList<String> faces) {
		ArrayList<PieceInterface> corners, edges;
		PieceInterface cornerOne, cornerTwo, cornerThree, cornerFour;
		PieceInterface edgeOne, edgeTwo, edgeThree, edgeFour;

		// Get Corners and update rotated positions
		(cornerOne = pieces.get(positions.get(0) - 1)).setPosition(positions.get(3));
		(cornerTwo = pieces.get(positions.get(1) - 1)).setPosition(positions.get(0));
		(cornerThree = pieces.get(positions.get(2) - 1)).setPosition(positions.get(1));
		(cornerFour = pieces.get(positions.get(3) - 1)).setPosition(positions.get(2));

		// Setup list to update faces
		corners = new ArrayList<>(Arrays.asList(cornerOne, cornerTwo, cornerThree, cornerFour));

		// update the notations of stickers of the corners
		updateStickers(corners, faces);

		// Get edges and update rotated positions
		(edgeOne = pieces.get(positions.get(4) - 1)).setPosition(positions.get(7));
		(edgeTwo = pieces.get(positions.get(5) - 1)).setPosition(positions.get(4));
		(edgeThree = pieces.get(positions.get(6) - 1)).setPosition(positions.get(5));
		(edgeFour = pieces.get(positions.get(7) - 1)).setPosition(positions.get(6));

		// Setup list to update faces
		edges = new ArrayList<>(Arrays.asList(edgeOne, edgeTwo, edgeThree, edgeFour));

		// update the notations of stickers of the edges
		updateStickers(edges, faces);

		// adjust list to reflect new positions
		Collections.sort(pieces, comp);
	}

	private void generateCenters() {
		Sticker white, yellow, orange, red, blue, green;

		white = new Sticker("white", "f");
		pieces.add(new Center(white, "center", 5));

		yellow = new Sticker("yellow", "b");
		pieces.add(new Center(yellow, "center", 22));

		green = new Sticker("green", "l");
		pieces.add(new Center(green, "center", 13));

		blue = new Sticker("blue", "r");
		pieces.add(new Center(blue, "center", 14));

		orange = new Sticker("orange", "u");
		pieces.add(new Center(orange, "center", 11));

		red = new Sticker("red", "d");
		pieces.add(new Center(red, "center", 16));
	}

	private void generateCorners() {
		Sticker white, yellow, orange, red, blue, green;

		white = new Sticker("white", "f");
		green = new Sticker("green", "l");
		orange = new Sticker("orange", "u");
		pieces.add(new Corner(white, green, orange, "corner", 1));

		white = new Sticker("white", "f");
		orange = new Sticker("orange", "u");
		blue = new Sticker("blue", "r");
		pieces.add(new Corner(white, orange, blue, "corner", 3));

		white = new Sticker("white", "f");
		blue = new Sticker("blue", "r");
		red = new Sticker("red", "d");
		pieces.add(new Corner(white, blue, red, "corner", 9));

		white = new Sticker("white", "f");
		red = new Sticker("red", "d");
		green = new Sticker("green", "l");
		pieces.add(new Corner(white, red, green, "corner", 7));

		yellow = new Sticker("yellow", "b");
		blue = new Sticker("blue", "r");
		orange = new Sticker("orange", "u");
		pieces.add(new Corner(yellow, blue, orange, "corner", 20));

		yellow = new Sticker("yellow", "b");
		orange = new Sticker("orange", "u");
		green = new Sticker("green", "l");
		pieces.add(new Corner(yellow, orange, green, "corner", 18));

		yellow = new Sticker("yellow", "b");
		green = new Sticker("green", "l");
		red = new Sticker("red", "d");
		pieces.add(new Corner(yellow, green, red, "corner", 24));

		yellow = new Sticker("yellow", "b");
		red = new Sticker("red", "d");
		blue = new Sticker("blue", "r");
		pieces.add(new Corner(yellow, red, blue, "corner", 26));
	}

	private void generateEdges() {
		Sticker white, yellow, orange, red, blue, green;

		white = new Sticker("white", "f");
		green = new Sticker("green", "l");
		pieces.add(new Edge(white, green, "edge", 4));

		white = new Sticker("white", "f");
		orange = new Sticker("orange", "u");
		pieces.add(new Edge(white, orange, "edge", 2));

		white = new Sticker("white", "f");
		blue = new Sticker("blue", "r");
		pieces.add(new Edge(white, blue, "edge", 6));

		white = new Sticker("white", "f");
		red = new Sticker("red", "d");
		pieces.add(new Edge(white, red, "edge", 8));

		orange = new Sticker("orange", "u");
		green = new Sticker("green", "l");
		pieces.add(new Edge(orange, green, "edge", 10));

		orange = new Sticker("orange", "u");
		blue = new Sticker("blue", "r");
		pieces.add(new Edge(orange, blue, "edge", 12));

		red = new Sticker("red", "d");
		green = new Sticker("green", "l");
		pieces.add(new Edge(red, green, "edge", 15));

		red = new Sticker("red", "d");
		blue = new Sticker("blue", "r");
		pieces.add(new Edge(red, blue, "edge", 17));

		yellow = new Sticker("yellow", "b");
		blue = new Sticker("blue", "r");
		pieces.add(new Edge(yellow, blue, "edge", 23));

		yellow = new Sticker("yellow", "b");
		orange = new Sticker("orange", "u");
		pieces.add(new Edge(yellow, orange, "edge", 19));

		yellow = new Sticker("yellow", "b");
		green = new Sticker("green", "l");
		pieces.add(new Edge(yellow, green, "edge", 21));

		yellow = new Sticker("yellow", "b");
		red = new Sticker("red", "d");
		pieces.add(new Edge(yellow, red, "edge", 25));
	}

	@Override
	public String toString() {
		return this.getCurrentState();
	}

}
