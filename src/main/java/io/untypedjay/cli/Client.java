package io.untypedjay.cli;

import io.untypedjay.util.Printer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;

public class Client {
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
        // TODO
        break;
      case "issues":
        // TODO
        break;
      case "addresses":
        // TODO
        break;
      case "employees":
        // TODO
        break;
      case "entries":
        // TODO
        break;
      default:
        Printer.printInvalidCommandError(commands);
        break;
    }
  }

  private static void make(String[] commands) {
    switch (commands[1]) {
      case "project":
        // TODO
        break;
      case "issue":
        // TODO
        break;
      case "address":
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

  private static void remove(String[] commands) {
    switch (commands[1]) {
      case "project":
        // TODO
        break;
      case "issue":
        // TODO
        break;
      case "address":
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

  private static void update(String[] commands) {
    switch (commands[1]) {
      case "project":
        // TODO
        break;
      case "issue":
        // TODO
        break;
      case "address":
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

  private static Duration getDuration(int[] input) {
    if (input.length != 3) {
      return null;
    }
    return Duration.parse("PT" + input[0] + "H" + input[1] + "M" + input[2] + "S");
  }
}