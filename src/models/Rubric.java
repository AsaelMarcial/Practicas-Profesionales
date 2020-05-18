package models;

public class Rubric {
    private int educationalExperience;
    private String filePath;

    public Rubric(int educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public int getEducationalExperience() {
        return educationalExperience;
    }

    public void setEducationalExperience(int educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
