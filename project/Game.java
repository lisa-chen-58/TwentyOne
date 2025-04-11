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
        System.out.println("----------------------------");
        String input = "";

        while (true) {
            input = this.gameScanner.nextLine().trim();

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

    private void evaluateGameStatus(){
        if(this.numOfActivePlayers <= 1){
            this.isGameOver = true;
            System.out.println("Game is over");
        }
    }

    private Player determineStartingPlayer(){
        if (this.numOfActivePlayers <= 0) {
            throw new IllegalStateException("No active players left.");
        }
        Player player = getCurrentPlayer();
        int safetyCheck = 0;
        while(!player.isAbleToPlay()){
            cyclePlayerIndex();
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

    public void takeTurn(){

        // check for game over
        evaluateGameStatus();
        if(getCurrentPlayer().getStatus() == "win" || this.isGameOver){
            printResults();
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
    private void printResults(){
        List<Player> playersLost = new ArrayList<>();
        for(Player player : playersList){
            if(player.getStatus() == "win"){
                System.out.println("The following player is a winner!: " + player.getName());
            }
            if(player.getStatus() == "playing" && this.isGameOver){
                System.out.println("The following player wins by default!: " + player.getName());
            }
            else{
                playersLost.add(player);
            }
        }
        if(!playersLost.isEmpty()){
            System.out.println("The following players have lost: ");
            for(Player loser : playersLost){
                System.out.println(loser.getName());
            }
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
        if(names.length <= 1){
            throw new IllegalArgumentException("You need more than 1 name.");
        }
        Set<String> namesAlreadyAdded = new HashSet<>();
        for( String name : names){
            if(!namesAlreadyAdded.add(name)){
                throw new IllegalArgumentException("You have entered duplicate names.");
            }
        }
    }
}