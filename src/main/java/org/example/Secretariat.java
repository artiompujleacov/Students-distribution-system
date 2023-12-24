package org.example;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.*;


public class Secretariat {
    private List<StudentLicenta> studentiLicenta;
    private List<StudentMaster> studentiMaster;
    private List<Curs<StudentLicenta>> cursuriLicenta;
    private List<Curs<StudentMaster>> cursuriMaster;
    private List<Student> studenti;

    public Secretariat() {
        this.studentiLicenta = new ArrayList<>();
        this.studentiMaster = new ArrayList<>();
        this.cursuriLicenta = new ArrayList<>();
        this.cursuriMaster = new ArrayList<>();
        this.studenti = new ArrayList<>();
    }

    public void adaugaStudent(String nume, String tip) throws StudentDuplicatException {

        if (existaStudentCuNume(nume)) {
            throw new StudentDuplicatException("Student duplicat: " + nume);
        }

        if (tip.equals("licenta")) {
            StudentLicenta student = new StudentLicenta(nume);
            studentiLicenta.add(student);
        } else {
            StudentMaster student = new StudentMaster(nume);
            studentiMaster.add(student);
        }
    }

    private boolean existaStudentCuNume(String nume) {
        for (StudentLicenta student : studentiLicenta) {
            if (student.getNume().equals(nume)) {
                return true;
            }
        }

        for (StudentMaster student : studentiMaster) {
            if (student.getNume().equals(nume)) {
                return true;
            }
        }

        return false;
    }

    public void adaugaMedie(String nume , double medie){
        for(StudentLicenta student: studentiLicenta){
            if(student.getNume().equals(nume)){
                student.setMedie(medie);
                return;
            }
        }
        for(StudentMaster student: studentiMaster){
            if(student.getNume().equals(nume)){
                student.setMedie(medie);
                return;
            }
        }
    }

    public void adaugaCurs(String numeCurs,int capacitate, String tip){
        if(tip.equals("licenta")){
            Curs<StudentLicenta> cursLicenta = new Curs<>(numeCurs, capacitate);
            cursuriLicenta.add(cursLicenta);
        }else{
            Curs<StudentMaster> cursMaster = new Curs<>(numeCurs, capacitate);
            cursuriMaster.add(cursMaster);
        }
    }

    public void adaugaPreferinte(String nume, String preferinta){
      for(Student student : studenti){
        if(student.getNume().equals(nume)){
          student.addPreferinte(preferinta);
        }
      }
    }

    public void unesteStudenti() {
        for (StudentLicenta student : studentiLicenta) {
            if (!studenti.contains(student)) {
                studenti.add(student);
            }
        }

        for (StudentMaster student : studentiMaster) {
            if (!studenti.contains(student)) {
                studenti.add(student);
            }
        }

        studenti.sort((s1, s2) -> {
            int medieComparison = Double.compare(s2.getMedie(), s1.getMedie());
            if (medieComparison == 0) {
                return s1.getNume().compareTo(s2.getNume());
            }
            return medieComparison;
        });
    }

