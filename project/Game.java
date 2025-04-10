import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class Game{
    private final List<Player> playersList = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean isGameOver = false;
    private int numOfActivePlayers;

    // Constructor
    public Game (String[] playerNames){
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
        this.numOfActivePlayers = playersList.size();
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
        for(Player player : playersList){
            if(player.getStatus() == "win"){
                System.out.println("The following player is a winner!:");
            }
            player.printResult();
        }
    }

    // Static Methods
    public static void validateEntries (String[] names){
        Set<String> namesAlreadyAdded = new HashSet<>();
        for( String name : names){
            if(!namesAlreadyAdded.add(name)){
                throw new IllegalArgumentException("You have entered duplicate names.");
            }
        }
    }
}