package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Game {
    private ArrayList<String> players = new ArrayList<>();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];

    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast(createPopQuestion(i));
			scienceQuestions.addLast((createScienceQuestion(i)));
			sportsQuestions.addLast((createSportsQuestion(i)));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	private String createSportsQuestion(int index){
		return "Sports Question " + index;
	}

	private String createScienceQuestion(int index){
		return "Science Question " + index;
	}

	private String createPopQuestion(int index){
		return "Pop Question " + index;
	}

	public boolean isPlayable() {
		return (numberOfPlayers() >= 2);
	}

	public boolean add(String playerName) {


	    players.add(playerName);
	    places[numberOfPlayers()] = 0;
	    purses[numberOfPlayers()] = 0;
	    inPenaltyBox[numberOfPlayers()] = false;

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	private int numberOfPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (rollIsOdd(roll)) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				moveCurrentPlayerForward(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {
			moveCurrentPlayerForward(roll);
		}

	}

    private boolean rollIsOdd(int roll) {
        return roll % 2 != 0;
    }

    private void moveCurrentPlayerForward(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) {
			places[currentPlayer] = places[currentPlayer] - 12;
		}

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			System.out.println(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		if (IntStream.of(0, 4, 8).anyMatch(x -> x == places[currentPlayer])) {
		    return "Pop";
        }

		if (IntStream.of(1, 5, 9).anyMatch(x -> x == places[currentPlayer])) {
		    return "Science";
        }

		if (IntStream.of(2, 6, 10).anyMatch(x -> x == places[currentPlayer])) {
		    return "Sports";
        }
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				return increasePointsAndGetNextPlayer();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
		} else {
			return increasePointsAndGetNextPlayer();
		}
	}

	private boolean increasePointsAndGetNextPlayer() {
		System.out.println("Answer was correct!!!!");
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;

		if (currentPlayer == players.size()) {
			currentPlayer = 0;
		}

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
