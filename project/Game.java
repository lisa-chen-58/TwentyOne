import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Game{
    private final List<Player> playersList = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean isGameOver = false;
    private int numOfActivePlayers;
    private Scanner gameScanner;

    // Constructor
    public Game (Scanner scanner){
        this.gameScanner = scanner;
        addPlayersToGame();
        this.numOfActivePlayers = playersList.size();
    }

    public void addPlayersToGame(){
        List<String> playersToAdd = new ArrayList<>();

        System.out.println("Continue adding names until all have been added.");
        System.out.println("-- 'done' when all players have been entered.");
        System.out.println("-- 'exit' to leave game.");
        System.out.printf("---------------------------- %n");

        while (true) {
            String input = this.gameScanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("User exited the game.");
                return;
            }

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            if (input.isEmpty()){
                System.out.println("Player name cannot be empty. Try again!");
                continue;
            }

            playersToAdd.add(input);
            System.out.println("Added " + input + " to game");
            System.out.println("----------------------------");

        }

        String[] playerNames = playersToAdd.toArray(new String[0]);

        validateEntries(playerNames);
        for(String name : playerNames){
            try {
                Player player = new Player(name);
                playersList.add(player);
                System.out.println(player.getName() + " has joined the game!");
            } catch (IllegalArgumentException e){
                System.out.println("Invalid player name: " + name);
            }
        }
        System.out.println("----------------------------");
    }

    public void startGame(){
        System.out.println("Game has started! Hit enter to continue.");

        while (!getIsGameOver()) {
            System.out.println("Enter or type 'exit' to close");
            String input = this.gameScanner.nextLine();
            System.out.println("----------------------------");

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game is ending early!");
                System.out.println("----------------------------");
                break;
            }
            takeTurn();
        }
    }

    public void takeTurn(){

        // check for game over
        evaluateGameStatus();
        if(getCurrentPlayer().getStatus().equals("win") || this.isGameOver){
            printResults();
            endGame();
            return;
        } else {

            try {
                Player startingPlayer = determineStartingPlayer();
                startingPlayer.rollDice();
                if(!startingPlayer.isAbleToPlay()){
                    decreaseActivePlayersByOne();
                }
            } catch (IllegalStateException e){
                System.out.println("Player could not roll: " + e.getMessage());
            }
            cyclePlayerIndex();
        }
    }

    public void endGame() {
        this.isGameOver = true;
        System.out.println("Game over.");
        System.out.println("----------------------------");
    }

    private void evaluateGameStatus(){
        if(this.numOfActivePlayers == 0){
            this.isGameOver = true;
            System.out.println("GAME OVER");
            System.out.println("----------------------------");
        }
    }

    private void cyclePlayerIndex(){
        System.out.println("Cycling to next player...");
        if(this.currentPlayerIndex == this.playersList.size()-1){
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex += 1;
        }
    }

    private void decreaseActivePlayersByOne(){
        this.numOfActivePlayers -=1;
    }

    private Player determineStartingPlayer(){
        if (this.numOfActivePlayers <= 0) {
            throw new IllegalStateException("No active players left.");
        }
        Player player = getCurrentPlayer();
        int safetyCheck = 0;
        while(!player.isAbleToPlay()){
            cyclePlayerIndex();
            System.out.println("----------------------------");
            player = getCurrentPlayer();
            safetyCheck++;

            if (safetyCheck >= playersList.size()) {
                throw new IllegalStateException("No valid starting player found.");
            }
        }

        System.out.println("Starting round with player " + player.getName());
        System.out.println("----------------------------");

        return player;
    }

    private void printResults(){
        List<Player> losers = new ArrayList<>();
        List<Player> winners = new ArrayList<>();
        for(Player player : playersList){
            if(
                player.getStatus().equals("win")
            ){
                winners.add(player);
            } else {
                losers.add(player);
            }
        }
        if(winners.isEmpty()){
            System.out.println("There are no winners :(");
        } else {
            System.out.println("The following players have won: ");
            printList(winners);
        }
        if(losers.isEmpty()){
            System.out.println("There are no losers <3");
        } else {
            System.out.println("The following players have lost: ");
            printList(losers);
        }
    }

    private void printList(List<Player> players){
        for(Player player : players){
            System.out.printf("- %s (Score: %d)%n", player.getName(),player.getScore());
        }
    }

    public List<Player> getPlayersList(){
        return this.playersList;
    }

    public boolean getIsGameOver() { return this.isGameOver; }

    public int getNumOfActivePlayers(){
        return this.numOfActivePlayers;
    }

    public Player getCurrentPlayer(){
        return playersList.get(this.currentPlayerIndex);
    }

    // Static Methods
    public static void validateEntries (String[] names){
        if(names.length == 0){
            throw new IllegalArgumentException("No player names provided.");
        }
        Set<String> namesAlreadyAdded = new HashSet<>();
        for( String name : names){
            if(!namesAlreadyAdded.add(name)){
                throw new IllegalArgumentException("You have entered duplicate names.");
            }
        }
    }
}