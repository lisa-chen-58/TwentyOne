import java.util.Random;

public class Player {
    private String name;
    private int score = 0;
    private boolean isAbleToPlay = true;
    private String status = "playing";

    public Player(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        this.name = name;
    }

    private void addToScore(int scoredValue){
        this.score += scoredValue;
    }

    private void invalidatePlayCondition(){
        this.isAbleToPlay = false;
        System.out.println(this.name + " is done with the game!");
    }

    private void playerIsDone(){
        System.out.println("Status has changed to " + this.status);
        invalidatePlayCondition();
    }

    private String evaluateStatus(){
        if(this.score == 21){
            this.status = "win";
            playerIsDone();
        }
        if(this.score > 21){
            this.status = "bust";
            playerIsDone();
        }
        return "playing";
    }

    public void rollDice(){
        System.out.println("Rolling dice... ");
        if(!isAbleToPlay){
            throw new IllegalStateException("Player is unable to resume play");
        }
        Random random = new Random();
        int rollResult = random.nextInt(6) + 1;

        addToScore(rollResult);
        printCurrentScore();
        evaluateStatus();
    }

    public void printCurrentScore(){
        System.out.printf("%s's score is now %d.", this.name, this.score);
        System.out.println("----------------------------");
    }

    public void printResult() {

        System.out.printf("%s: %d (%s) \n", this.name, this.score, this.status);
        System.out.println("----------------------------");
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public boolean isAbleToPlay(){
        return this.isAbleToPlay;
    }

    public String getStatus(){
        return this.status;
    }
}