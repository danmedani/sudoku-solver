/**
 * 
 * Medani
 * November 2013
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class BruteSolver {

    private int[][] board;
    private boolean[][] initialPieceSet;

    public void printBoard() {
	for (int y = 0; y < 9; y ++) {
	    for (int x = 0; x < 9; x ++) {
		if (board[x][y] == 0) {
		    System.out.print("x ");
		}
		else {
		    System.out.print(board[x][y] + " ");
		}
	    }
	    System.out.println();
	}
    }

    public BruteSolver(String fileName) {
	// Init board based on file.
	board = new int[9][9];
	initialPieceSet = new boolean[9][9];
	
	try {
	    BufferedReader buff = new BufferedReader(new FileReader(fileName));
	    String line;
	    String[] cArr = new String[9];
	    int x = 0;
	    int y = 0;
	    
	    // Read through file, populate board & initialPieceSet
	    while ((line = buff.readLine()) != null) {
		cArr = line.split(" ");
		for (int i = 0; i < 9; i ++) {
		    if (cArr[i].equals("x")) {
			board[x][y] = 0;
			initialPieceSet[x][y] = false;
		    }
		    else {
			board[x][y] = Integer.parseInt(cArr[i]);
			initialPieceSet[x][y] = true;
		    }
		    x += 1;
		}
		y += 1;
		x = 0;
	    }
	} catch (Exception e) {
	    System.out.println("ERROR. " + e);
	}
    }
    
    public void solve() {
	solvePiece(0, 0);
    }
    
    private boolean solvePiece(int x, int y) {
	// Run through the board left to right, then top to bottom like a book
	int nextX = getNextX(x, y);
	int nextY = getNextY(x, y);
	
	if (initialPieceSet[x][y]) {
	    if (nextX >= 0) {
		// Move along to the next piece.
		return solvePiece(nextX, nextY);
	    }
	    else {
		return true;
	    }
	}
	else {
	    // Try every number 1..9
	    for (int i = 1; i < 10; i ++) {
		board[x][y] = i;
		if (!illegalBoard(x, y)) {
		    if (nextX < 0) {
			return true;
		    }
		    else {
			// Recurse
			if (solvePiece(nextX, nextY)) {
			    return true;
			}
		    }
		}
	    }
	    // reset the spot
	    board[x][y] = 0;
	    return false;
	}
    }

    private boolean illegalBoard(int xAy, int yAy) {
	int val = board[xAy][yAy];

	// Checks horizonal rows
	for (int x = 0; x < 9; x ++)
	    if (x != xAy)
		if (board[x][yAy] == val)
		    return true;

	// Checks vertical rows	
	for (int y = 0; y < 9; y ++)
	    if (y != yAy)
		if (board[xAy][y] == val)
		    return true;
	
	// Checks local box
	int bigX = xAy / 3;
	int bigY = yAy / 3;
	for (int x = bigX * 3; x < (bigX * 3) + 3; x ++)
	    for (int y = bigY * 3; y < (bigY * 3) + 3; y ++)
		if (x != xAy || y != yAy)
		    if (board[x][y] == val)
			return true;

	// Nothing illegal here..
	return false;
    }


    public int getNextX(int x, int y) {
	if (x < 8)
	    return x + 1;
	else {
	    if (y == 8) {
		return -1;
	    } else {
		return 0;
	    }
	}
    }

    public int getNextY(int x, int y) {
	if (x < 8)
	    return y;
	else {
	    if (y == 8) {
		return -1;
	    } else {
		return y + 1;
	    }
	}
    }
    
    public static void main(String[] args) {
	// Init, print the board
	BruteSolver solver = new BruteSolver(args[0]);
	solver.printBoard();
	System.out.println();

	// Solve, print the board
	solver.solve();
	solver.printBoard();
    }
    
}