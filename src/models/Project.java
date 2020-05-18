package models;

public class Project {
    private int idProject;
    private String projectName;
    private String linkedOrganization;
    private String domain;
    private String description;
    private int projectInfo;

    public Project(String projectName, String linkedOrganization, String domain, String description, int projectInfo) {
        this.projectName = projectName;
        this.linkedOrganization = linkedOrganization;
        this.domain = domain;
        this.description = description;
        this.projectInfo = projectInfo;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLinkedOrganization() {
        return linkedOrganization;
    }

    public void setLinkedOrganization(String linkedOrganization) {
        this.linkedOrganization = linkedOrganization;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(int projectInfo) {
        this.projectInfo = projectInfo;
    }
}
