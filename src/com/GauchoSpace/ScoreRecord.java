package com.GauchoSpace;

public class ScoreRecord implements Comparable<ScoreRecord> {

    private String name;
    private int points;
 
    public ScoreRecord(String name, Integer point) {
        this.name = name;
        this.points = point;
    }

    public int compareTo(ScoreRecord score) {
        ScoreRecord comp = (ScoreRecord) score;
        if (comp.getPoints() == this.getPoints()) {
            return 0;
        }
        if (comp.getPoints() < this.getPoints()) {
            return -1;
        } else {
            return 1;
        }
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }
}
