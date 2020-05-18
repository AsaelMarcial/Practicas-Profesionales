package models;

import java.io.File;
import java.util.Date;

public class Report {
    private int idReport;
    private int author;
    private String reportType;
    private Date uploadDate;
    private int score;
    private File report;

    public Report(int author, String reportType, Date uploadDate) {
        this.author = author;
        this.reportType = reportType;
        this.uploadDate = uploadDate;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public File getReport() {
        return report;
    }

    public void setReport(File report) {
        this.report = report;
    }
}
