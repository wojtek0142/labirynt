package maze;

import java.util.Random;

public class Maze {
    private final byte[][] grid;
    private final int size;

    public Maze(int size){
        this.grid = new byte[size][size];
        this.size = size;
        generate();
    }
    // Konstruktor do ładowania z pliku
    public Maze(byte[][] grid){
        this.grid = grid;
        this.size = grid.length;
    }

    public void generate(){
        Random random = new Random();
        int maxFrontier = size * size;
        int [][] frontier = new int[maxFrontier][2];
        int frontierCount = 0;

        int currentRow = random.nextInt(size - 2) + 1;
        int currentCol = random.nextInt(size - 2) + 1;
        if (currentRow % 2 == 0) currentRow++;
        if (currentCol % 2 == 0) currentCol++;
        grid[currentRow][currentCol] = 1;

        frontierCount = addFrontiers(frontier, frontierCount, currentRow, currentCol);
        while(frontierCount > 0){
            // Losowanie komórki frontowej
            int idx = random.nextInt(frontierCount);
            int newRow = frontier[idx][0];
            int newCol = frontier[idx][1];

            frontier[idx][0] = frontier[frontierCount - 1][0];
            frontier[idx][1] = frontier[frontierCount - 1][1];
            frontierCount--;

            if (grid[newRow][newCol] == 1) continue;
            grid[newRow][newCol] = 1;

            int[] neighbor = findNeighbor(newRow, newCol);
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];

            if (neighborRow == newRow) {
                int midCol = (newCol + neighborCol) / 2;
                grid[newRow][midCol] = 1;
            } else {
                int midRow = (newRow + neighborRow) / 2;
                grid[midRow][newCol] = 1;
            }
            frontierCount = addFrontiers(frontier, frontierCount, newRow, newCol);
        }

        // Wyjście i wejście

        int rowEntrance;
        do {
            rowEntrance = random.nextInt(size - 2) + 1;
        } while (grid[rowEntrance][1] != 1);
        grid[rowEntrance][0] = 1;
        int colEntrance = 0;
        int rowExit;
        do {
            rowExit = random.nextInt(size - 2) + 1;
        } while (grid[rowExit][size - 2] != 1);
        grid[rowExit][size - 1] = 1;
        int colExit = size - 1;
        grid[rowEntrance][colEntrance] = 1;
        grid[rowExit][colExit] = 1;
    }

    private int[] findNeighbor(int row, int col) {
        Random random = new Random();
        int[][] candidates = new int[4][2];
        int count = 0;

        if (row - 2 > 0 && grid[row - 2][col] == 1) {
            candidates[count++] = new int[]{row - 2, col};
        }
        if (row + 2 < size - 1 && grid[row + 2][col] == 1) {
            candidates[count++] = new int[]{row + 2, col};
        }
        if (col - 2 > 0 && grid[row][col - 2] == 1) {
            candidates[count++] = new int[]{row, col - 2};
        }
        if (col + 2 < size - 1 && grid[row][col + 2] == 1) {
            candidates[count++] = new int[]{row, col + 2};
        }
        return candidates[random.nextInt(count)];
    }
    private int addFrontiers(int[][] frontier, int frontierCount, int currentRow, int currentCol) {
        int northCellRow = currentRow - 2;
        int eastCellRow = currentRow;
        int southCellRow = currentRow + 2;
        int westCellRow = currentRow;
        int northCellCol = currentCol;
        int eastCellCol = currentCol + 2;
        int southCellCol = currentCol;
        int westCellCol = currentCol - 2;
        if (northCellRow > 0 && grid[northCellRow][northCellCol] == 0) {
            // Dodanie komórki frontowej
            grid[northCellRow][northCellCol] = 2;
            frontier[frontierCount][0] = northCellRow;
            frontier[frontierCount][1] = northCellCol;
            frontierCount++;
        }
        if (eastCellCol < size - 1 && grid[eastCellRow][eastCellCol] == 0) {
            grid[eastCellRow][eastCellCol] = 2;
            frontier[frontierCount][0] = eastCellRow;
            frontier[frontierCount][1] = eastCellCol;
            frontierCount++;
        }
        if (southCellRow < size - 1 && grid[southCellRow][southCellCol] == 0) {
            grid[southCellRow][southCellCol] = 2;
            frontier[frontierCount][0] = southCellRow;
            frontier[frontierCount][1] = southCellCol;
            frontierCount++;
        }
        if (westCellCol > 0 && grid[westCellRow][westCellCol] == 0) {
            grid[westCellRow][westCellCol] = 2;
            frontier[frontierCount][0] = westCellRow;
            frontier[frontierCount][1] = westCellCol;
            frontierCount++;
        }
        return frontierCount;
    }

    public int getSize(){
        return size;
    }

    public byte[][] getGrid(){
        return grid;
    }
    public void display(){
        for (byte[] row : grid) {
            for (byte cell : row) {
                System.out.print(cell == 0 ? "██" : "  ");
            }
            System.out.println();
        }
    }
}
