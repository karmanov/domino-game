package io.karmanov.domino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {

    public static void main(String[] args) {
        System.out.println("Welcome to the Domino game!");
        System.out.println("Step 1. Populate and shuffle the stock");
        List<Domino> allDominoes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Domino domino = new Domino(i, j);
                if (!allDominoes.contains(domino))
                    allDominoes.add(domino);
            }
        }
        System.out.println(allDominoes);
        Collections.shuffle(allDominoes);
        Queue<Domino> stock = new LinkedList<>(allDominoes);
        System.out.println("Stock of size: " + stock.size() + " generated");
        System.out.println("Step 2. Create player and give them dominos");
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        for (int i = 0; i < 7; i++) {
            player1.getHand().add(stock.poll());
            player2.getHand().add(stock.poll());
        }

        System.out.println("Player 1 Hand: " + player1.getHand());
        System.out.println("Player 2 Hand: " + player2.getHand());
        List<Domino> board = new ArrayList<>();


        System.out.println("Step 3. Let's the game begin!");
        System.out.println("Player 1 move");
        System.out.println("Select an action");
        System.out.println("1. Show hand");
        System.out.println("2. Show another players hand size");
        System.out.println("3. Exit");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        gameEngine(reader, player1, player2, board);
        System.out.println("Select an action");
        System.out.println("1. Show hand");
        System.out.println("2. Show another players hand size");
        gameEngine(reader, player2, player1, board);
    }

    private static void gameEngine(BufferedReader reader, Player currentPlayer, Player anotherPlayer, List<Domino> board) {
        try {
            String playersChose = reader.readLine();
            if (playersChose.equals("1")) {
                System.out.println("My hand:");
                for (int i = 0; i < currentPlayer.getHandSize(); i++) {
                    System.out.println(i + ": " + currentPlayer.getHand().get(i));
                }
                System.out.println("Do you want to make a move?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String answer = reader.readLine();
                if (answer.equals("1")) {
                    System.out.println("Please enter domino index: ");
                    answer = reader.readLine();
                    int dominoIndex = Integer.parseInt(answer);
                    Domino domino = currentPlayer.getHand().get(dominoIndex);
                    board.add(domino);
                    currentPlayer.getHand().remove(dominoIndex);
                    System.out.println("Current board: " + board);
                    System.out.println("Current player 1 hand: " + currentPlayer.getHand());
                }
            } else if (playersChose.equals("2")) {
                System.out.println("Player 2 hand size is: " + anotherPlayer.getHandSize());
            } else if (playersChose.equals("3")) {
                System.exit(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
