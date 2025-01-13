import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Deck {
    public ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        String[] suits = {"Hearts","Diamonds","Clubs","Spades"};
        String[] ranks = {"1","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
        //Create the deck
        for(String suit: suits){
            for(String rank : ranks){
                cards.add(new Card(rank,suit));
            }
        }
        cards.add(new Card("None","Joker"));
        cards.add(new Card("None","Joker"));
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card deal_card(){
        if(isEmpty()){
            throw new IllegalStateException("Empty no card left: ");
        }
        return cards.remove(0);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int cards_left(){
        return cards.size();
    }
    @Override
    public String toString(){
        return cards.toString();
    }
}
