package org.mp;

public class AlarmType {
    private String eID, eText;

    public String geteID() {
        return eID;
    }
    public String geteText() {
        return eText;
    }
    public AlarmType(String id, String text){
        this.eID = id.replace("{", "").replace("}", "").toUpperCase();
        this.eText = text.replace("{", "").replace("}", "").toUpperCase();
    }
    public String print(){
        return String.format("ID: "+this.eID+"\nTEXT: "+this.eText);
    }
}
