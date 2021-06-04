package com.celmelund.listview21spring.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note {

    private String id;
    private String text;

    public Note(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
