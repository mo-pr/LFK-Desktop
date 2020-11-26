package org.mp;

import java.io.*;
import java.sql.*;
import javafx.scene.control.TextArea;

public class Alarm {
    private String eNum, eOrt, eTime, eStatus, eStage, eType, eTypeDetail, eSubType, eAdress, eCoordinates, eDistrict, eFDAmount, eAmount ,eFDArray,ff;
    private AlarmType eDetail, eSub, eDist;
    private AlarmLocation eLocation;
    private AlarmCoords eCoords;
    private AlarmFF eFD;
    private byte[] data;
    private String[] regEx = {"NUM1:", "EINSATZORT:", "STARTZEIT:", "STATUS:", "ALARMSTUFE:", "EINSATZART:", "EINSATZTYP:", "EINSATZSUBTYP:", "ADRESSE:","WGS84:", "BEZIRK:", "CNTFEUERWEHREN:", "FEUERWEHRENARRAY:"};
    private int[] indizes = new int[regEx.length], startindizes = new int[regEx.length];
    private String[] regEx_2 = {"ID:", "TEXT:"};
    private int[] indizes_2 = new int[regEx_2.length], startIndizes_2 = new int[regEx_2.length];
    private  String[] regEx_3 = {"DEFAULT:", "EAREA:", "EMUN:", "EFEANME:", "ESTNUM:", "ECOMPL:"};
    private int[] indizes_3 = new int[regEx_3.length], startIndizes_3 = new int[regEx_3.length];
    private String[] regEx_4 = {"LNG:", "LAT:"};
    private int[] indizes_4 = new int[regEx_4.length], startIndizes_4 = new int[regEx_4.length];

    public Alarm(String alarmInfo, TextArea text, String ffString, File logFile){
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
            if(this.eFDArray.contains(this.ff)){
                Database db = new Database();
                db.Connect();
                Connection qnap = db.getQnap();
                try {
                    Statement stmt = qnap.createStatement();
                    String sql = String.format("INSERT INTO Einsätze VALUES (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');", eNum, eOrt, eTime, eStage, eType, eDetail.geteID(), eDetail.geteText(), eSub.geteID(), eSub.geteText(), eLocation.geteLoc(), eLocation.geteArea() + " / " + eLocation.geteMun(), eLocation.getEfeanme(), eLocation.getEstnum(), eLocation.getEcompl(), eCoords.print(), eDist.geteID(), eDist.geteText(), eAmount, eFD.print());
                    stmt.executeUpdate(sql);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        text.appendText(print());
        System.out.println(print());
    }
    private void ExtractFields(String input){
        input = input.replace("\"", "").replace(",", " ").replace("inzeit:", "").replace("\\", "");
        input = input.replace("u00c4", "AE");
        input = input.replace("u00e4", "ae");
        input = input.replace("u00d6", "OE");
        input = input.replace("u00f6", "oe");
        input = input.replace("u00df", "SS");
        input = input.replace("u00dc", "UE");
        input = input.replace("u00fc", "ue");
        input = input.toUpperCase();

        for(int i = 0; i < regEx.length; i++){
            indizes[i] = input.indexOf(regEx[i]);
        }
        for(int i = 0; i < indizes.length; i++){
            startindizes[i] = indizes[i]+regEx[i].length();
        }
        eNum = input.substring(startindizes[0], indizes[1]);
        eOrt = input.substring(startindizes[1], indizes[2]);
        eTime = input.substring(startindizes[2], indizes[3]);
        eStatus = input.substring(startindizes[3], indizes[4]).toUpperCase();
        eStage = input.substring(startindizes[4], indizes[5]);
        eType = input.substring(startindizes[5], indizes[6]);
        eTypeDetail = input.substring(startindizes[6], indizes[7]);
        eSubType = input.substring(startindizes[7], indizes[8]);
        eAdress = input.substring(startindizes[8], indizes[9]);
        eCoordinates = input.substring(startindizes[9], indizes[10]).replace("{", "").replace("}", "");
        eDistrict = input.substring(startindizes[10], indizes[11]);
        eFDAmount = input.substring(startindizes[11], indizes[12]);
        eFDArray = input.substring(startindizes[12], input.length()-1);

        for(int i = 0; i < regEx_2.length; i++){
            indizes_2[i] = eTypeDetail.indexOf(regEx_2[i]);
        }
        for(int i = 0; i < indizes_2.length; i++){
            startIndizes_2[i] = indizes_2[i]+regEx_2[i].length();
        }
        eDetail = new AlarmType(eTypeDetail.substring(startIndizes_2[0], indizes_2[1]), eTypeDetail.substring(startIndizes_2[1], eTypeDetail.length()-1));
        for(int i = 0; i < regEx_2.length; i++){
            indizes_2[i] = eSubType.indexOf(regEx_2[i]);
        }
        for(int i = 0; i < indizes_2.length; i++){
            startIndizes_2[i] = indizes_2[i]+regEx_2[i].length();
        }
        eSub = new AlarmType(eSubType.substring(startIndizes_2[0], indizes_2[1]), eSubType.substring(startIndizes_2[1], eSubType.length()-1));
        for(int i = 0; i < regEx_2.length; i++){
            indizes_2[i] = eDistrict.indexOf(regEx_2[i]);
        }
        for(int i = 0; i < indizes_2.length; i++){
            startIndizes_2[i] = indizes_2[i]+regEx_2[i].length();
        }
        eDist = new AlarmType(eDistrict.substring(startIndizes_2[0], indizes_2[1]), eDistrict.substring(startIndizes_2[1], eDistrict.length()-1));
        for(int i = 0; i < regEx_3.length; i++){
            indizes_3[i] = eAdress.indexOf(regEx_3[i]);
        }
        for(int i = 0; i < indizes_3.length; i++){
            startIndizes_3[i] = indizes_3[i]+regEx_3[i].length();
        }
        eLocation = new AlarmLocation(eAdress.substring(startIndizes_3[0], indizes_3[1]), eAdress.substring(startIndizes_3[1], indizes_3[2]), eAdress.substring(startIndizes_3[2], indizes_3[3]), eAdress.substring(startIndizes_3[3], indizes_3[4]), eAdress.substring(startIndizes_3[4], indizes_3[5]), eAdress.substring(startIndizes_3[5], eAdress.length()-1));
        for(int i = 0; i < regEx_4.length; i++){
            indizes_4[i] = eCoordinates.indexOf(regEx_4[i]);
        }
        for(int i = 0; i < indizes_4.length; i++){
            startIndizes_4[i] = indizes_4[i]+regEx_4[i].length();
        }
        eCoords = new AlarmCoords(eCoordinates.substring(startIndizes_4[1], eCoordinates.length()-1), eCoordinates.substring(startIndizes_4[0], indizes_4[1]));;
        String[] temp = eFDAmount.split(":");
        eAmount = temp[0].replaceAll("[^0-9]", "");
        eFD = new AlarmFF(eFDArray, eAmount);
    }
    public String print(){
        return String.format("\n"+eNum+"\n"+eOrt+"\nALARMZEIT: "+eTime+"\nEINSATZSTATUS: "+eStatus+"\nALARMSTUFE: "+eStage+"\n"+eType+"\n"+eDetail.print()+"\n"+eSub.print()+"\n"+eLocation.print()+"\n"+eCoords.print()+"\n"+eDist.print()+"\nFEUERWEHREN: "+eAmount+"\n"+eFD.print()+"\n");
    }
    public String geteFDArray() {
        return eFDArray;
    }
}
