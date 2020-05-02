package com.slinger.recipeapp.domain;

public enum Difficulty {

    EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

    public final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return difficulty;
    }
}
