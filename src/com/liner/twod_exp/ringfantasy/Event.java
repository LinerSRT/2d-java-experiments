package com.liner.twod_exp.ringfantasy;

public class Event {
    public int id;
    public int type;
    public String data;

    public Event(String event) {
        this.id = Resource.getInt(event, 1, '@');
        this.type = Resource.getInt(event, 2, '@');
        this.data = Resource.get(event, 3, '@');
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}
