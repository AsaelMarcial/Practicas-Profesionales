package models;

import java.util.List;

public class EducationalExperience {
    private int NRC;
    private String name;
    private int professor;
    private List<Integer> enlistedPractitioners;

    public EducationalExperience(String name, int professor, List<Integer> enlistedPractitioners) {
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

    public List<Integer> getEnlistedPractitioners() {
        return enlistedPractitioners;
    }

    public void setEnlistedPractitioners(List<Integer> enlistedPractitioners) {
        this.enlistedPractitioners = enlistedPractitioners;
    }
}
