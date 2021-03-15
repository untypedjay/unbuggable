package io.untypedjay.util;

import io.untypedjay.domain.Project;
import io.untypedjay.services.ProjectService;
import io.untypedjay.services.ProjectServiceImpl;

import java.time.Duration;
import java.util.List;

public class Printer {
  private static ProjectService projectService = new ProjectServiceImpl();

  public static void printInvalidCommandError(String[] commandArray) {
    System.out.println("'" + String.join(" ", commandArray) + "' is not a valid command");
    System.out.println("See 'help'");
  }

  public static void printProjects() {
    System.out.println("ID    NAME");
    List<Project> projects = projectService.getAll();
    for (var project : projects) {
      System.out.println(project.getId() + "    " + project.getName());
    }
  }

  public static void printEmployees() {
    System.out.println("ID    NAME    DATE OF BIRTH   ADDRESS   SALARY   RENTER    RATE    START   END");
    // TODO
  }

  public static void printIssues() {
    System.out.println("ID    STATE   PRIORITY    ESTIMATION    PROGRESS");
    // TODO
  }

  public static void printLogbookEntries() {
    System.out.println("ID    ACTIVITY    START   END");
    // TODO
  }

  public static void printProjectStats(String[] commandArray) {
    if (commandArray[3] == "--open") {
      // TODO
    } else if (commandArray[3] == "--closed") {
      // TODO
    } else {
      printInvalidCommandError(commandArray);
    }
  }

  public static void printEmployeeStats(Long employeeId) {
    // TODO
  }

  public static void printHelpPage(String command) {
    // TODO
    switch (command) {
      case "ls":
        System.out.println("Usage:  ls ENTITY");
        System.out.println("List all items of an entity");
        System.out.println("ENTITY: 'projects', 'issues', 'employees', 'entries'");
        break;
      case "mk":
        System.out.println("Usage:  mk ENTITY PARAMS");
        System.out.println("Create a new entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee', 'entry'");
        System.out.println("PARAMS:");
        System.out.println("-project: NAME (string)");
        System.out.println("-issue: "); // TODO
        System.out.println("-employee: "); // TODO
        System.out.println("-entry: "); // TODO
        break;
      case "rm":
        System.out.println("Usage:  rm ENTITY PARAMS");
        System.out.println("Remove a specific entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee', 'entry'");
        System.out.println("PARAMS:");
        System.out.println("-project: ID (integer)");
        System.out.println("-issue: "); // TODO
        System.out.println("-employee: "); // TODO
        System.out.println("-entry: "); // TODO
        break;
      case "update":
        System.out.println("Usage:  update ENTITY PARAMS");
        System.out.println("Update a specific entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee', 'entry'");
        System.out.println("PARAMS:");
        System.out.println("-project: ID (integer) NAME (string)");
        System.out.println("-issue: "); // TODO
        System.out.println("-employee: "); // TODO
        System.out.println("-entry: "); // TODO
        break;
      case "stats":
        System.out.println("Usage:  stats EMPLOYEEID [PROJECTID ('--open' | '--closed')]");
        System.out.println("See statistics for an employee");
        System.out.println("EMPLOYEEID (integer): employee identifier");
        System.out.println("PROJECTID (integer): project identifier");
        break;
      case "exit":
        System.out.println("Usage:  exit");
        System.out.println("Quit the application");
        break;
      default:
        System.out.println("ls          list entities");
        System.out.println("mk          new entity");
        System.out.println("rm          remove entity");
        System.out.println("update      update entity");
        System.out.println("stats       see statistics");
        System.out.println("exit        quit application");
    }
  }

  public static String formatDuration(Duration duration) {
    long seconds = duration.getSeconds();
    long absSeconds = Math.abs(seconds);
    String positive = String.format(
      "%d:%02d:%02d",
      absSeconds / 3600,
      (absSeconds % 3600) / 60,
      absSeconds % 60);
    return seconds < 0 ? "-" + positive : positive;
  }
}
