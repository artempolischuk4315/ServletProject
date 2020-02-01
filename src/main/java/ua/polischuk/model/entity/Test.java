package ua.polischuk.model.entity;

import java.io.Serializable;

public class Test implements Serializable {
    private int id;
    private String name;
    private String nameUa;
    private Category category;
    private int difficulty;
    private int numberOfQuestions;
    private int timeLimit;
    private int result;
    private boolean active;

    public Test() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Test(String name, String nameUa, Category category, int difficulty, int numberOfQuestions, int timeLimit) {
        this.name = name;
        this.nameUa = nameUa;
        this.category = category;
        this.difficulty = difficulty;
        this.numberOfQuestions = numberOfQuestions;
        this.timeLimit = timeLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
