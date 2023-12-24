package org.example;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Curs<T extends Student> {
    private String denumire;
    private int capacitateMaxima;
    private List<Student> studentiInscrisi;
    private double mediaMinima;

    public Curs(String denumire, int capacitateMaxima) {
        this.denumire = denumire;
        this.capacitateMaxima = capacitateMaxima;
        this.studentiInscrisi = new ArrayList<>();
        this.mediaMinima = 10.0;
    }

    public void inscrieStudent(T student) {
            studentiInscrisi.add(student);
            if(student.getMedie() < this.mediaMinima){
                this.mediaMinima = student.getMedie();
            }
    }

    public String getNume(){
        return this.denumire;
    }

    public int getCapacitate(){
        return this.capacitateMaxima;
    }

    public List<Student> getStudentiInscrisi(){
            return this.studentiInscrisi;
        }

    public double getMediaMinima(){
        return this.mediaMinima;
    }

    public void setCapacitate(int capacitate){
        this.capacitateMaxima = capacitate;
    }

    public void setMediaMinima(double mediaMinima){
        this.mediaMinima = mediaMinima;
    }
}
