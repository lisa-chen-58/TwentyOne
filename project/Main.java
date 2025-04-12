import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)) {
            Game game = new Game(scanner);
            game.startGame();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}