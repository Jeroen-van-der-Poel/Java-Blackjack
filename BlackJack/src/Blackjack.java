import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Blackjack {

	// instance Variabelen
	private Player player;
	private Hand divisorHand;
	private Cards cards;

	private BufferedReader br;

	// constructor
	public Blackjack(Cards cards, Player player) {
		this.cards = cards;
		this.player = player;

		br = new BufferedReader(new InputStreamReader(System.in));

		// deler heeft 1 hand
		divisorHand = new Hand(cards.getCard(), cards.getCard(), 0);

	}

	// print een ronde uit
	public void printRound() {
		System.out.println("*******************************************************************************");
		System.out.print("Deler: ");
		divisorHand.printFirstCard();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------------");
		player.printAllHands();
		System.out.println("*******************************************************************************");
		System.out.println("");
	}

	// als de speler aan de beurt is
	public void playerTurn() {
		for (int i = 0; i < player.getAmountOfHands(); i++) {
			if (player.isHandPlayable(i)) {
				System.out.println(player.getName() + ", wat wil je doen met hand " + (i + 1) + "?");
				String command = askCommand();
				// pas
				if (command.equals("p")) {
					player.pas(i);
					// draaien
				} else if (command.equals("d")) {
					player.turn(i, cards.getCard());
					// verdubbelen
				} else {
					if (player.getFunds() >= player.getHandBet(i)) {
						player.removeFund(player.getHandBet(i));
						player.dubble(i, cards.getCard());
					} else {
						System.out.println(player.getName() + ", je hebt niet genoeg geld om te verdubbelen.");
						player.turn(i, cards.getCard());
					}
				}
			}
		}
	}

	// als de deler aan de beurt is
	public void divisorTurn() {
		// print eerste kaart
		System.out.print("Deler: ");
		divisorHand.printFirstCard();

		System.out.println("");
		System.out.println("Druk op return om de deler te laten spelen...");

		// kijken of die dood is of blackjack heeft
		divisorHand.isBlackjack();
		divisorHand.isDead();

		// print kaarten in de hand
		String st1 = ("Deler: " + divisorHand);
		// voeg de spaties toe tussen de hand en de status
		String str1 = String.format("%-30s", st1) + divisorHand.getStatus();
		System.out.println(str1);

		// zolang de deler hand minder als 17 is, pak een nieuwe kaart
		while (divisorHand.handValue() < 17) {
			System.out.println("Druk op return om de deler te laten spelen...");
			divisorHand.addCard(cards.getCard());

			divisorHand.isBlackjack();
			divisorHand.isDead();

			// print kaarten uit
			st1 = ("Deler: " + divisorHand);
			// voeg de spaties toe tussen de hand en de status
			str1 = String.format("%-30s", st1) + divisorHand.getStatus();
			System.out.println(str1);
		}
		// deler is dood
		if (divisorHand.handValue() > 21 ) {
			System.out.println("De deler is dood");
		}
		// deler heeft blackjack
		else if (divisorHand.handValue() == 21) {
			System.out.println("De deler heeft blackjack");
		}
		// deler past
		else {
			System.out.println("De deler heeft gepast en is geëindigd met " + divisorHand.handValue() + " punten");
		}
	}

	// commands opvragen
	private String askCommand() {
		String input = "";

		// kijken of de input gelijk is aan p of d of 2, zo niet:
		// loop totdat het gelijk is aan p, d, 2
		while (!input.equals("p") && !input.equals("d") && !input.equals("2")) {
			input = readLine();
			if (!input.equals("p") && !input.equals("d") && !input.equals("2")) {
				System.out.println("Probeer opnieuw!");
			}
		}
		return input;
	}

	// input lezen en returnen
	private String readLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// vergelijken van de score van de speler en deler
	public void comparePoints() {
		for (int i = 0; i < player.getAmountOfHands(); i++) {
			int playerscore = player.getScore(i);
			if (playerscore <= 21) {
				// deler is dood/speler gewonnen
				if (divisorHand.handValue() > 21 || playerscore > divisorHand.handValue()) {
					playerWins(i, playerscore);
				}

				// speler verliest/deler wint
				else if (playerscore < divisorHand.handValue()) {
					playerLoses(i);
					// deler en speler spelen gelijk
				} else {
					System.out.println(player.getName() + ", je hebt gelijk gespeelt met hand " + (i + 1) + ".");
					player.addFund(player.getHandBet(i));
				}
			}
			// speler heeft verloren
			else {
				playerLoses(i);
			}
		}
		// kapitaal uitprinten
		System.out.println("Je kapitaal is nu €" + player.getFunds() + ".");
	}

	// Als de speler verliest wordt deze method aangeroepen
	public void playerLoses(int index) {
		System.out.println(player.getName() + ", je verliest hand " + (index + 1) + " met een inzet van "
				+ player.getHandBet(index) + ".");
	}

	// als de speler wint wordt deze method aangeroepen
	public void playerWins(int index, int score) {
		System.out.print(player.getName() + ", je wint hand " + (index + 1) + " met een inzet van ");
		// als blackjack, inzet * 2.5 = uiteindelijke winst
		if (player.getHandStatus(index).equals("BLACKJACK!")) {
			int profit = (int) Math.round(player.getHandBet(index) * 2.5);
			System.out.println(profit + ".");
			// kapitaal verhogen met 2.5 keer de inzet
			player.addFund(profit);
		}
		// als de speler meer punten heeft dan de deler:
		else {
			int profit = (player.getHandBet(index) * 2);
			System.out.println(profit + ".");
			// kapitaal verhogen met de dubbelen inzet
			player.addFund(profit);
		}
	}
}
