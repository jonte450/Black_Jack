import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Game {
    public Deck deck;
    public Player player;
    public Dealer dealer;
    public JFrame frame;
    public JPanel panel;
    public JLabel playerHandLabel1;
    public JLabel dealerHandLabel;
    public JLabel playersScorelabel;
    public JLabel gameStatusLabel;
    public JButton hitButton;
    public JButton staybutton;


    public Game(String player_name){
        deck = new Deck();
        player = new Player(player_name,false);
        dealer = new Dealer("Dealer");

        //Set up the GUI
        frame = new JFrame("Blackjack Game");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        playerHandLabel1 = new JLabel("Player's Hand: ");
        dealerHandLabel = new JLabel("Dealer's Hand: ");
        playersScorelabel = new JLabel("Your Score: 0");
        gameStatusLabel = new JLabel("Game Status: Ready");
        hitButton = new JButton("Hit");
        staybutton = new JButton("Stay");

        panel.add(playerHandLabel1);
        panel.add(dealerHandLabel);
        panel.add(playersScorelabel);
        panel.add(gameStatusLabel);
        panel.add(hitButton);
        panel.add(staybutton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setContentPane(panel);
        frame.setVisible(true);
        hitButton.addActionListener(new HitActionListener());
        staybutton.addActionListener(new StayActionListener());
    }
    public  void startGame(){
        deck.shuffle();
        player.clear_hand();
        dealer.clear_hand();

        //Deal the cards
        player.add_card(deck.deal_card());
        player.add_card(deck.deal_card());

        dealer.add_card(deck.deal_card());
        dealer.add_card(deck.deal_card());
        updateGUI();
        playerTurn();
    }

    private void updateGUI(){
        playerHandLabel1.setText("Player's Hand: "+ player.show_hand(false));
        dealerHandLabel.setText("Dealer's Hand: " + dealer.show_hand(true));
        playersScorelabel.setText("Your Score: " + player.hand_score());
    }

    private void playerTurn(){
        gameStatusLabel.setText("It is your turn: (Hit or Stay)");
        hitButton.setEnabled(true);
        staybutton.setEnabled(true);

    }

    private class HitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            player.add_card(deck.deal_card());
            updateGUI();

            if (player.hand_score() > 21) {
                gameStatusLabel.setText("You bust with a score of "+player.hand_score()+ "!");
                hitButton.setEnabled(false);
                staybutton.setEnabled(false);
                dealerTurn();
            }
        }

    }
    private class StayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            gameStatusLabel.setText("You stay with a score of " + player.hand_score());
            hitButton.setEnabled(false);
            staybutton.setEnabled(false);
            dealerTurn();
        }
    }

    private void dealerTurn(){
        while (dealer.should_hit()) {
        dealer.add_card(deck.deal_card());
        updateGUI();
        if(dealer.hand_score() > 21) {
        gameStatusLabel.setText("Dealer got a bust score of " + dealer.hand_score()+ " !");
        determineWinner();
        return;
        }
        }
    }

    public void determineWinner(){
        int playerScore = player.hand_score();
        int dealerScore = dealer.hand_score();
        if (playerScore > 21){
            gameStatusLabel.setText("You lose! Your score: "+playerScore + ", Dealer's score :"+ dealerScore);
        }else if(dealerScore > 21) {
            gameStatusLabel.setText("You Win Dealer got a bust. Your score: "+ playerScore + ", Dealer's score: "+ dealerScore);
        }else if (dealerScore > 21) {
            gameStatusLabel.setText("You win! Dealer got a bust. Your score: "+playerScore+ ". Dealer's score "+ dealerScore);
        }else if (dealerScore > playerScore){
            gameStatusLabel.setText("You lose! Your score: "+ playerScore+ ", Dealers score is " +dealerScore);
        }else if (playerScore> dealerScore){
            gameStatusLabel.setText("You win! Your score: "+ playerScore+ ", Dealers score is " +dealerScore);
        }else {
            gameStatusLabel.setText("It is a tie! Your score: " + playerScore +" and dealers score: "+dealerScore);
        }
        int response = JOptionPane.showConfirmDialog(frame,"Do you want to play again: !");
        if(response == JOptionPane.YES_OPTION) {
            startGame();
        }else {
            System.exit(0);
        }
    }
}
