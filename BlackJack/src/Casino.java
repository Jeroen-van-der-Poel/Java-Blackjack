import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Casino {

	// instance Variabelen
	private Player player;
	private Cards cards;
	private Blackjack blackjack;

	private BufferedReader br;

	// constructor
	public Casino() {
		cards = new Cards();
		br = new BufferedReader(new InputStreamReader(System.in));

		// potje blackjack beginnen
		intro();
		blackjack = new Blackjack(cards, player);
		playGame();

		// loop om nog een keer te spelen
		while (playAgain()) {
			// kijken of de stapel half vol is
			// zo nee:
			if (cards.halfEmpty()) {
				// voeg 6 nieuwe stokken toe
				// schrijft deze over de oude stokken heen
				cards.addSixStocks();
			}
			askPlayer();
			blackjack = new Blackjack(cards, player);
			playGame();
		}
		// als er n wordt ingevuld, voer dit uit
		System.out.println("Tot de volgende keer!");
	}

	// methode om een spel te beginnen
	public void playGame() {
		// loop die de game uitvoert
		while (player.playable()) {
			blackjack.printRound();
			blackjack.playerTurn();
		}
		blackjack.printRound();
		blackjack.divisorTurn();
		blackjack.comparePoints();
	}

	// introductie tekst
	private void intro() {
		// intro bericht met informatie
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("Welkom in het ICasino! We gaan fijn Blackjack spelen!!");
		System.out.println("-------------------------------------------------------------------------------");

		// commando's
		System.out.println("");
		System.out.println("Commando's");
		System.out.println("----------");
		System.out.println("p = passen");
		System.out.println("d = draaien");
		System.out.println("2 = inzet verdubbelen");
		System.out.println("");

		// speler verwelkomen
		String playerName = askForName();
		System.out.println("Welkom " + playerName + ", Je start kapitaal is €1000,-!");

		// speler aanmaken
		player = new Player(playerName);

		askPlayer();
	}

	// informatie vragen aan de speler
	public void askPlayer() {
		// vraag aantal handen
		int hands = askForHands();
		System.out.println("Je hebt gekozen voor " + hands + " handen.");

		// vraag voor de inzet
		int bet = askForBet(player.getFunds() / hands);
		System.out.println("Je hebt gekozen voor een inzet van " + bet + ".");

		// witregel toevoegen
		System.out.println("");

		// array van handen aanmaken
		Hand[] playerHands = new Hand[hands];

		// loop waarin een hand wodt aangemaakt
		for (int i = 0; i < hands; i++) {
			playerHands[i] = new Hand(cards.getCard(), cards.getCard(), bet);
			player.removeFund(bet);
		}

		// handen toevoegen aan speler
		player.addHands(playerHands);
	}

	// naam opvragen
	private String askForName() {
		System.out.println("Wat is je naam?");
		String playername = "";
		while (playername.equals("")) {
			playername = readLine();
		}
		return playername;
	}

	// handen opvragen
	private int askForHands() {
		System.out.println("Met hoeveel handen wil je spelen (1-5)?");
		String amountOfHands = "";
		int hands = 0;

		// loop totdat het antwoord een getal is
		while (hands < 1 || hands > 5) {
			amountOfHands = readLine();
			try {
				// string omzetten in een int
				hands = Integer.parseInt(amountOfHands);
			} catch (Exception e) {
			}
			// als het niet tussen 1 en 5 zitten, print probeer opnieuw
			if (hands < 1 || hands > 5) {
				System.out.println("Probeer opnieuw!");
			}
		}
		return hands;
	}

	// inzet opvragen
	private int askForBet(int maxBet) {
		System.out.println("Met welke inzet wil je spelen (1-" + maxBet + ")?");
		String betValue = "";
		int bets = 0;

		// loop totdat de inzet kleiner is dan max inzet en groter dan 0
		while (bets > maxBet || bets < 1) {
			betValue = readLine();
			try {
				// String omzetten in een int
				bets = Integer.parseInt(betValue);
			} catch (Exception e) {
			}
			// als de inzet groter is dan max inzet en kleiner dan 1, print probeer opnieuw
			if (bets > maxBet || bets < 1) {
				System.out.println("Pobeer opnieuw!");
			}
		}
		return bets;
	}

	// input lezen en returnen
	private String readLine() {
		try {
			// geef de input van de gebruiker terug
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// vragen of speler nog een keer wil spelen
	private boolean playAgain() {
		System.out.println("");

		// kijken of de speler nog genoeg kapitaal heeft
		// de speler heeft genoeg kapitaal:
		if (player.getFunds() >= 5) {
			System.out.println("Wil je nog een keer spelen (j/n)?");
			String input = "";

			// blijf herhalen totdat de input gelijk is aan ja of nee
			while (!input.equals("j") && !input.equals("n")) {
				input = readLine().toLowerCase();
				// bij een andere invoer:
				if (!input.equals("j") && !input.equals("n")) {
					System.out.println("probeer opnieuw!");
				}
			}
			// als het j is, speel nog een spel
			if (input.equals("j")) {
				return true;
			}
			return false;
		}
		// als de speler te weinig kaptiaal heeft:
		System.out.println(player.getName() + ", je hebt te weinig kaptiaal en kan niet nog een keer spelen!");
		return false;
	}
}
