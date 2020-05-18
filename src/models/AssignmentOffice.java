package models;

public class AssignmentOffice {
    private int idOffice;
    private int creator;
    private int practicing;
    private int project;

    public AssignmentOffice(int creator, int practicing, int project) {
        this.creator = creator;
        this.practicing = practicing;
        this.project = project;
    }

    public int getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(int idOffice) {
        this.idOffice = idOffice;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getPracticing() {
        return practicing;
    }

    public void setPracticing(int practicing) {
        this.practicing = practicing;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }
}
