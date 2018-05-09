import java.util.Random;

public class ArrayUtilities {
  private ArrayUtilities() {}

  public static Integer[] generateRandomIntegerArray(int size, int minValue, int maxValue) {
    Integer[] array = new Integer[size];
    int upperLimit = maxValue + 1 - minValue;
    Random r = new Random();

    for (int i = 0; i < size; i++) {
      array[i] = r.nextInt(upperLimit) + minValue;
    }

    return array;
  }

  public static Integer[] generateReverseIntegerArray(int size) {
    Integer[] array = new Integer[size];

    for (int i = 0; i < size; i++) {
      array[i] = size - i;
    }

    return array;
  }

  public static <T> void reverse(T[] array) {
    int upperBound = array.length - 1;

    for (int i = 0; i < array.length / 2; i++) {
      T element = array[i];
      array[i] = array[upperBound-i];
      array[upperBound-i] = element;
    }
  }

  public static <T> void print(T[] array) {
    String content = "";

    for (int i = 0; i < array.length; i++) {
      content += array[i].toString();

      if (i < array.length - 1) {
        content += ", ";
      }
    }

    System.out.println("[" + content + "]");
  }
}
