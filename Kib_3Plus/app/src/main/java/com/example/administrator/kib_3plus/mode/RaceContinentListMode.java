package com.example.administrator.kib_3plus.mode;

/**
 * Created by cui on 2017/7/21.
 */

public class RaceContinentListMode {
    private String name;
    private String steps;
    private int icon;


    public RaceContinentListMode(String name, String steps, int icon) {
        this.name = name;
        this.steps = steps;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "RaceContinentListMode{" +
                "name='" + name + '\'' +
                ", steps='" + steps + '\'' +
                ", icon=" + icon +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
