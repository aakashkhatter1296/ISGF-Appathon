package com.nsit.hack.energy;

public class RootItem {

    private String title;
    private int image;
    private String reading;

    public RootItem(String title, int image, String reading) {
        this.title = title;
        this.image = image;
        this.reading = reading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }
}