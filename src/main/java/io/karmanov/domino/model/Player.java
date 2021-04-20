package io.karmanov.domino.model;

import io.karmanov.domino.model.Domino;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Domino> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Domino> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }
}
