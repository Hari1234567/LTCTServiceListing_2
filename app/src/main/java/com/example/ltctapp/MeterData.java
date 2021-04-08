package com.example.ltctapp;

public class MeterData {
    public String AccNo;
    public String CTCoilRatio;
    public String CTMake;
    public String ConsumerName;
    public String Division;
    public String Load;
    public String MF;
    public String MeterMake;
    public String MeterNo;
    public String PoNo;
    public String Section;
    public String ServiceDate;
    public String Sno;
    public String Tariff;
    public String __Class;
    public String SealNumber;
    public MeterData(String accNo, String CTCoilRatio, String CTMake, String consumerName, String division, String load, String MF, String meterMake, String meterNo, String poNo, String section, String serviceDate, String sno, String tariff, String __Class, String SealNumber) {
        AccNo = accNo;
        this.CTCoilRatio = CTCoilRatio;
        this.CTMake = CTMake;
        ConsumerName = consumerName;
        Division = division;
        Load = load;
        this.MF = MF;
        MeterMake = meterMake;
        MeterNo = meterNo;
        PoNo = poNo;
        Section = section;
        ServiceDate = serviceDate;
        Sno = sno;
        Tariff = tariff;
        this.__Class = __Class;
        this.SealNumber = SealNumber;
    }

    public MeterData(){}
}
