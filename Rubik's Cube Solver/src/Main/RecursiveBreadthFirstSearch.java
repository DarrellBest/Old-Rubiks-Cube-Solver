
package Main;

/*
 * Darrell Best
 * 5/3/2016
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import Objects.Cube;

public class RecursiveBreadthFirstSearch implements Runnable {

	public class Load implements Runnable {
		@Override
		public void run() {
			load();
		}
	}

	public class Search implements Runnable {
		@Override
		public void run() {
			search();
		}
	}

	private Thread t;
	private Queue<String> queue;
	private Cube cube;
	private ArrayList<String> solutions;
	private String threadName;
	private int queueSize;

	public RecursiveBreadthFirstSearch(Cube cube, String threadName) {
		solutions = new ArrayList<>();
		this.cube = cube;
		this.threadName = threadName;

		// if (threadName.contentEquals("0"))
		// queue = new LinkedList<>(Arrays.asList(" "));
	}

	public void load() {
		// Do nothing
	}

	public void search() {
		long startTime = System.nanoTime();
		String retVal = "";
		String moves = "";

		ArrayList<String> nextMove = new ArrayList<>();

		if (cube.isSolved()) {
			// already in a solved state
			queue.clear();
			System.out.println("Already in a solved state");
			System.exit(2);
		}

		while (!queue.isEmpty()) {
			nextMove.clear();
			nextMove.addAll(Arrays.asList("F", "f", "B", "b", "L", "l", "R", "r", "U", "u", "D", "d"));
			// Get next moves
			moves = queue.remove();
			int maxSteps = cube.getMaxSteps();

			if (moves.length() > maxSteps)
				queue.clear();

			// Remove inverse of last move as it is irrelevant
			// ex R R' just reverses R
			for (char c : moves.substring(moves.length() - 1).toCharArray()) {
				if (Character.isUpperCase(c))
					nextMove.remove(String.valueOf(Character.toLowerCase(c)));
				else
					nextMove.remove(String.valueOf(Character.toUpperCase(c)));
			}

			// Add all children to the queue
			// Don't add anything else to the list if the size is larger than a
			// solution already found
			if (moves.length() < maxSteps - 1)
				for (String next : nextMove)
					if (moves.concat(next).length() <= 3)
						queue.add(moves.concat(next));
					else if (!isDuplicate(moves.concat(next)))
						queue.add(moves.concat(next));

			// Performs moves
			cube.performMoves(moves);

			if (cube.isSolved()) {
				retVal = new String(cube.formatString(moves));
				// System.out.println("Solution: " + retVal);
				solutions.add(retVal);
				// cube.setMaxSteps(moves.length());
				cube.setMaxSteps(0);
				queueSize = queue.size();
			} else
				// Undo moves
				cube.performInverse(moves);

		}

		// print solutions on thread exit
		for (String solution : solutions)
			System.out.println("Solution: " + solution);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime) / 1000000;
		System.out.println("Size of queue: " + queueSize);
		System.out.println("Runtime in seconds: " + duration / 1000 + "." + String.format("%03d", (duration % 1000)));
		// Terminates thread
		System.exit(10);
	}

	private boolean isDuplicate(String moves) {
		// if palindrome, then moves reset themselves (not a solution)
		boolean retVal = false;
		// checking for pal
		retVal = moves.equals(new StringBuilder(moves).reverse().toString());

		// if not pal then check for redundant moves
		if (!retVal) {
			String[] temp1 = { "F", "L", "U", "f", "l", "u" };
			String[] temp2 = { "f", "l", "u", "F", "L", "U" };
			String[] temp3 = { "B", "R", "D", "b", "r", "d" };
			String[] temp4 = { "b", "r", "d", "B", "R", "D" };
			for (int i = 0; i < 6; i++) {
				if (moves.contains(new StringBuilder().append(temp3[i]).append(temp1[i]).append(temp4[i]))) {
					retVal = true;
					break;
				} else if (moves.contains(new StringBuilder().append(temp4[i]).append(temp1[i]).append(temp3[i]))) {
					retVal = true;
					break;
				} else if (moves.contains(new StringBuilder().append(temp3[i]).append(temp2[i]).append(temp4[i]))) {
					retVal = true;
					break;
				} else if (moves.contains(new StringBuilder().append(temp4[i]).append(temp2[i]).append(temp3[i]))) {
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}

	@Override
	public void run() {
		this.search();
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public ArrayList<String> getSolutions() {
		return this.solutions;
	}

}
