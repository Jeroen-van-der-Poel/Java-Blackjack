import java.util.ArrayList;
import java.util.Random;

public class Cards {

	// instance Variabelen
	private ArrayList<Card> cards;

	// constructor
	public Cards() {
		addSixStocks();
	}

	// creeren van 1 stock
	private void addStock() {

		// voeg de tekens van de kaarten toe aan een array
		String[] kinds = { "\u2660", "\u2665", "\u2666", "\u2663" };
		// kind betekent soort
		for (String kind : kinds) {
			// aanmaken speciale kaarten
			cards.add(new Card("A", kind));
			cards.add(new Card("B", kind));
			cards.add(new Card("V", kind));
			cards.add(new Card("H", kind));

			// loop voor genummerde kaarten
			for (int j = 2; j <= 10; j++) {
				cards.add(new Card("" + j, kind));
			}
		}
	}

	// 6 stokken kaarten toevoegen
	public void addSixStocks() {
		// elke keer dat de method wordt aangeroepen, maakt die een nieuwe lijst aan
		// oude kaarten in de lijst zijn gewist
		cards = new ArrayList<Card>();

		// loopen 6 keer addstock aanroepen
		for (int i = 0; i < 6; i++) {
			addStock();
		}
	}

	// geeft een random kaart uit de lijst terug
	public Card getCard() {

		Random r = new Random();
		// krijg een nummer tussen 0 en card size
		int n = r.nextInt(cards.size());

		// haal een kaart uit de lijst met random nummer
		Card card = cards.get(n);

		return card;
	}

	// kijken of er meer dan de helft van de kaarten is gebruikt
	// dan moet je opnieuw 6 stokken kaarten ergens anders aanroepen
	public boolean halfEmpty() {
		if (cards.size() < 156) {
			return true;
		}
		return false;
	}
}
