package org.mp;

public class AlarmCoords {
    private String lat, lng;

    public AlarmCoords(String lat, String lng){
        this.lat = lat;
        this.lng = lng;
    }
    public String print(){
        return String.format("N"+this.lat+" E"+this.lng);
    }
}
