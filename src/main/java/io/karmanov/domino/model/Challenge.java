package io.karmanov.domino.model;

import io.karmanov.domino.GameController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Challenge {

    public static void main(String[] args) {
    }


    // start = [... | 6]
    // end = [2 | ...]
    // pool = [4 | 6], [2 | 1], [2, 1], [2, 4]
    // pool =  ,


    // solution: [2, 4]......[2 | 1]


    private static boolean hasSolution(Domino start, Domino end, List<Domino> pool) {
        if(pool.isEmpty() && start.equals(end)) {
            return true;
        }
        for (Domino domino : pool) {
            boolean canBePlacedOnLeft = GameController.isCanBePlacedOnLeft(start, domino);
            if (canBePlacedOnLeft) {
                List<Domino> objects = new ArrayList<>(pool);
                objects.remove(domino);
                return hasSolution(domino, end, objects);
            }
            if (GameController.isCanBePlacedOnRight(end, domino)) {
                List<Domino> objects = new ArrayList<>(pool);
                objects.remove(domino);
                return hasSolution(start, domino, objects);
            }

        }
        return false;
    }

}
