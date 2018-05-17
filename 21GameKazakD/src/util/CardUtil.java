package util;

import bean.Card;
import bean.CardType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Util class for work with Cards.
 */
public class CardUtil {

    private static Random randomGenerator = new Random();
    private static ArrayList<Card> cards;

    static {
        getCardList();
    }

    /**
     * @return Random card.
     * @see Card
     */
    public static Card getRandomCard() {
        int index = randomGenerator.nextInt(cards.size());
        Card card = cards.get(index);
        return card;
    }


    private static ArrayList<Card> getCardList() {
        cards = new ArrayList<Card>();
        cards.add(new Card(CardType.TWO, "/images/cards/2b(2).jpg"));
        cards.add(new Card(CardType.TWO, "/images/cards/2c(2).jpg"));
        cards.add(new Card(CardType.TWO, "/images/cards/2k(2).jpg"));
        cards.add(new Card(CardType.TWO, "/images/cards/2p(2).jpg"));
        cards.add(new Card(CardType.THREE, "/images/cards/3b(3).jpg"));
        cards.add(new Card(CardType.THREE, "/images/cards/3c(3).jpg"));
        cards.add(new Card(CardType.THREE, "/images/cards/3k(3).jpg"));
        cards.add(new Card(CardType.THREE, "/images/cards/3p(3).jpg"));
        cards.add(new Card(CardType.FOUR, "/images/cards/4b(4).jpg"));
        cards.add(new Card(CardType.FOUR, "/images/cards/4c(4).jpg"));
        cards.add(new Card(CardType.FOUR, "/images/cards/4k(4).jpg"));
        cards.add(new Card(CardType.FOUR, "/images/cards/4p(4).jpg"));
        cards.add(new Card(CardType.FIVE, "/images/cards/5b(5).jpg"));
        cards.add(new Card(CardType.FIVE, "/images/cards/5c(5).jpg"));
        cards.add(new Card(CardType.FIVE, "/images/cards/5k(5).jpg"));
        cards.add(new Card(CardType.FIVE, "/images/cards/5p(5).jpg"));
        cards.add(new Card(CardType.SIX, "/images/cards/6b(6).jpg"));
        cards.add(new Card(CardType.SIX, "/images/cards/6c(6).jpg"));
        cards.add(new Card(CardType.SIX, "/images/cards/6k(6).jpg"));
        cards.add(new Card(CardType.SIX, "/images/cards/6p(6).jpg"));
        cards.add(new Card(CardType.SEVEN, "/images/cards/7b(7).jpg"));
        cards.add(new Card(CardType.SEVEN, "/images/cards/7c(7).jpg"));
        cards.add(new Card(CardType.SEVEN, "/images/cards/7k(7).jpg"));
        cards.add(new Card(CardType.SEVEN, "/images/cards/7p(7).jpg"));
        cards.add(new Card(CardType.EIGHT, "/images/cards/8b(8).jpg"));
        cards.add(new Card(CardType.EIGHT, "/images/cards/8c(8).jpg"));
        cards.add(new Card(CardType.EIGHT, "/images/cards/8k(8).jpg"));
        cards.add(new Card(CardType.EIGHT, "/images/cards/8p(8).jpg"));
        cards.add(new Card(CardType.NINE, "/images/cards/9b(9).jpg"));
        cards.add(new Card(CardType.NINE, "/images/cards/9c(9).jpg"));
        cards.add(new Card(CardType.NINE, "/images/cards/9k(9).jpg"));
        cards.add(new Card(CardType.NINE, "/images/cards/9p(9).jpg"));
        cards.add(new Card(CardType.TEN, "/images/cards/10b(10).jpg"));
        cards.add(new Card(CardType.TEN, "/images/cards/10c(10).jpg"));
        cards.add(new Card(CardType.TEN, "/images/cards/10k(10).jpg"));
        cards.add(new Card(CardType.TEN, "/images/cards/10p(10).jpg"));
        cards.add(new Card(CardType.JACK, "/images/cards/valetb(10).jpg"));
        cards.add(new Card(CardType.JACK, "/images/cards/valetc(10).jpg"));
        cards.add(new Card(CardType.JACK, "/images/cards/valetk(10).jpg"));
        cards.add(new Card(CardType.JACK, "/images/cards/valetp(10).jpg"));
        cards.add(new Card(CardType.QUEEN, "/images/cards/damab(10).jpg"));
        cards.add(new Card(CardType.QUEEN, "/images/cards/damac(10).jpg"));
        cards.add(new Card(CardType.QUEEN, "/images/cards/damak(10).jpg"));
        cards.add(new Card(CardType.QUEEN, "/images/cards/damap(10).jpg"));
        cards.add(new Card(CardType.KING, "/images/cards/korolb(10).jpg"));
        cards.add(new Card(CardType.KING, "/images/cards/korolc(10).jpg"));
        cards.add(new Card(CardType.KING, "/images/cards/korolk(10).jpg"));
        cards.add(new Card(CardType.KING, "/images/cards/korolp(10).jpg"));
        cards.add(new Card(CardType.ACE, "/images/cards/tuzb(11).jpg"));
        cards.add(new Card(CardType.ACE, "/images/cards/tuzc(11).jpg"));
        cards.add(new Card(CardType.ACE, "/images/cards/tuzk(11).jpg"));
        cards.add(new Card(CardType.ACE, "/images/cards/tuzp(11).jpg"));
        return cards;
    }
}
