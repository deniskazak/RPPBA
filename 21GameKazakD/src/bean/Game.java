
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * Game between user and computer or between two users and computer.
 */
public class Game implements Serializable {

    private LinkedList<Card> myCards;
    private LinkedList<Card> enemyCards;
    private int id;
    private int userId;
    private Date date;
    private int bet;
    private int win;
    private int rate;
    private boolean isReady;

    public Game() {
        myCards = new LinkedList<>();
        enemyCards = new LinkedList<>();
    }
    /**
     * @return List of user's cards.
     * @see Card
     */
    public LinkedList<Card> getMyCards() {
        return myCards;
    }
    /**
     * @return List of computer's cards.
     * @see Card
     */
    public LinkedList<Card> getEnemyCards() {
        return enemyCards;
    }

    public void setEnemyCards(LinkedList<Card> enemyCards) {
        this.enemyCards = enemyCards;
    }
    /**
     * Add new card in the list of the user's cards.
     * @param card New card
     * @see Card
     */
    public void addMyCard(Card card) {
        myCards.add(card);
    }

    /**
     * Add new card in the list of the computer's cards.
     * @param card New card
     * @see Card
     */
    public void addEnemyCard(Card card){
        enemyCards.add(card);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * @return Date of the finished game.
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date Date of the finished game.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return Won cash.
     */
    public int getWin() {
        return win;
    }
    /**
     * @param win Won cash.
     */
    public void setWin(int win) {
        this.win = win;
    }
    /**
     * @return User's rating of finished game (-3 if lost, 0 if draw, 3 if won).
     */
    public int getRate() {
        return rate;
    }
    /**
     * @param rate  User's rating of finished game (-3 if lost, 0 if draw, 3 if won).
     */
    public void setRate(int rate) {
        this.rate = rate;
    }
    /**
     * @return User's bet of current game.
     */
    public int getBet() {
        return bet;
    }
    /**
     * @param bet User's bet of current game.
     */
    public void setBet(int bet) {
        this.bet = bet;
    }
    /**
     * @return  True if user ready to play (Multiplayer).
     */
    public boolean isReady() {
        return isReady;
    }
    /**
     * @param ready   True if user ready to play (Multiplayer).
     */
    public void setReady(boolean ready) {
        isReady = ready;
    }
    /**
     * @param myCards List of user's cards.
     */
    public void setMyCards(LinkedList<Card> myCards) {
        this.myCards = myCards;
    }
    /**
     * @param cards List of cards.
     * @return  Points of cards.
     */
    public int getPoints(LinkedList<Card> cards) {
        int points = 0;
        for (Card card : cards)
        {
            switch (card.getType()) {
                case TWO:
                    points += 2;
                    break;
                case THREE:
                    points += 3;
                    break;
                case FOUR:
                    points += 4;
                    break;
                case FIVE:
                    points += 5;
                    break;
                case SIX:
                    points += 6;
                    break;
                case SEVEN:
                    points += 7;
                    break;
                case EIGHT:
                    points += 8;
                    break;
                case NINE:
                    points += 9;
                    break;
                case TEN:
                    points += 10;
                    break;
                case JACK:
                    points += 10;
                    break;
                case QUEEN:
                    points += 10;
                    break;
                case KING:
                    points += 10;
                    break;
                case ACE: {
                    if ((points + 11) > 21){
                        points += 1;
                        break;
                    }
                    else{
                        points += 11;
                        break;
                    }
                }
            }
        }

        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != game.id) return false;
        if (userId != game.userId) return false;
        if (bet != game.bet) return false;
        if (win != game.win) return false;
        if (rate != game.rate) return false;
        if (isReady != game.isReady) return false;
        if (myCards != null ? !myCards.equals(game.myCards) : game.myCards != null) return false;
        if (enemyCards != null ? !enemyCards.equals(game.enemyCards) : game.enemyCards != null) return false;
        return date != null ? date.equals(game.date) : game.date == null;

    }

    @Override
    public int hashCode() {
        int result = myCards != null ? myCards.hashCode() : 0;
        result = 31 * result + (enemyCards != null ? enemyCards.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + userId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + bet;
        result = 31 * result + win;
        result = 31 * result + rate;
        result = 31 * result + (isReady ? 1 : 0);
        return result;
    }
}
