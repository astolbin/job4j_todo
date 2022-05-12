package ru.job4j.controller;

public class Action {
    private String name;
    private String action;
    private String view;

    public Action(String name, String action, String view) {
        this.name = name;
        this.action = action;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
