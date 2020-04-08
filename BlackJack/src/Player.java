
public class Player {

	// instance Variabelen
	private int funds;
	private String name;

	private Hand[] hands;

	// constructor
	public Player(String name) {
		this.name = name;
		this.funds = 1000;
	}

	// opvragen van kapitaal
	public int getFunds() {
		return funds;
	}

	// toevoegen van kapitaal
	public void addFund(int fund) {
		funds += fund;
	}

	// verwijderen van kapitaal
	public void removeFund(int fund) {
		funds -= fund;
	}

	// opvragen van de naam van de speler
	public String getName() {
		return name;
	}

	// aantal handen opvragen
	public int getAmountOfHands() {
		return hands.length;
	}

	// command pas
	public void pas(int index) {
		hands[index].setStatusPas();
	}

	// command draai
	public void turn(int index, Card card) {
		hands[index].addCard(card);

		// kijkt of die dood is of blackjack heeft
		hands[index].isBlackjack();
		hands[index].isDead();

		// print de nieuwe situatie onder het ingevoerde command uit
		printNewSituation(index);
	}

	// command verdubbel
	public void dubble(int index, Card card) {
		hands[index].addCard(card);
		hands[index].DubbleBet();

		// kijkt of die dood is of blackjack heeft
		hands[index].isBlackjack();
		hands[index].isDead();

		// print de nieuwe situatie onder het ingevoerde command uit
		printNewSituation(index);
	}

	// de hand uitprinten met de speler
	public void printHand(int index) {
		String st1 = (name + ", hand " + (index + 1) + ": ") + hands[index].toString();
		// voeg de spaties toe tussen de hand en inzet
		String str1 = String.format("%-50s", st1) + "Inzet = " + hands[index].getBet() + "  "
				+ hands[index].getStatus();
		System.out.println(str1);
	}

	// print de nieuwe situatie van de speler, na het toevoegen van een kaart
	public void printNewSituation(int index) {
		String st1 = ("Nieuwe situatie: ") + hands[index].toString();
		// voeg de spaties toe tussen de hand en inzet
		String str1 = String.format("%-50s", st1) + "Inzet = " + hands[index].getBet() + "  "
				+ hands[index].getStatus();
		System.out.println(str1);
	}

	// print alle handen uit
	public void printAllHands() {
		for (int i = 0; i < hands.length; i++) {
			printHand(i);
		}
	}

	// kijkt of er nog handen zijn, waar nog mee gespeeld kan worden
	public boolean playable() {
		for (Hand h : hands) {
			if (h.isActive()) {
				return true;
			}
		}
		return false;
	}

	// kijken naar een specifieke hand of die playable is
	public boolean isHandPlayable(int index) {
		return hands[index].isActive();
	}

	// opvragen van een score van een specieke hand
	public int getScore(int index) {
		return hands[index].handValue();
	}

	// opvragen van de inzet van een hand
	public int getHandBet(int index) {
		return hands[index].getBet();
	}

	// toevoegen handen
	public void addHands(Hand[] hands) {
		this.hands = hands;
	}

	// opvragen van de status van de hand
	public String getHandStatus(int index) {
		return hands[index].getStatus();
	}
}
