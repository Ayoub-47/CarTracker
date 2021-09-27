package com.example.car.CarDataUtility;

public class CarData {
    private String marque;
    private String matricule;
    private String model;
    private String privateCode;

    public CarData(String marque, String matricule, String model , String privateCode) {
        this.marque = marque;
        this.matricule = matricule;
        this.model = model;
        this.privateCode = privateCode;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrivateCode() {
        return privateCode;
    }

    public void setPrivateCode(String privateCode) {
        this.privateCode = privateCode;
    }
}