    public void afisare(String numeFisier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier, true))) {
            writer.println("***");
            for (Student student : studenti) {
                writer.println(student.getNume() + " - " + student.getMedie());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contesteaza(String nume, double medie){
        for(StudentLicenta student : studentiLicenta){
            if(student.getNume().equals(nume)){
                student.setMedie(medie);
                studentiMaster.sort((s1, s2) -> {
                    int medieComparison = Double.compare(s2.getMedie(), s1.getMedie());
                    if (medieComparison == 0) {
                        return s1.getNume().compareTo(s2.getNume());
                    }
                    return medieComparison;
                });
                return;
            }
        }

        for(StudentMaster student : studentiMaster){
            if(student.getNume().equals(nume)){
                student.setMedie(medie);
                studentiLicenta.sort((s1, s2) -> {
                    int medieComparison = Double.compare(s2.getMedie(), s1.getMedie());
                    if (medieComparison == 0) {
                        return s1.getNume().compareTo(s2.getNume());
                    }
                    return medieComparison;
                });
                return;
            }
        }

        for(Student student : studenti){
            if(student.getNume().equals(nume)){
                student.setMedie(medie);
                studenti.sort((s1, s2) -> {
                    int medieComparison = Double.compare(s2.getMedie(), s1.getMedie());
                    if (medieComparison == 0) {
                        return s1.getNume().compareTo(s2.getNume());
                    }
                    return medieComparison;
                });
                return;
            }
        }

    }

    public void repartizeaza() {
        studenti.sort((s1, s2) -> {
            int medieComparison = Double.compare(s2.getMedie(), s1.getMedie());
            if (medieComparison == 0) {
                return s1.getNume().compareTo(s2.getNume());
            }
            return medieComparison;
        });

        cursuriLicenta.sort((c1, c2) -> {
            int capacitateComparison = Integer.compare(c1.getCapacitate(), c2.getCapacitate());
            if (capacitateComparison == 0) {
                return c1.getNume().compareTo(c2.getNume());
            }
            return capacitateComparison;
        });

        cursuriMaster.sort((c1, c2) -> {
            int capacitateComparison = Integer.compare(c1.getCapacitate(), c2.getCapacitate());
            if (capacitateComparison == 0) {
                return c1.getNume().compareTo(c2.getNume());
            }
            return capacitateComparison;
        });

        for (Student student : studenti) {
            boolean distributed = false;

            for (String preferinta : student.getPreferinte()) {
                for (Curs curs : cursuriLicenta) {
                    if (curs.getNume().equals(preferinta) && (curs.getStudentiInscrisi().size() < curs.getCapacitate() || curs.getMediaMinima() == student.getMedie())) {
                        curs.inscrieStudent(student);
                        student.setCursOptional(preferinta);
                        distributed = true;
                        break;
                    }
                }

                if (distributed) {
                    break;
                }

                for (Curs curs : cursuriMaster) {
                    if (curs.getNume().equals(preferinta) && (curs.getStudentiInscrisi().size() < curs.getCapacitate() || curs.getMediaMinima() == student.getMedie())) {
                        curs.inscrieStudent(student);
                        student.setCursOptional(preferinta);
                        distributed = true;
                        break;
                    }
                }

                if (distributed) {
                    break;
                }
            }
            if (!distributed) {
                for (Curs curs : cursuriLicenta) {
                    if (curs.getStudentiInscrisi().size() < curs.getCapacitate()) {
                        curs.inscrieStudent(student);
                        student.setCursOptional(curs.getNume());
                        distributed = true;
                        break;
                    }
                }

                if (!distributed) {
                    for (Curs curs : cursuriMaster) {
                        if (curs.getStudentiInscrisi().size() < curs.getCapacitate()) {
                            curs.inscrieStudent(student);
                            student.setCursOptional(curs.getNume());
                            break;
                        }
                    }
                }
            }
        }
    }





    public void afiseazaCurs(String numeCurs, String numeFisier){

        try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier, true))) {
            writer.println("***");
            for(Curs curs : cursuriLicenta){
                if(curs.getNume().equals(numeCurs)){
                    writer.println(curs.getNume()+" ("+curs.getCapacitate()+ ")");
                    break;
                }
            }

            for(Curs curs : cursuriMaster){
                if(curs.getNume().equals(numeCurs)){
                    writer.println(curs.getNume()+" ("+curs.getCapacitate()+ ")");
                    break;
                }
            }

            List <Student> studentiDeAfisat = new ArrayList<>();
            for(Student student : studenti){
                if(student.getCursOptional().equals(numeCurs)){
                    studentiDeAfisat.add(student);
                }
            }
            Collections.sort(studentiDeAfisat, Comparator.comparing(Student::getNume));
            for (Student student : studentiDeAfisat) {
                if(student.getCursOptional().equals(numeCurs)){
                    writer.println(student.getNume() + " - " + student.getMedie());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afiseazaStudent(String nume,String numeFisier){
        for(StudentLicenta student : studentiLicenta){
            if(student.getNume().equals(nume)){
                try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier, true))) {
                    writer.println("***");
                    writer.println("Student Licenta: "+student.getNume() + " - " + student.getMedie()+ " - " + student.getCursOptional());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
    }

            for(StudentMaster student : studentiMaster){
                if(student.getNume().equals(nume)){
                    try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier, true))) {
                        writer.println("***");
                        writer.println("Student Master: "+student.getNume() + " - " + student.getMedie()+ " - " + student.getCursOptional());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
    }

    public List<Student> getStudenti() {
        return studenti;
    }

}
