
public class Card {

	// instance Variabelen
	private String value;
	private String figure;

	// constructor
	public Card(String value, String figure) {
		this.value = value;
		this.figure = figure;
	}

	// vraag de waarde van de kaart op
	public String getValue() {
		return value;
	}

	// vraag het figuur van de kaart op
	public String getFigure() {
		return figure;
	}

	// veranderd de String naar een int
	public int StringToInt() {
		// kijken naar de koning, vrouw, boer
		if (value.equals("H") || value.equals("V") || value.equals("B")) {
			return 10;
		}
		// kijken naar de aas
		if (value.equals("A")) {
			// kan 1 0f 11 aan de hand van de totaal score, maar er niet overheen gaat.
			// op het begin instellen als 11
			return 11;
		}
		// maak van value (String) een int
		return Integer.parseInt(value);
	}

	// print de kaart uit
	public void printCard() {
		System.out.print(figure + value);
	}
}
