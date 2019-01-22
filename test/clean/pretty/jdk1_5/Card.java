package jdk1_5;

import java.util.*;

public class Card implements Comparable, java.io.Serializable {
    public enum Rank { deuce, three, four, five, six, seven, eight, nine, ten,
                       jack, queen, king, ace }
    public enum Suit { clubs, diamonds, hearts, spades }

    private final Rank rank;
    private final Suit suit;

    private Card(Rank rank, Suit suit) {
        if (rank == null || suit == null)
            throw new NullPointerException(rank + ", " + suit);
        this.rank = rank;
        this.suit = suit;
    }

    public Rank rank() { return rank; }
    public Suit suit() { return suit; }

    public String toString() { return rank + " of " + suit; }

    public int compareTo(Object o) {
        Card c = (Card)o;
        int rankCompare = rank.compareTo(c.rank);
        return rankCompare != 0 ? rankCompare : suit.compareTo(c.suit);
    }

    private static List<Card> sortedDeck = new ArrayList<Card>(52);
    static {
        for (Rank rank : Rank.VALUES)
             for (Suit suit : Suit.VALUES)
                sortedDeck.add(new Card(rank, suit));
    }

    // Returns a shuffled deck
    public static List<Card> newDeck() {
        List<Card> result = new ArrayList<Card>(sortedDeck);
        Collections.shuffle(result);
        return result;
    }

    public static void main(String args[]) {
        int numHands     = Integer.parseInt(args[0]);
        int cardsPerHand = Integer.parseInt(args[1]);
        List<Card> deck  = Card.newDeck();

        for (int i=0; i < numHands; i++)
            System.out.println(dealHand(deck, cardsPerHand));
    }

    /**
     * Returns a new general-purpose list consisting of the last n
     * elements of deck.  The returned list is sorted using the
     * elements natural ordering.
     */
    public static <E> List<E> dealHand(List<E> deck, int n) {
        int deckSize = deck.size();
        List<E> handView = deck.subList(deckSize-n, deckSize);
        List<E> hand = new ArrayList<E>(handView);
        handView.clear();
        Collections.sort(hand);
        return hand;
    }

}

