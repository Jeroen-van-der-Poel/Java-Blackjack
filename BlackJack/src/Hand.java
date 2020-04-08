import java.util.ArrayList;

public class Hand {

	// instance Variabelen
	private ArrayList<Card> cards;
	private int bet;
	private String status;

	// constructor
	public Hand(Card card1, Card card2, int bet) {
		cards = new ArrayList<Card>();
		this.bet = bet;
		status = "";

		// toevoegen 2 begin kaarten
		cards.add(card1);
		cards.add(card2);

		// geeft aan of de speler blackjack heeft of niet
		isBlackjack();
	}

	// kijken of de hand meer dan 22 is
	public void isDead() {
		if (handValue() > 21) {
			status = "Dood";
		}
	}

	// kijken of de hand 21 is
	public void isBlackjack() {
		// 21 punten met 2 kaarten = blackjack
		if (handValue() == 21 && cards.size() == 2) {
			status = "BLACKJACK!";
		}
		// 21 punten met meer dan 2 kaarten = gepast
		// stond niet in het assigment wat de status dan moet worden
		else if (handValue() == 21) {
			status = "Gepast";
		}
	}

	// kijken naar de waarde van de hand
	public int handValue() {
		int totalvalue = 0;

		// waarde ophogen met kaarten uit de arraylist
		for (Card c : cards) {
			totalvalue += c.StringToInt();
		}

		// kijken naar of de waarde boven de 21 is en of ik een aas heb
		if (totalvalue > 21 && checkA() >= 1) {
			// zo ja
			for (int i = 0; i < checkA(); i++) {
				// aas krijgt de waarde 1
				totalvalue -= 10;
				// zit ik onder de 21
				if (totalvalue <= 21) {
					// geef de waarde terug
					return totalvalue;
				}
			}
		}
		return totalvalue;
	}

	// kaart toevoegen aan de hand
	public void addCard(Card card) {
		cards.add(card);
	}

	// hand uitprinten
	public void printHand() {

		for (Card c : cards) {
			System.out.print(" ");
			c.printCard();
		}
	}

	// return de hand naar een String
	@Override
	public String toString() {
		String str = "";

		// loop door de array heen
		for (Card c : cards) {
			// voeg voor elke kaart het figuur en de waarde toe aan de String
			str += c.getFigure();
			str += c.getValue();
			str += " ";
		}
		// return de String met het figuur en de waarde
		return str;

	}

	// kijken of er een aas in je hand zit
	private int checkA() {
		int amountOfA = 0;

		for (Card c : cards) {
			// kijken of een kaart gelijk is aan A
			if (c.getValue().equals("A")) {
				amountOfA++;
			}
		}
		return amountOfA;
	}

	// verdubbellen van de inzet
	public void DubbleBet() {
		bet = bet * 2;
		status = "Dubbel";
	}

	// inzet opvragen
	public int getBet() {
		return bet;
	}

	// kijken of de hand nog actief is
	public boolean isActive() {
		if (status.equals("")) {
			return true;
		}
		return false;
	}

	// de hand op inactief zetten
	public void setStatusPas() {
		status = "Gepast";
	}

	// return de status
	public String getStatus() {
		return status;
	}

	// print de eerste kaart in een hand
	public void printFirstCard() {
		cards.get(0).printCard();
	}
}
