package bean;

import java.io.Serializable;

/**
 * Game card.
 */
public class Card implements Serializable {

    private CardType type;
    private String src;

    public Card(CardType type, String src) {
        this.type = type;
        this.src = src;
    }
    /**
     * @return Type of the card
     * @see CardType
     */
    public CardType getType() {
        return type;
    }
    /**
     * @return Path to image of the card.
     */
    public String getSrc() {
        return src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (type != card.type) return false;
        return src != null ? src.equals(card.src) : card.src == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (src != null ? src.hashCode() : 0);
        return result;
    }
}
