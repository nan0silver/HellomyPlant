package com.nahyun.helloplant;

public class MyplantInformationData {

    private String myplant_information_attribute;
    private String myplant_information_value;

    public MyplantInformationData(String myplant_information_attribute, String myplant_information_value) {
        this.myplant_information_attribute = myplant_information_attribute;
        this.myplant_information_value = myplant_information_value;
    }

    public String getMyplant_information_attribute() {
        return myplant_information_attribute;
    }

    public void setMyplant_information_attribute(String myplant_information_attribute) {
        this.myplant_information_attribute = myplant_information_attribute;
    }

    public String getMyplant_information_value() {
        return myplant_information_value;
    }

    public void setMyplant_information_value(String myplant_information_value) {
        this.myplant_information_value = myplant_information_value;
    }
}
