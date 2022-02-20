package com.justinviasus.tenpercenthappierproject;

import java.util.ArrayList;

// A Topic Item to populate in the view holder
public class TopicItemCard {

    // Some properties are not used, however, it was mentioned that these are the relevant keys
    // for each topic. They may have potential later.
    private String uuid;
    private String title;
    private int position;
    private ArrayList<String> meditations;
    private boolean isFeatured;
    private String color;
    private String descriptionShort;

    public TopicItemCard(String uuid, String title, int position, ArrayList<String> meditations, boolean isFeatured, String color, String descriptionShort) {
        this.uuid = uuid;
        this.title = title;
        this.position = position;
        this.meditations = meditations;
        this.isFeatured = isFeatured;
        this.color = color;
        this.descriptionShort = descriptionShort;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<String> getMeditations() {
        return this.meditations;
    }

    public String getDescriptionShort() { return this.descriptionShort; }

    public String getColor() { return this.color; }
}
