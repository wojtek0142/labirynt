package maze;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean running = true;
        Maze maze = null;
        Scanner scan = new Scanner(System.in);

        while(running){
            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze.");
            if (maze != null){
                System.out.println("3. Save the maze");
                System.out.println("4. Display the maze");
            }
            System.out.println("0. Exit");

            String choice = scan.nextLine();
            switch(choice){
                case "1":
                    System.out.println("Enter the size of a new maze");
                    try {
                        int size = Integer.parseInt(scan.nextLine());
                        maze = new Maze(size);
                        maze.display();
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;
                case "2":
                    System.out.println("Enter filename of a maze to load");
                    String pathToFile = scan.nextLine();
                    File file = new File(pathToFile);
                    if (!file.exists()) {
                        System.out.println("The file " + pathToFile + " does not exist");
                        break;
                    }
                    try (Scanner scanner = new Scanner(file)){
                        if (!scanner.hasNextInt()) throw new Exception();
                        int size = scanner.nextInt();
                        byte[][] newGrid = new byte[size][size];
                        for (int i = 0; i < size; i++){
                            for (int j = 0; j < size; j++){
                                byte cell = scanner.nextByte();
                                newGrid[i][j] = cell;
                            }
                        }
                        maze = new Maze(newGrid);
                    } catch (Exception e){
                        System.out.println("Cannot load the maze. It has an invalid format.");
                    }
                    break;
                case "3":
                    if (maze == null){
                        System.out.println("Incorrect option. Please try again");
                        break;
                    }
                    System.out.println("Enter filename to save the maze:");
                    String savePath = scan.nextLine();
                    try (PrintWriter writer = new PrintWriter(savePath)) {
                        writer.println(maze.getSize());
                        for (byte[] row : maze.getGrid()){
                            for (byte cell : row){
                                writer.print(cell + " ");
                            }
                            writer.println();
                        }
                    } catch (IOException e) {
                        System.out.printf("An exception occurred %s", e.getMessage());
                    }
                    break;
                case "4":
                    if (maze != null) {
                        maze.display();
                    } else {
                        System.out.println("Incorrect option. Please try again");
                    }
                    break;
                case "0":
                    running = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;

            }

        }

    }
}