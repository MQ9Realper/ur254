package models;

/**
 * Created by dennismwebia on 7/21/17.
 */

public class Project {
    private String project_name;
    private String project_description;
    private Integer project_banner;

    public Project(String project_name, String project_description, Integer project_banner){
        this.project_name = project_name;
        this.project_description = project_description;
        this.project_banner =  project_banner;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public Integer getProject_banner() {
        return project_banner;
    }

    public void setProject_banner(Integer project_banner) {
        this.project_banner = project_banner;
    }
}
