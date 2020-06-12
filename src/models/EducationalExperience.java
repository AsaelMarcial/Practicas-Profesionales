package models;

import java.io.File;
import java.util.List;

public class EducationalExperience {
    private int NRC;
    private String name;
    private int professor;
    private File enlistedPractitioners;

    public EducationalExperience(String name, int professor, File enlistedPractitioners) {
        this.name = name;
        this.professor = professor;
        this.enlistedPractitioners = enlistedPractitioners;
    }

    public int getNRC() {
        return NRC;
    }

    public void setNRC(int NRC) {
        this.NRC = NRC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfessor() {
        return professor;
    }

    public void setProfessor(int professor) {
        this.professor = professor;
    }

    public File getEnlistedPractitioners() {
        return enlistedPractitioners;
    }

    public void setEnlistedPractitioners(File enlistedPractitioners) {
        this.enlistedPractitioners = enlistedPractitioners;
    }
}
