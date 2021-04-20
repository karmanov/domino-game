package io.karmanov.domino;

import io.karmanov.domino.model.Player;

public class DominoGame {

    public static void main(String[] args) {
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        GameController gameController = new GameController(p1, p2);
        gameController.simulateGame();
    }
}
