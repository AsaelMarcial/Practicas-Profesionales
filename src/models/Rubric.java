package models;

import java.io.File;

public class Rubric {
    private int idRubric;
    private int educationalExperience;
    private File file;

    public Rubric(int educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public int getIdRubric() {
        return idRubric;
    }

    public void setIdRubric(int idRubric) {
        this.idRubric = idRubric;
    }

    public int getEducationalExperience() {
        return educationalExperience;
    }

    public void setEducationalExperience(int educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
