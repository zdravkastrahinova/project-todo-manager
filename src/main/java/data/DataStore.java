package data;

import models.Project;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public List<User> getUsersList() {
        return new ArrayList<User>() {{
            add(new User("William Smith", "Manager"));
            add(new User("Amy Adams", "Developer"));
            add(new User("John Allan", "Developer"));
        }};
    }

    public List<Project> getProjectsList() {
        List<Project> projects = new ArrayList<Project>() {{
            add(new Project("HelloWorld", "Simple console Java project"));
            add(new Project("Awesome", "Simple Java project with Hibernate"));
            add(new Project("Spring", "Setup Java project with Spring Boot"));
        }};

        Project project = projects.stream().filter(p -> p.getTitle().equals("Awesome")).findFirst().orElse(null);
        project.setUsers(this.getUsersList());

        List<Project> subProjects = new ArrayList<Project>() {{
            add(new Project("SilverMoon", "Simple console Java project"));
            add(new Project("GoldWays", "Simple Java project with Hibernate"));
            add(new Project("TheBees", "Setup Java project with Spring Boot"));
        }};

        project.setSubProjects(subProjects);

        return projects;
    }
}
