package org.example;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String nume;
    private double medie;
    private String cursOptional;
    private List<String> preferinte;

    public Student(String nume) {
        this.nume = nume;
        this.medie = 0;
        this.cursOptional = null;
        this.preferinte = new ArrayList<>();
    }

    public void addPreferinte(String preferinta) {
        this.preferinte.add(preferinta);
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public String getNume() {
        return nume;
    }

    public double getMedie() {
        return medie;
    }

    public String getCursOptional() {
        return cursOptional;
    }

    public void setCursOptional(String cursOptional) {
        this.cursOptional = cursOptional;
    }

    public List<String> getPreferinte() {
        return preferinte;
    }

}
