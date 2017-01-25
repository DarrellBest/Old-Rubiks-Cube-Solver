package Main;

import Objects.Cube;

/*
 * Darrell Best
 * bbest@g.clemson.edu
 * 5/3/2016
 * 
 * Rubik's cube solver demonstrating the breadth first search algorithm and threading in Java
 * This algorithm does not just find a solution, but the optimal solution in the lowest amount of moves possible in the given state!
 * 
 * This program was written to demonstrate the understanding of OOP, the Java language, Java conventions, advanced algorithm programming, and threading.
 */

public class MainDriver {

	public static void main(String[] args) {
		BreadthFirstSearch bfs;
		Cube main;
		String randomSteps = "";

		main = new Cube();

		// Works fine if set to 6 random moves, but the solver runs out of
		// memory with 7 or more
		// Average runtime with randomization is 1 minute
		randomSteps = main.randomize(5);

		if (randomSteps.length() > 0)
			System.out.println("Mix: " + main.formatString(randomSteps));

		// Start Thread using BFS on Main
		bfs = new BreadthFirstSearch(main, "0");
		bfs.start();

		// Runs on a single thread for demonstration purposes only.

	}
}
