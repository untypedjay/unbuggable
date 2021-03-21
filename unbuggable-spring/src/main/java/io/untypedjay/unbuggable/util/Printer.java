package io.untypedjay.unbuggable.util;

import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.domain.Issue;
import io.untypedjay.unbuggable.domain.LogbookEntry;
import io.untypedjay.unbuggable.domain.Project;

import java.util.List;

public class Printer {

  public static void printInvalidCommandError(String[] commandArray) {
    System.out.println("'" + String.join(" ", commandArray) + "' is not a valid command");
    System.out.println("See 'help'");
  }

  public static void printProjects() {
    System.out.println("ID    NAME");
    // TODO
//    List<Project> projects = projectService.getAll();
//    for (var project : projects) {
//      System.out.println(project.getId() + "    " + project.getName());
//    }
  }

  public static void printEmployees() {
    System.out.println("ID    NAME    DATE OF BIRTH   ADDRESS");
    // TODO
//    List<Employee> employees = employeeService.getAll();
//    for (var employee : employees) {
//      System.out.print(employee.getId() + "   ");
//      System.out.print(employee.getFirstName() + " " + employee.getLastName() + "   ");
//      System.out.print(employee.getDateOfBirth() + "    ");
//      System.out.print(employee.getAddress());
//      System.out.println();
//    }
  }

  public static void printHelpPage(String command) {
    switch (command) {
      case "list":
        System.out.println("Usage:  list ENTITY [ID] [OPTION]");
        System.out.println("List items of an entity");
        System.out.println("ENTITY: 'project', 'employee',");
        System.out.println("list employee: list employees");
        System.out.println("list project [ID] [PROJECT_OPTIONS]: list all or specific project"); // TODO list remaining and completed time for project by emplyoee
        System.out.println("  PROJECT_OPTIONS: -E (employees) -I (issues"); // TODO filter by status, grouped by employee
        break;
      case "add":
        System.out.println("Usage:  add ENTITY PARAMS");
        System.out.println("Create a new entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee'");
        System.out.println("PARAMS:"); // TODO
        System.out.println("-project: NAME (string)");
        System.out.println("-issue: PRIORITY ('LOW' | 'NORMAL' | 'HIGH'), ESTIMATED COMPLETION TIME (hh:mm:ss)");
        System.out.println("-employee: FIRST NAME (string), LAST NAME (string), DATE OF BIRTH (dd:mm:yyyy)");
        break;
      case "remove":
        System.out.println("Usage:  remove ENTITY ID [OPTIONS]");
        System.out.println("Remove a specific entity");
        System.out.println("ENTITY: 'project', 'issue'");
        System.out.println("OPTIONS:");
        System.out.println("remove project PROJECT_ID -E EMPLOYEE_ID");
        break;
      case "update":
        System.out.println("Usage:  update ENTITY PARAMS");
        System.out.println("Update a specific entity");
        System.out.println("ENTITY: 'issue'");
        System.out.println("PARAMS:");
        System.out.println("-issue: "); // TODO issue status, estimated time
        break;
      case "exit":
        System.out.println("Usage:  exit");
        System.out.println("Quit the application");
        break;
      default:
        System.out.println("list        list entities");
        System.out.println("add         new entity");
        System.out.println("remove      remove entity");
        System.out.println("update      update entity");
        System.out.println("exit        quit application");
    }
  }
}
