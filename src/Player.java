import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private boolean isDealer;
    private int score;

    public Player(String name, boolean isDealer) {
        this.name = name;
        this.isDealer = isDealer;
        this.cards = new ArrayList<>();
        this.score = 0;
    }

    public void add_card(Card card) {
        cards.add(card);
    }

    public int hand_score() {


        int total_aces = 0;
        int total_val = 0;

        for (
                Card card : cards) {
            String rank = card.getRank();
            if (rank.equals("Ace")) {
                total_val = total_val + 11;
                total_aces = total_aces + 1;
            } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
                total_val = total_val + 10;
            } else {
                try {
                    total_val = total_val + Integer.parseInt(card.getRank());
                }catch (NumberFormatException e){
                    throw new IllegalStateException("Invalid card rank: " +rank);
                }
            }
        }
        while(total_val > 21 && total_aces >0){
            total_val = total_val - 10;
            total_aces = total_aces -1;
        }
        return total_val;
    }

    public String show_hand(boolean hidden){
        if (isDealer && hidden && cards.size() > 0) {
            return "[Hidden], " +cards.subList(1,cards.size());
        }
        return cards.toString();
    }

    public void clear_hand(){
        cards.clear();
    }

    public void incremeant_score(){
        score++;
    }

    public int getScore() {
        return score;
    }
    public boolean isDealer(){
        return isDealer;
    }

    public String getName(){
        return name;
    }
}

