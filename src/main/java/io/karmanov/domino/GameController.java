package io.karmanov.domino;

import io.karmanov.domino.model.Domino;
import io.karmanov.domino.model.Player;

import java.util.*;

public class GameController {
    private Player player1;
    private Player player2;
    private Queue<Domino> stock;
    private LinkedList<Domino> board = new LinkedList<>();
    private Player currentPlayer;
    private Player winner;
    private boolean isGameFinished;

    public GameController(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void initStock() {
        System.out.println("initialize the stoke....STARTED");
        List<Domino> allDominoes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                Domino domino = new Domino(i, j);
                if (!allDominoes.contains(domino))
                    allDominoes.add(domino);
            }
        }

        for (int i = 0; i < 7; i++) {
            Domino domino = new Domino(i, 6-i);
        }



        Collections.shuffle(allDominoes);
        stock = new LinkedList<>(allDominoes);
        System.out.println("initialize the stoke....DONE");
    }

    public void startGame() {
        System.out.println("Hand over dominoes....STARTED");
        for (int i = 0; i < 7; i++) {
            player1.getHand().add(stock.poll());
            player2.getHand().add(stock.poll());
        }
        currentPlayer = player1;
        System.out.println("Player 1 hand: " + player1.getHand());
        System.out.println("Player 2 hand: " + player2.getHand());
        System.out.println("Hand over dominoes....DONE");
    }

    public void simulateGame() {
        initStock();
        startGame();
        while (!isGameFinished) {
            makeMove();
        }
        System.out.println("Game finished");
        System.out.println(winner.getName() + " won!!!");
    }

    public void makeMove() {
        System.out.println("************************* - " + currentPlayer.getName() + " move - ********************************");
        selectAndPlaceTile();
        System.out.println("Current board: " + board);
        System.out.println("Player 1 hand: " + player1.getHand());
        System.out.println("Player 2 hand: " + player2.getHand());
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    public void selectAndPlaceTile() {
        // 1. Check if board is currently empty, so can play any domino
        if (board.isEmpty()) {
            Domino domino = currentPlayer.getHand().remove(0);
            board.addFirst(domino);
            System.out.println(currentPlayer.getName() + " place domino: " + domino + " to the head");
            //2. If board already have some dominoes
        } else {
            // 2.1 Check the current player dominoes in hand
            boolean dominoPlaced = false;
            List<Domino> playerHand = currentPlayer.getHand();
            for (Domino domino : playerHand) {
                boolean canBePlacedOnLeft = isCanBePlacedOnLeft(board.getFirst(), domino);
                if (canBePlacedOnLeft) {
                    placeDominoToTheLeft(domino);
                    playerHand.remove(domino);
                    dominoPlaced = true;
                    break;
                } else if (isCanBePlacedOnRight(board.getLast(), domino)) {
                    placeDominoToTheRight(domino);
                    playerHand.remove(domino);
                    dominoPlaced = true;
                    break;
                }
            }
            if (playerHand.isEmpty()) {
                finishTheGame(currentPlayer);
            }
            // 2.2 If player has no fitting domino in the hand, take one from the stock and check
            while (!dominoPlaced && !isGameFinished) {
                if (!stock.isEmpty()) {
                    Domino dominoFromStock = stock.poll();
                    boolean canBePlacedOnLeft = isCanBePlacedOnLeft(board.getFirst(), dominoFromStock);
                    if (canBePlacedOnLeft) {
                        placeDominoToTheLeft(dominoFromStock);
                        dominoPlaced = true;
                    } else if (isCanBePlacedOnRight(board.getLast(), dominoFromStock)) {
                        placeDominoToTheRight(dominoFromStock);
                        dominoPlaced = true;
                    }
                    if (!dominoPlaced) {
                        currentPlayer.getHand().add(dominoFromStock);
                    }
                } else {
                    // 2.3 If stock is empty, current player is block. There previous player who made a move wins.
                    Player winner = currentPlayer.equals(player1) ? player2 : player1;
                    finishTheGame(winner);
                }
            }
        }
    }

    public void placeDominoToTheLeft(Domino domino) {
        System.out.println(currentPlayer.getName() + " place " + domino + " on the left");
        board.addFirst(domino);
    }

    public void placeDominoToTheRight(Domino domino) {
        System.out.println(currentPlayer.getName() + " place " + domino + " on the right");
        board.addLast(domino);
    }

    public static boolean isCanBePlacedOnLeft(Domino dominoOnBoard, Domino dominoToCheck) {
        boolean couldBePlaced = false;
        int leftValue = dominoOnBoard.getLeftValue();
        if (leftValue != dominoToCheck.getRightValue()) {
            if (leftValue == dominoToCheck.getLeftValue()) {
                dominoToCheck.flip();
                couldBePlaced = true;
            }
        } else {
            couldBePlaced = true;
        }
        return couldBePlaced;
    }


    public static boolean isCanBePlacedOnRight(Domino dominoOnBoard, Domino dominoToCheck) {
        boolean couldBePlaced = false;
        int rightValue = dominoOnBoard.getRightValue();
        if (rightValue != dominoToCheck.getLeftValue()) {
            if (rightValue == dominoToCheck.getRightValue()) {
                dominoToCheck.flip();
                couldBePlaced = true;
            }
        } else {
            couldBePlaced = true;
        }
        return couldBePlaced;
    }

    public void finishTheGame(Player winner) {
        isGameFinished = true;
        this.winner = winner;
    }
}
