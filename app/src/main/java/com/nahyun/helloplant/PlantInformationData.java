package com.nahyun.helloplant;

public class PlantInformationData {

    private String plant_information_attribute;
    private String plant_information_value;

    public PlantInformationData(String plant_information_attribute, String plant_information_value) {
        this.plant_information_attribute = plant_information_attribute;
        this.plant_information_value = plant_information_value;
    }

    public String getPlant_information_attribute() {
        return plant_information_attribute;
    }

    public void setPlant_information_attribute(String plant_information_attribute) {
        this.plant_information_attribute = plant_information_attribute;
    }

    public String getPlant_information_value() {
        return plant_information_value;
    }

    public void setPlant_information_value(String plant_information_value) {
        this.plant_information_value = plant_information_value;
    }
}
