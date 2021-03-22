package io.untypedjay.unbuggable.util;

import io.untypedjay.unbuggable.dao.EmployeeRepository;
import io.untypedjay.unbuggable.dao.ProjectRepository;
import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.domain.Project;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Printer {
  public static void printInvalidCommandError(String[] commandArray) {
    System.out.println("'" + String.join(" ", commandArray) + "' is not a valid command");
    System.out.println("See 'help'");
  }

  public static void printProjects(EntityManagerFactory emf) {
    JpaUtil.executeInTransaction(emf, () -> {
      ProjectRepository projectRepo = JpaUtil.getJpaRepository(emf, ProjectRepository.class);
      System.out.println("ID    NAME");
      List<Project> projects = projectRepo.findAll();
      for (var project : projects) {
        System.out.println(project.getId() + "    " + project.getName());
      }
    });
  }

  public static void printProjectIssues(int projectId) {
    // TODO list remaining and completed time for project
  }

  public static void printProjectIssuesByEmployee(int projectId, int employeeId) {
    // TODO filter by status
  }

  public static void printEmployees(EntityManagerFactory emf) {
    JpaUtil.executeInTransaction(emf, () -> {
      EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);
      System.out.println("ID    NAME    DATE OF BIRTH");
      List<Employee> employees = emplRepo.findAll();
      for (var employee : employees) {
        System.out.print(employee.getId() + "   ");
        System.out.print(employee.getFirstName() + " " + employee.getLastName() + "   ");
        System.out.print(employee.getDateOfBirth() + "    ");
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
        System.out.println("  PROJECT_OPTIONS: -E EMPLOYEE_ID (list issues in project by employee");
        break;
      case "new":
        System.out.println("Usage:  new ENTITY PARAMS");
        System.out.println("Create a new entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee'");
        System.out.println("PARAMS:");
        System.out.println("-project: NAME (string)");
        System.out.println("-issue: NAME (string), PRIORITY ('LOW' | 'NORMAL' | 'HIGH'), ESTIMATED COMPLETION TIME (hh:mm:ss)");
        System.out.println("-employee: FIRST NAME (string), LAST NAME (string), DATE OF BIRTH (dd:mm:yyyy) | EMPLOYEE_ID (integer) -P PROJECT_ID (integer)");
        break;
      case "add":
        System.out.println("Usage: add SUBJECT ID TARGET ID");
        System.out.println("Add SUBJECT to TARGET");
        System.out.println("-add employee ID project ID: add employee to project");
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
        System.out.println("-A EMPLOYEE_ID (integer): assign employee");
        System.out.println("-E TIME (hh:mm:ss): update estimation");
        System.out.println("-S STATUS ('NEW', 'OPEN', 'RESOLVED', 'CLOSED', 'REJECTED'): update status");
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
