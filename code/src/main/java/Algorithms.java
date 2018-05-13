import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Algorithms {
  private static ArrayList<Method> algorithmMethods;
  private static int iterationCount;
  private static int swapCount;
  private static int[] shellSortGaps;

  private Algorithms() {}

  static {
    resetCounters();

    algorithmMethods = new ArrayList<>();

    for (Method method : Algorithms.class.getMethods()) {
      if (method.getName().endsWith("Sort")) {
        algorithmMethods.add(method);
      }
    }

    shellSortGaps = new int[31];

    for (int i = 0; i < shellSortGaps.length; i++) {
      shellSortGaps[i] = (int) Math.pow(2, shellSortGaps.length - i) - 1;
    }

    // Need to add 1 to the first value due to reaching max value of integer.
    shellSortGaps[0] += 1;
  }

  private static <T> void swap(T[] array, int firstIndex, int secondIndex) {
    T element = array[firstIndex];
    array[firstIndex] = array[secondIndex];
    array[secondIndex] = element;
    swapCount++;
  }

  private static void resetCounters() {
    iterationCount = 0;
    swapCount = 0;
  }

  public static int getIterationCount() {
    return iterationCount;
  }

  public static int getSwapCount() {
    return swapCount;
  }

  public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    for (int i = 0; i < array.length - 1; i++) {
      for (int j = 0; j < array.length - 1; j++) {
        if (array[j].compareTo(array[j+1]) > 0) {
          swap(array, j, j+1);
        }
      }
    }
  }

  public static <T extends Comparable<? super T>> void insertionSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    for (int i = 1; i < array.length; i++) {
      for (int j = i; j > 0; j--) {
        if (array[j].compareTo(array[j-1]) < 0) {
          swap(array, j, j-1);
        }
      }
    }
  }

  public static <T extends Comparable<? super T>> void selectionSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    for (int i = 0; i < array.length - 1; i++) {
      int lowest = i;

      for (int j = i + 1; j < array.length; j++) {
        if (array[j].compareTo(array[lowest]) < 0) {
          lowest = j;
        }
      }

      if (lowest != i) {
        swap(array, i, lowest);
      }
    }
  }

  public static <T extends Comparable<? super T>> void shellSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    for (int i = 0; i < shellSortGaps.length; i++) {
      int gap = shellSortGaps[i];

      if (gap < array.length) {
        for (int k, j = gap; j < array.length; j++) {
          T element = array[j];

          for (k = j; k >= gap && array[k-gap].compareTo(element) > 0; k -= gap) {
            array[k] = array[k-gap];
          }

          array[k] = element;
        }
      }
    }
  }

  public static void testAlgorithm(String algorithm, boolean isReverse) {
    Method algorithmMethod = getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      System.out.format("Unsupported algorithm: \"%s\"\n", algorithm);
      return;
    }

    Integer[] array;
    int size = 20;

    if (isReverse) {
      array = ArrayUtilities.generateReverseIntegerArray(size);
    } else {
      array = ArrayUtilities.generateRandomIntegerArray(size, 0, size);
    }

    System.out.format("Testing algorithm \"%s\" with %s array of size %d\n",
                      algorithm, isReverse ? "reversed" : "randomized", size);

    System.out.println("Before: " + ArrayUtilities.toString(array));

    runAlgorithmMethod(algorithmMethod, array);

    System.out.println("After:  " + ArrayUtilities.toString(array));
  }

  public static Method getAlgorithmMethod(String algorithm) {
    for (Method method : algorithmMethods) {
      if (method.getName().equals(algorithm)) {
        return method;
      }
    }

    return null;
  }

  public static <T extends Comparable<? super T>> void runAlgorithmMethod(Method method, T[] array) {
    try {
      method.invoke(null, new Object[]{array});
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
