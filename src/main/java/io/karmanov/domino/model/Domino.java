package io.karmanov.domino.model;

import java.util.Objects;

public class Domino {
    private int leftValue;
    private int rightValue;

    public Domino(int leftValue, int rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public int getRightValue() {
        return rightValue;
    }

    public void flip() {
        int oldRLeftValue = this.leftValue;
        this.leftValue = this.rightValue;
        this.rightValue = oldRLeftValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domino domino = (Domino) o;
        return (leftValue == domino.leftValue && rightValue == domino.rightValue) ||
                (leftValue == domino.rightValue && rightValue == domino.leftValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftValue, rightValue);
    }

    @Override
    public String toString() {
        return "[" +
                "" + leftValue +
                " | " + rightValue +
                ']';
    }
}
