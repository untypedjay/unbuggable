package io.untypedjay.unbuggable.util;

import io.untypedjay.unbuggable.dao.EmployeeRepository;
import io.untypedjay.unbuggable.dao.ProjectRepository;
import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.domain.Issue;
import io.untypedjay.unbuggable.domain.Project;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static io.untypedjay.unbuggable.util.TimeUtil.formatDuration;

public class Printer {
  public static void printInvalidCommandError(String[] commandArray) {
    System.out.println("'" + String.join(" ", commandArray) + "' is not a valid command");
    System.out.println("See 'help'");
  }

  public static void printProjects(EntityManagerFactory emf) {
    JpaUtil.executeInTransaction(emf, () -> {
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      System.out.println("ID                          NAME");
      List<Project> projects = projectRepo.findAll();
      for (var project : projects) {
        System.out.format("%2s", project.getId());
        System.out.format("%30s", project.getName());
        System.out.println();
      }
    });
  }

  public static void printProject(EntityManagerFactory emf, Long projectId) {
    JpaUtil.executeInTransaction(emf, () -> {
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      System.out.println("ID                          NAME       DATE OF BIRTH");
      Project project = projectRepo.getOne(projectId);
      Set<Employee> members = project.getEmployees();
      for (var member : members) {
        System.out.format("%2s", member.getId());
        System.out.format("%30s", member.getFirstName() + " " + member.getLastName());
        System.out.format("%20s", member.getDateOfBirth());
        System.out.println();
      }
    });
  }

  public static void printProjectIssues(EntityManagerFactory emf, Long projectId) {
    JpaUtil.executeInTransaction(emf, () -> {
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      Project project = projectRepo.getOne(projectId);
      printIssues(project.getIssues(), null);
    });
  }

  public static void printProjectIssuesByEmployee(EntityManagerFactory emf, Long projectId, Long employeeId) {
    JpaUtil.executeInTransaction(emf, () -> {
      EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      Employee employee = emplRepo.getOne(employeeId);
      System.out.println("===" + employee.getFirstName() + " " + employee.getLastName() + "===");
      Project project = projectRepo.getOne(projectId);
      printIssues(project.getIssues(), employee);
    });
  }

  private static void printIssues(Set<Issue> issues, Employee employee) {
    Duration timeLeft = Duration.ofSeconds(0);
    Duration alreadyDone = Duration.ofSeconds(0);
    System.out.println("ID                NAME     STATE     PRIORITY    ESTIMATED     EXPENDED    FULFILLMENT                      ASSIGNEE");
    for (var issue : issues) {
      if (employee != null && issue.getAssignee() != employee) {
        continue;
      }
      Duration estimation = issue.getEstimatedTime();
      Duration expended = issue.getExpendedTime();
      timeLeft = timeLeft.plus(estimation.minus(expended));
      alreadyDone = alreadyDone.plus(expended);
      System.out.format("%2s", issue.getId());
      System.out.format("%20s", issue.getName());
      System.out.format("%10s", issue.getState());
      System.out.format("%13s", issue.getPriority());
      System.out.format("%13s", formatDuration(estimation));
      System.out.format("%13s", formatDuration(expended));
      double percentage = 0;
      if (estimation.getSeconds() != 0 && expended.getSeconds() != 0) {
        percentage = ((double)expended.getSeconds() / (double)estimation.getSeconds()) * 100;
      }
      System.out.format("%15s", percentage + "%");
      Employee assignee = issue.getAssignee();
      if (assignee == null) {
        System.out.format("%13s", "<not assigned>");
      } else {
        System.out.format("%30s", assignee.getFirstName() + " " + assignee.getLastName());
      }
      System.out.println();
    }

    System.out.println("Time Remaining: " + formatDuration(timeLeft));
    System.out.println("Time Spent: " + formatDuration(alreadyDone));
  }

  public static void printEmployees(EntityManagerFactory emf) {
    JpaUtil.executeInTransaction(emf, () -> {
      EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
      System.out.println("ID                          NAME       DATE OF BIRTH");
      List<Employee> employees = emplRepo.findAll();
      for (var employee : employees) {
        System.out.format("%2s", employee.getId());
        System.out.format("%30s", employee.getFirstName() + " " + employee.getLastName());
        System.out.format("%20s", employee.getDateOfBirth());
        System.out.println();
      }
    });
  }

  public static void printHelpPage(String command) {
    switch (command) {
      case "list":
        System.out.println("Usage:  list ENTITY [ID] [OPTION]");
        System.out.println("List items of an entity");
        System.out.println("ENTITY: 'project', 'employee',");
        System.out.println("list employee: list all employees");
        System.out.println("list project [ID] [PROJECT_OPTIONS]: list all or specific projects");
        System.out.println("  PROJECT_OPTIONS: -I (list issues in project), -E EMPLOYEE_ID (list issues in project by employee)");
        break;
      case "new":
        System.out.println("Usage:  new ENTITY PARAMS");
        System.out.println("Create a new entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee'");
        System.out.println("PARAMS:");
        System.out.println("-project: NAME (string)");
        System.out.println("-issue: NAME, PROJECT_ID, PRIORITY ('LOW' | 'NORMAL' | 'HIGH')");
        System.out.println("-employee: FIRST NAME, LAST NAME, DATE OF BIRTH (dd:mm:yyyy)");
        break;
      case "add":
        System.out.println("Usage: add SUBJECT ID TARGET ID");
        System.out.println("Add SUBJECT to TARGET");
        System.out.println("-add employee ID project ID: add employee to project");
        System.out.println("-add issue ID employee ID: assign issue to employee");
        break;
      case "delete":
        System.out.println("Usage:  delete ENTITY ID");
        System.out.println("Delete a specific entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee'");
        break;
      case "remove":
        System.out.println("Usage: remove SUBJECT ID TARGET ID");
        System.out.println("Remove SUBJECT from TARGET");
        System.out.println("-remove employee ID project ID: remove employee from project");
        break;
      case "update":
        System.out.println("Usage:  update issue ID PARAMS");
        System.out.println("Update an issue");
        System.out.println("PARAMS:");
        System.out.println("-T TIME (hh:mm:ss): add spent time");
        System.out.println("-E TIME (hh:mm:ss): update estimation");
        System.out.println("-S STATE ('NEW', 'OPEN', 'RESOLVED', 'CLOSED', 'REJECTED'): update state");
        break;
      case "exit":
        System.out.println("Usage:  exit");
        System.out.println("Quit the application");
        break;
      default:
        System.out.println("list        list entities");
        System.out.println("new         new entity");
        System.out.println("add         add entity to another");
        System.out.println("delete      delete entity");
        System.out.println("remove      remove entity from another");
        System.out.println("update      update entity");
        System.out.println("exit        quit application");
    }
  }
}
