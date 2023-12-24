package org.example;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String filename = "src/main/resources/"+args[0]+ "/" + args[0] +".in";
        Secretariat secretariat = new Secretariat();
        String numeFisier ="src/main/resources/"+args[0] + "/" + args[0] + ".out";
        try(BufferedReader br= new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                String command= line.split(" ")[0];

                switch(command){
                    case "adauga_student":
                    String tip = line.split(" ")[2];
                    String nume = line.split(" ")[4];
                        try {
                            secretariat.adaugaStudent(nume, tip);
                        } catch (StudentDuplicatException e) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier, true))) {
                                writer.write("***");
                                writer.newLine();
                                writer.write(e.getMessage());
                                writer.newLine();
                            } catch (IOException ignored) {
                            }
                        }
                    break;

                    case "citeste_mediile":
                    int nrfisier=1;
                    while(true){
                        String numeNote ="src/main/resources/"+args[0] + "/note_" + nrfisier + ".txt";
                        try(BufferedReader br2= new BufferedReader(new FileReader(numeNote))) {
                            String line2 = br2.readLine();
                            while (line2 != null) {
                                String numeStudent = line2.split(" ")[0];
                                double medie = Double.parseDouble(line2.split(" ")[2]);
                                secretariat.adaugaMedie(numeStudent, medie);
                                line2 = br2.readLine();
                            }
                            nrfisier++;
                        } catch (IOException e) {
                            break;
                        }
                    }
                    break;

                    case "posteaza_mediile":
                    secretariat.unesteStudenti();
                    secretariat.afisare(numeFisier);
                    break;

                    case "contestatie":
                    String numeContestant= line.split(" ")[2];
                    double notaNoua= Double.parseDouble(line.split(" ")[4]);
                    secretariat.contesteaza(numeContestant,notaNoua);
                    break;

                    case "adauga_curs":
                    String program = line.split(" ")[2];
                    String numeCurs= line.split(" ")[4];
                    int capacitate= Integer.parseInt(line.split(" ")[6]);
                    secretariat.adaugaCurs(numeCurs, capacitate, program);
                    break;

                    case "adauga_preferinte":
                    String numeStudent= line.split(" ")[2];
                    int bound=4;
                    while(bound<line.split(" ").length){
                        String preferinta= line.split(" ")[bound];
                        secretariat.adaugaPreferinte(numeStudent, preferinta);
                        bound=bound+2;
                    }
                    break;

                    case "repartizeaza":
                    secretariat.repartizeaza();
                    break;

                    case "posteaza_curs":
                    String numeCursPostat= line.split(" ")[2];
                    secretariat.afiseazaCurs(numeCursPostat, numeFisier);
                    break;

                    case "posteaza_student":
                    String numeStudentPostat= line.split(" ")[2];
                    secretariat.afiseazaStudent(numeStudentPostat, numeFisier);
                    break;

                    default:
                        System.out.println("Invalid command");
                        break;
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
    }
}
