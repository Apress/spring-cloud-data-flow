package com.apress.batch.moviecustom;

public class Movie {
    private String title;
    private String actor;
    private int year;

    public Movie(){}

    public Movie(String title, String actor, int year){
        this.title = title;
        this.actor = actor;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Movie(tile: ");
        builder.append(title);
        builder.append(", actor: ");
        builder.append(actor);
        builder.append(", year: ");
        builder.append(year);
        builder.append(")");
        return builder.toString();
    }
}
