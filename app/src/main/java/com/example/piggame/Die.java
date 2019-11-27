package com.example.piggame;

public class Die {
    private Integer maxSides;

    public Die(int sides) {
        this.maxSides = sides;
    }

    public int Roll() {
        return (int)(Math.random() * (maxSides) + 1);
    }
}
