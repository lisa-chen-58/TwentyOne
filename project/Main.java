import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args) {

        try {
            // Create scanner object
            Scanner scanner = new Scanner(System.in);
            List<String> playersToAdd = new ArrayList<>();

            System.out.println("Continue adding names until all have been added.");
            System.out.println("-- 'done' when all players have been entered.");
            System.out.println("-- 'exit' to leave game.");
            System.out.println("----------------------------");
            String input = "";

            while (true) {
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("User exited the game.");
                    return;
                }

                if (input.equalsIgnoreCase("done")) {
                    break;
                }

                playersToAdd.add(input);
                System.out.println("Added " + input + " to game");
                System.out.println("----------------------------");

            }

            // Game creation
            String[] finalList = playersToAdd.toArray(new String[0]);
            Game game = new Game(finalList);
            System.out.println("Game has started! Hit enter to continue.");

            while (!game.getIsGameOver()) {
                System.out.println("Enter or type 'exit' to close");
                input = scanner.nextLine();
                System.out.println("----------------------------");

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Game is ending early!");
                    System.out.println("----------------------------");
                    break;
                }
                game.takeTurn();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}