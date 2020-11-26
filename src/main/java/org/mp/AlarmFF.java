package org.mp;

public class AlarmFF {
    private String[] eNR, eFF;
    //private int[] indizes_;
    private int eAmount;
    private String eOri;
    private String[] regEx = {"FWNR:", "FWNAME:"};
    private int[] indizes = new int[regEx.length], startIndizes = new int[regEx.length];

    public AlarmFF(String input, String amount){
        if(input != null) {
            this.eOri = input;
        }
        this.eAmount = Integer.parseInt(amount);
        eNR = new String[this.eAmount];
        eFF = new String[this.eAmount];
        //indizes_ = new int[this.eAmount];
        String[]temp = this.eOri.split("}");
        for(int i = 0; i < temp.length; i++){
            temp[i] =temp[i].replace("{", "");
        }
        for(int i = 0;i < temp.length; i++){
            if(temp[i].length() >= 10) {
                for (int j = 0; j < regEx.length; j++) {
                    indizes[j] = temp[i].indexOf(regEx[j]);
                }
                for (int j = 0; j < indizes.length; j++) {
                    startIndizes[j] = indizes[j] + regEx[j].length();
                }
                eNR[i] = temp[i].substring(startIndizes[0], indizes[1]);
                eFF[i] = temp[i].substring(startIndizes[1], temp[i].length());
            }
        }
    }
    public String print(){
        String output = "";
        for(int i = 0; i < eNR.length; i++){
            if(i == this.eAmount-1) {
                output += eNR[i] + eFF[i];
            }
            else
            {
                output += eNR[i] + eFF[i] + ", ";
            }
        }
        return output;
    }
}
