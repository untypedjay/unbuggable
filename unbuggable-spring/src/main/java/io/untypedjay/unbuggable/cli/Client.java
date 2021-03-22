package io.untypedjay.unbuggable.cli;

import io.untypedjay.unbuggable.util.Printer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
  public static void main(String[] args) {
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

        case "add":
          if (commands.length > 2 && commands.length < 6) {
            add(commands);
          } else {
            Printer.printInvalidCommandError(commands);
          }
          break;

        case "remove":
          if (commands.length == 3) {
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

  private static void list(String[] commands) {
    switch (commands[1]) {
      case "project":
        if (commands.length == 2) {
          Printer.printProjects();
        } else if (commands.length == 3) {
          Printer.printProjectIssues(Integer.parseInt(commands[2]));
        } else if (commands.length == 5) {
          Printer.printProjectIssuesByEmployee(Integer.parseInt(commands[2]), Integer.parseInt(commands[4]));
        } else {
          Printer.printInvalidCommandError(commands);
        }

        break;
      case "employee":
        Printer.printEmployees();
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void add(String[] commands) {
    switch (commands[1]) {
      case "project":
        //TODO projectService.add(commands[2]);
        break;
      case "issue":
        //TODO issueService.add(commands[2], commands[3]); // priority, estimated completion time
        break;
      case "employee":
        // TODO employeeService.add(commands[2], commands[3], commands[4]); // first, last, dob
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void remove(String[] commands) {
    switch (commands[1]) {
      case "project":
        //TODO projectService.remove(Long.parseLong(commands[2]));
        break;
      case "issue":
        // TODO issueService.remove(Long.parseLong(commands[2]));
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void update(String[] commands) {
    switch (commands[1]) {
      case "issue":
        if (commands[3] == "-T") {
          // TODO add spent time
        } else if (commands[3] == "-A") {
          // TODO assign issue to employee
        } else if (commands[3] == "-E") {
          // TODO update estimate
        } else if (commands[3] == "-S") {
          // TODO update status
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