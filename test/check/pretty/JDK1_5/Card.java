package jdk1_5;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Card implements Comparable, java.io.Serializable {

	private final Rank rank;
	private final Suit suit;

	private static List<Card> sortedDeck = new ArrayList<Card>(52);


	/**
	 *  Constructor for the Card object
	 *
	 *@param  rank  Description of Parameter
	 *@param  suit  Description of Parameter
	 */
	private Card(Rank rank, Suit suit) {
		if (rank == null || suit == null) {
			throw new NullPointerException(rank + ", " + suit);
		}
		this.rank = rank;
		this.suit = suit;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Rank rank() {
		return rank;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Suit suit() {
		return suit;
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return rank + " of " + suit;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o  Description of Parameter
	 *@return    Description of the Returned Value
	 */
	public int compareTo(Object o) {
		Card c = (Card) o;
		int rankCompare = rank.compareTo(c.rank);
		return rankCompare != 0 ? rankCompare : suit.compareTo(c.suit);
	}


	// Returns a shuffled deck
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public static List<Card> newDeck() {
		List<Card> result = new ArrayList<Card>(sortedDeck);
		Collections.shuffle(result);
		return result;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  args  Description of Parameter
	 */
	public static void main(String args[]) {
		int numHands = Integer.parseInt(args[0]);
		int cardsPerHand = Integer.parseInt(args[1]);
		List<Card> deck = Card.newDeck();

		for (int i = 0; i < numHands; i++) {
			System.out.println(dealHand(deck, cardsPerHand));
		}
	}


	/**
	 *  Returns a new general-purpose list consisting of the last n elements of
	 *  deck. The returned list is sorted using the elements natural ordering.
	 *
	 *@param  deck  Description of Parameter
	 *@param  n     Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public static <E> List<E> dealHand(List<E> deck, int n) {
		int deckSize = deck.size();
		List<E> handView = deck.subList(deckSize - n, deckSize);
		List<E> hand = new ArrayList<E>(handView);
		handView.clear();
		Collections.sort(hand);
		return hand;
	}
	static {
		for (Rank rank : Rank.VALUES) {
			for (Suit suit : Suit.VALUES) {
				sortedDeck.add(new Card(rank, suit));
			}
		}
	}


	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Rank { deuce, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace }
	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Suit { clubs, diamonds, hearts, spades }

}

