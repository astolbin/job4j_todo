package ru.job4j.controller;

public class Menu {
    private String name;
    private String path;
    private boolean current;

    public Menu(String name, String path) {
        this(name, path, false);
    }

    public Menu(String name, String path, boolean current) {
        this.name = name;
        this.path = path;
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
