package org.mp;

public class AlarmLocation {
    private String eLoc, eArea, eMun, efeanme, estnum, ecompl;

    public String geteLoc() {
        return eLoc;
    }
    public String geteArea() {
        return eArea;
    }
    public String geteMun() {
        return eMun;
    }
    public String getEfeanme() {
        return efeanme;
    }
    public String getEstnum() {
        return estnum;
    }
    public String getEcompl() {
        return ecompl;
    }
    public AlarmLocation(String loc, String area, String mun, String feanme, String stnum, String compl){
        this.eLoc = loc.replace("{", "").replace("}", "").toUpperCase();
        this.eArea = area.replace("{", "").replace("}", "").toUpperCase();
        this.eMun = mun.replace("{", "").replace("}", "").toUpperCase();
        this.efeanme = feanme.replace("{", "").replace("}", "").toUpperCase();
        this.estnum = stnum.replace("{", "").replace("}", "").toUpperCase();
        this.ecompl = compl.replace("{", "").replace("}", "").toUpperCase();
    }
    public String print(){
        return String.format("ADRESSE: "+this.eLoc+"\nORT: "+this.eArea+"/ "+this.eMun+"\nSTRASSE: "+this.efeanme+" HAUSNR.: "+this.estnum+"\nZUSATZ: "+this.ecompl);
    }
}
