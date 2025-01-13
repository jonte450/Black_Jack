public class Dealer extends Player{

    public Dealer(String name){
        super(name,true);
    }

    public boolean should_hit(){
        return hand_score() < 17;
    }
}
