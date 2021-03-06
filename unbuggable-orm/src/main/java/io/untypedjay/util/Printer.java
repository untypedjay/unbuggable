package io.untypedjay.util;

import io.untypedjay.domain.Employee;
import io.untypedjay.domain.Issue;
import io.untypedjay.domain.LogbookEntry;
import io.untypedjay.domain.Project;
import io.untypedjay.services.*;
import java.util.List;

public class Printer {
  private static ProjectService projectService = new ProjectServiceImpl();
  private static EmployeeService employeeService = new EmployeeServiceImpl();
  private static IssueService issueService = new IssueServiceImpl();
  private static LogbookEntryService logbookEntryService = new LogbookEntryServiceImpl();

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
    System.out.println("ID    NAME    DATE OF BIRTH   ADDRESS");
    List<Employee> employees = employeeService.getAll();
    for (var employee : employees) {
      System.out.print(employee.getId() + "   ");
      System.out.print(employee.getFirstName() + " " + employee.getLastName() + "   ");
      System.out.print(employee.getDateOfBirth() + "    ");
      System.out.print(employee.getAddress());
      System.out.println();
    }
  }

  public static void printIssues() {
    System.out.println("ID    STATE   PRIORITY    ESTIMATION    PROGRESS    ASSIGNEE");
    List<Issue> issues = issueService.getAll();
    for (var issue : issues) {
      System.out.print(issue.getId() + "    ");
      System.out.print(issue.getState() + "    ");
      System.out.print(issue.getPriority() + "   ");
      System.out.print(issue.getEstimatedCompletionTime() + "    ");
      System.out.print(issue.getProgress() + "   ");
      System.out.print(issue.getAssignee().getFirstName() + " " + issue.getAssignee().getFirstName());
      System.out.println();
    }
  }

  public static void printLogbookEntries() {
    System.out.println("ID    ACTIVITY    START   END   EMPLOYEE");
   List<LogbookEntry> logbookEntries = logbookEntryService.getAll();
   for (var logbookEntry : logbookEntries) {
     System.out.print(logbookEntry.getId() + "    ");
     System.out.print(logbookEntry.getActivity() + "    ");
     System.out.print(logbookEntry.getStartTime() + "   ");
     System.out.print(logbookEntry.getEndTime() + "   ");
     System.out.print(logbookEntry.getEmployee().getFirstName() + " " + logbookEntry.getEmployee().getLastName());
     System.out.println();
   }
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
        System.out.println("-issue: PRIORITY ('LOW' | 'NORMAL' | 'HIGH'), ESTIMATED COMPLETION TIME (hh:mm:ss)");
        System.out.println("-employee: FIRST NAME (string), LAST NAME (string), DATE OF BIRTH (dd:mm:yyyy)");
        System.out.println("-entry: ACTIVITY (string), START TIME (hh:mm:ss), END TIME (hh:mm:ss)");
        break;
      case "rm":
        System.out.println("Usage:  rm ENTITY PARAMS");
        System.out.println("Remove a specific entity");
        System.out.println("ENTITY: 'project', 'issue', 'employee', 'entry'");
        System.out.println("PARAMS:");
        System.out.println("-project: ID (integer)");
        System.out.println("-issue: ID (integer)");
        System.out.println("-employee: ID (integer)");
        System.out.println("-entry: ID (integer)");
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
}
