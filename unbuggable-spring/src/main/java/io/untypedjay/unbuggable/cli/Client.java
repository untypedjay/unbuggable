package io.untypedjay.unbuggable.cli;

import io.untypedjay.unbuggable.dao.EmployeeRepository;
import io.untypedjay.unbuggable.dao.IssueRepository;
import io.untypedjay.unbuggable.dao.ProjectRepository;
import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.domain.Issue;
import io.untypedjay.unbuggable.domain.Project;
import io.untypedjay.unbuggable.util.JpaUtil;
import io.untypedjay.unbuggable.util.Printer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;

import static io.untypedjay.unbuggable.util.Converter.toLocalDate;
import static io.untypedjay.unbuggable.util.TimeUtil.parseDuration;

public class Client {
  private final static String CONFIG_LOCATION = "io/untypedjay/unbuggable/test/applicationContext.xml";
  private static EntityManagerFactory emf;

  public static void main(String[] args) {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext (CONFIG_LOCATION)) {
      emf = factory.getBean(EntityManagerFactory.class);
      seedDatabase();
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

      String[] commands = promptFor(in, "").split(" ");

      while (!commands[0].equals("exit")) {
        switch (commands[0]) {
          case "list":
            if (commands.length > 1) {
              list(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "new":
            if (commands.length > 2 && commands.length < 7) {
              newEntity(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "add":
            if (commands.length == 5) {
              add(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "delete":
            if (commands.length == 3) {
              delete(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "remove":
            if (commands.length == 5) {
              remove(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "update":
            if (commands.length == 5) {
              update(commands);
            } else {
              Printer.printInvalidCommandError(commands);
            }
            break;

          case "help":
            if (commands.length >= 2) {
              Printer.printHelpPage(commands[1]);
            } else {
              Printer.printHelpPage("");
            }
            break;

          default:
            Printer.printInvalidCommandError(commands);
            break;
        }

        commands = promptFor(in, "").split(" ");
      }
    }
  }

  private static void seedDatabase() {
    JpaUtil.executeInTransaction(emf, () -> {
      EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);

      Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
      Employee empl2 = new Employee("Franz", "Heinzelmayr", LocalDate.of(1975, 9, 20));
      Employee empl3 = new Employee("Elon", "Musk", LocalDate.of(1880, 2, 2));

      Project proj1 = new Project("ProjectX");
      Project proj2 = new Project("TopSecret");
      empl1.addProject(proj1);
      empl3.addProject(proj1);
      empl2.addProject(proj2);
      empl3.addProject(proj2);

      Issue iss1 = new Issue("Configuration", proj1, Issue.Priority.NORMAL);
      Issue iss2 = new Issue("Implementation", proj1, Issue.Priority.HIGH);
      Issue iss3 = new Issue("Testing", proj1, Issue.Priority.LOW);
      Issue iss4 = new Issue("Planning", proj2, Issue.Priority.NORMAL);
      Issue iss5 = new Issue("Deployment", proj2, Issue.Priority.HIGH);
      iss2.setAssignee(empl3);
      iss5.setAssignee(empl2);
      iss4.setAssignee(empl3);
      iss5.setEstimatedTime(Duration.ofMinutes(40));
      iss2.setEstimatedTime(Duration.ofMinutes(34));
      iss5.addTime(Duration.ofMinutes(12));

      emplRepo.save(empl1);
      emplRepo.save(empl2);
      emplRepo.save(empl3);
      projectRepo.save(proj1);
      projectRepo.save(proj2);
      issueRepo.save(iss1);
      issueRepo.save(iss2);
      issueRepo.save(iss3);
      issueRepo.save(iss4);
      issueRepo.save(iss5);

      emplRepo.flush();
      projectRepo.flush();
      issueRepo.flush();
    });
  }

  private static void list(String[] commands) {
    switch (commands[1]) {
      case "project":
        if (commands.length == 2) { // list project
          Printer.printProjects(emf);
        } else if (commands.length == 3) { // list project ID
          Printer.printProject(emf, Long.parseLong(commands[2]));
        } else if (commands.length == 4) { // list project ID -I
          Printer.printProjectIssues(emf, Long.parseLong(commands[2]));
        } else if (commands.length == 5) { // list project ID -E ID
          Printer.printProjectIssuesByEmployee(emf, Long.parseLong(commands[2]), Long.parseLong(commands[4]));
        } else {
          Printer.printInvalidCommandError(commands);
        }

        break;
      case "employee":
        Printer.printEmployees(emf);
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void newEntity(String[] commands) {
    switch (commands[1]) {
      case "project":
        JpaUtil.executeInTransaction(emf, () -> {
          ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
          projectRepo.saveAndFlush(new Project(commands[2]));
        });
        break;
      case "issue":
        JpaUtil.executeInTransaction(emf, () -> {
          IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
          ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
          Project project = projectRepo.getOne(Long.parseLong(commands[3]));
          issueRepo.saveAndFlush(new Issue(commands[2], project, Issue.Priority.valueOf(commands[4].toUpperCase())));
        });
        break;
      case "employee":
        JpaUtil.executeInTransaction(emf, () -> {
          EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
          emplRepo.saveAndFlush(new Employee(commands[2], commands[3], toLocalDate(commands[4])));
        });
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void add(String[] commands) {
    if (commands[1].equals("employee") && commands[3].equals("project")) {
      JpaUtil.executeInTransaction(emf, () -> {
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
        ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
        Employee employee = emplRepo.getOne(Long.parseLong(commands[2]));
        Project project = projectRepo.getOne(Long.parseLong(commands[4]));
        employee.addProject(project);
        emplRepo.saveAndFlush(employee);
        projectRepo.saveAndFlush(project);
      });
    } else if (commands[1].equals("issue") && commands[3].equals("employee")) {
      JpaUtil.executeInTransaction(emf, () -> {
        IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
        Issue issue = issueRepo.getOne(Long.parseLong(commands[2]));
        Employee employee = emplRepo.getOne(Long.parseLong(commands[4]));
        issue.setAssignee(employee);
        issueRepo.saveAndFlush(issue);
        emplRepo.saveAndFlush(employee);
      });
    } else {
      Printer.printInvalidCommandError(commands);
    }
  }

  private static void delete(String[] commands) {
    switch (commands[1]) {
      case "project":
        JpaUtil.executeInTransaction(emf, () -> {
          ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
          projectRepo.delete(projectRepo.getOne(Long.parseLong(commands[2])));
        });
        break;
      case "issue":
        JpaUtil.executeInTransaction(emf, () -> {
          IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
          issueRepo.delete(issueRepo.getOne(Long.parseLong(commands[2])));
        });
        break;
      case "employee":
        JpaUtil.executeInTransaction(emf, () -> {
          EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
          emplRepo.delete(emplRepo.getOne(Long.parseLong(commands[2])));
        });
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void remove(String[] commands) {
    if (commands[1].equals("employee") && commands[3].equals("project")) {
      JpaUtil.executeInTransaction(emf, () -> {
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
        ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
        Employee employee = emplRepo.getOne(Long.parseLong(commands[2]));
        Project project = projectRepo.getOne(Long.parseLong(commands[4]));
        employee.removeProject(project);
        emplRepo.saveAndFlush(employee);
        projectRepo.saveAndFlush(project);
      });
    } else {
      Printer.printInvalidCommandError(commands);
    }
  }

  private static void update(String[] commands) {
    switch (commands[1]) {
      case "issue":
        if (commands[3].equals("-T")) {
          JpaUtil.executeInTransaction(emf, () -> {
            IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
            Issue issue = issueRepo.getOne(Long.parseLong(commands[2]));
            issue.addTime(parseDuration(commands[4]));
          });
        } else if (commands[3].equals("-E")) {
          JpaUtil.executeInTransaction(emf, () -> {
            IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
            Issue issue = issueRepo.getOne(Long.parseLong(commands[2]));
            issue.setEstimatedTime(parseDuration(commands[4]));
          });
        } else if (commands[3].equals("-S")) {
          JpaUtil.executeInTransaction(emf, () -> {
            IssueRepository issueRepo = JpaUtil.getJpaRepository(emf, IssueRepository.class);
            Issue issue = issueRepo.getOne(Long.parseLong(commands[2]));
            issue.setState(Issue.State.valueOf(commands[4].toUpperCase()));
          });
        } else {
          Printer.printInvalidCommandError(commands);
        }
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }
}