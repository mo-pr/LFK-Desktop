package org.preining;

import java.io.*;
import javafx.scene.control.TextArea;
import org.json.JSONObject;

public class Alarm {
    private String eNum, eOrt, eTime, eStatus, eStage, eType, eTypeDetail, eSubType, eAdress, eCoordinates, eDistrict, eFDAmount,eFDArray = "",ff;
    private byte[] data;

    public Alarm(JSONObject alarmInfo, TextArea text, String ffString, File logFile){
        if(ffString != null){
            this.ff = ffString;
        }
        ExtractFields(alarmInfo);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(logFile);
            this.data = new byte[(int) logFile.length()];
            fis.read(this.data);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dataString = new String(data);
        if(dataString.contains(this.eNum.replace(" ", ""))== false) {
            String temp = this.eNum.replace(" ", "") + ";";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
                writer.write(temp);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        text.appendText(print());
        System.out.println(print());
    }
    private void ExtractFields(JSONObject input){
        JSONObject temptype = new JSONObject(input.get("einsatztyp").toString());
        JSONObject tempsubtype = new JSONObject(input.get("einsatzsubtyp").toString());
        JSONObject tempcoordinates = new JSONObject(input.get("wgs84").toString());
        JSONObject tempdistrict = new JSONObject(input.get("bezirk").toString());
        JSONObject tempadress = new JSONObject(input.get("adresse").toString());
        JSONObject tempffarray = new JSONObject(input.get("feuerwehrenarray").toString());
        JSONObject tempff;
        eNum = input.get("num1").toString();
        eOrt = input.get("einsatzort").toString();
        eTime = input.get("startzeit").toString();
        eStatus = input.get("status").toString();
        eStage = input.get("alarmstufe").toString();
        eType = input.get("einsatzart").toString();
        eTypeDetail = temptype.get("text").toString();
        eSubType = tempsubtype.get("text").toString();
        eAdress = "ADRESSE: "+tempadress.get("default").toString()+"\nORT: "+tempadress.get("earea").toString()+" / "+tempadress.get("emun")+"\nSTRASSE: "+tempadress.get("efeanme")+" Hausnr.: "+tempadress.get("estnum")+"\nZUSATZ: "+tempadress.get("ecompl");
        eCoordinates = tempcoordinates.get("lat").toString()+" "+tempcoordinates.get("lng").toString();
        eDistrict = tempdistrict.get("text").toString();
        eFDAmount = input.get("cntfeuerwehren").toString();
        for(int i = 0; i < Integer.parseInt(eFDAmount); i++){
            String temp = String.valueOf(i);
            tempff = new JSONObject(tempffarray.get(temp).toString());
            eFDArray += "\n"+tempff.get("fwnr").toString()+": "+tempff.get("fwname").toString();
        }
    }
    public String print(){
        return String.format(eNum+"\n"+eOrt+"\nALARMZEIT: "+eTime+"\nEINSATZSTATUS: "+eStatus+"\nALARMSTUFE: "+eStage+"\n"+eType+"\n"+eTypeDetail+"\n"+eSubType+"\n"+eAdress+"\n"+eCoordinates+"\n"+eDistrict+"\nFEUERWEHREN: "+eFDAmount+eFDArray+"\n\n").toUpperCase();
    }
}
