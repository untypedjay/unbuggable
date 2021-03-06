package io.untypedjay.cli;

import io.untypedjay.services.*;
import io.untypedjay.util.Printer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
  private static ProjectService projectService = new ProjectServiceImpl();
  private static IssueService issueService = new IssueServiceImpl();
  private static EmployeeService employeeService = new EmployeeServiceImpl();
  private static LogbookEntryService logbookEntryService = new LogbookEntryServiceImpl();

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    String[] commands = promptFor(in, "").split(" ");

    while (!commands[0].equals("exit")) {
      switch (commands[0]) {
        case "ls":
          if (commands.length == 2) {
            list(commands);
          } else {
            Printer.printInvalidCommandError(commands);
          }
          break;

        case "mk":
          if (commands.length < 3) {
            Printer.printInvalidCommandError(commands);
          } else {
            make(commands);
          }
          break;

        case "rm":
          if (commands.length < 3) {
            Printer.printInvalidCommandError(commands);
          } else {
            remove(commands);
          }
          break;

        case "update":
          if (commands.length < 3) {
            Printer.printInvalidCommandError(commands);
          } else {
            update(commands);
          }
          break;

        case "stats":
          if (commands.length == 2) {

          } else if (commands.length == 4) {

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

  private static void list(String[] commands) {
    switch (commands[1]) {
      case "projects":
        Printer.printProjects();
        break;
      case "issues":
        Printer.printIssues();
        break;
      case "employees":
        Printer.printEmployees();
        break;
      case "entries":
        Printer.printLogbookEntries();
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void make(String[] commands) {
    switch (commands[1]) {
      case "project":
        projectService.add(commands[2]);
        break;
      case "issue":
        issueService.add(commands[2], commands[3]); // priority, estimated completion time
        break;
      case "employee":
        employeeService.add(commands[2], commands[3], commands[4]); // first, last, dob
        break;
      case "entry":
        logbookEntryService.add(commands[2], commands[3], commands[4]); // activity, start, end
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void remove(String[] commands) {
    switch (commands[1]) {
      case "project":
        projectService.remove(Long.parseLong(commands[2]));
        break;
      case "issue":
        issueService.remove(Long.parseLong(commands[2]));
        break;
      case "employee":
        employeeService.remove(Long.parseLong(commands[2]));
        break;
      case "entry":
        logbookEntryService.remove(Long.parseLong(commands[2]));
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void update(String[] commands) {
    switch (commands[1]) {
      case "project":
        projectService.update(Long.parseLong(commands[2]), commands[3]);
        break;
      case "issue":
        // TODO
        break;
      case "employee":
        // TODO
        break;
      case "entry":
        // TODO
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