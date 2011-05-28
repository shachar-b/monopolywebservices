package cards;

import java.util.ArrayList;
import java.util.Random;

/**
 * class ShaffledDeck
 * public
 * @author Omer Shenhar & Shachar Butnaro
 * this is a shuffled Deck of action cards were each card appears once
 * each card has the same probability of being polled
 */
public class ShaffledDeck {

    private ArrayList<ActionCard> deck = new ArrayList<ActionCard>();
    private Random randomGen = new Random();

    /**
     * method boolean isEmpty()
     * public
     * @return true if the deck is empty. false otherwise
     */
    public boolean isEmpty() {
        return deck.isEmpty();
    }

    /**
     * method int size()
     * public
     * @return the number of cards currently in the Deck
     */
    public int size() {
        return deck.size();
    }

    /**
     * method void add(ActionCard card) 
     * public
     * @param card - a card which is not in the Deck
     */
    public void add(ActionCard card) {
        if (!deck.contains(card)) {
            deck.add(card);
        }
    }

    /**
     * method ActionCard takeCard()
     * public
     * this function returns a random card from the deck without removing it
     * @return a random card from the deck
     */
    public ActionCard takeCard() {
        ActionCard card = deck.get(randomGen.nextInt(deck.size()));
        if (card.isGetOutOfJailFreeCard()) {
            deck.remove(card);
        }
        return card;
    }
}
