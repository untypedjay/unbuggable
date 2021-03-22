package io.untypedjay.unbuggable.util;

import java.time.Duration;

public class TimeUtil {
  public static Duration parseDuration(String input) {
    String[] timeStringArray = input.split(":");
    int[] timeNumberArray = new int[timeStringArray.length];

    try {
      for (int i = 0; i < timeStringArray.length; i++) {
        timeNumberArray[i] = Integer.parseInt(timeStringArray[i]);
      }
    } catch (NumberFormatException e) {
      throw e;
    }

    return getDuration(timeNumberArray);
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

  private static Duration getDuration(int[] input) {
    if (input.length != 3) {
      return null;
    }
    return Duration.parse("PT" + input[0] + "H" + input[1] + "M" + input[2] + "S");
  }
}
