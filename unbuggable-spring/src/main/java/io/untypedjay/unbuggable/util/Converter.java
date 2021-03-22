package io.untypedjay.unbuggable.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {
  public static LocalDate toLocalDate(String string) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return LocalDate.parse(string, formatter);
  }
}
