package es.parravidales.tasks.utils;

import java.util.Random;

public class RandomUtils {

  private static final Random RANDOM = new Random();

  public static long nextLong() {
    return RANDOM.nextLong();
  }
}
