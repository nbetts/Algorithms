import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Algorithms {
  private final ArrayList<Method> algorithmMethods;
  private final int[] shellSortGaps;
  private long iterationCount;
  private long swapCount;

  public Algorithms() {
    resetCounters();
    algorithmMethods = new ArrayList<>();
    shellSortGaps = new int[31];

    for (Method method : Algorithms.class.getMethods()) {
      if (method.getName().endsWith("Sort")) {
        algorithmMethods.add(method);
      }
    }

    for (int i = 0; i < shellSortGaps.length; i++) {
      shellSortGaps[i] = (int) Math.pow(2, shellSortGaps.length - i) - 1;
    }

    // Need to add 1 to the first value due to reaching max value of integer.
    shellSortGaps[0] += 1;
  }

  private <T> void swap(T[] array, int firstIndex, int secondIndex) {
    T element = array[firstIndex];
    array[firstIndex] = array[secondIndex];
    array[secondIndex] = element;
    swapCount++;
  }

  private void resetCounters() {
    iterationCount = 0;
    swapCount = 0;
  }

  public long getIterationCount() {
    return iterationCount;
  }

  public long getSwapCount() {
    return swapCount;
  }

  public <T extends Comparable<? super T>> void bubbleSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    resetCounters();
    boolean swapped = true;

    while (swapped) {
      swapped = false;

      for (int j = 0; j < array.length - 1; j++) {
        if (array[j].compareTo(array[j+1]) > 0) {
          swap(array, j, j+1);
          swapped = true;
        }

        iterationCount++;
      }
    }
  }

  public <T extends Comparable<? super T>> void insertionSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    resetCounters();

    for (int i = 1; i < array.length; i++) {
      for (int j = i; j > 0; j--) {
        if (array[j].compareTo(array[j-1]) < 0) {
          swap(array, j, j-1);
        }

        iterationCount++;
      }
    }
  }

  public <T extends Comparable<? super T>> void mergeSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    resetCounters();

    @SuppressWarnings("unchecked")
    T[] sortedArray = (T[]) new Comparable[array.length];

    mergeSort(array, sortedArray, 0, array.length-1);
  }

  private <T extends Comparable<? super T>> void mergeSort(T[] array, T[] sortedArray,
                                                                  int lowIndex, int highIndex) {
    if (lowIndex >= highIndex) {
      return;
    }

    int middleIndex = (lowIndex + highIndex) / 2;

    mergeSort(array, sortedArray, lowIndex, middleIndex);
    mergeSort(array, sortedArray, middleIndex + 1, highIndex);
    merge(array, sortedArray, lowIndex, middleIndex, highIndex);
  }

  private <T extends Comparable<? super T>> void merge(T[] array, T[] sortedArray,
                                                              int lowIndex, int middleIndex, int highIndex) {
    int leftIndex = lowIndex;
    int rightIndex = middleIndex + 1;

    for (int i = lowIndex; i <= highIndex; i++) {
      if (leftIndex <= middleIndex && rightIndex <= highIndex) {
        if (array[leftIndex].compareTo(array[rightIndex]) > 0) {
          sortedArray[i] = array[rightIndex++];
        } else {
          sortedArray[i] = array[leftIndex++];
        }
      } else if (leftIndex <= middleIndex && rightIndex > highIndex) {
        sortedArray[i] = array[leftIndex++];
      } else if (leftIndex > middleIndex && rightIndex <= highIndex) {
        sortedArray[i] = array[rightIndex++];
      }

      iterationCount++;
    }

    for (int i = lowIndex; i <= highIndex; i++) {
      array[i] = sortedArray[i];
    }
  }

  public <T extends Comparable<? super T>> void selectionSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    resetCounters();

    for (int i = 0; i < array.length - 1; i++) {
      int lowest = i;

      for (int j = i + 1; j < array.length; j++) {
        if (array[j].compareTo(array[lowest]) < 0) {
          lowest = j;
        }

        iterationCount++;
      }

      if (lowest != i) {
        swap(array, i, lowest);
      }
    }
  }

  public <T extends Comparable<? super T>> void shellSort(T[] array) {
    if (array.length <= 1) {
      return;
    }

    resetCounters();

    for (int i = 0; i < shellSortGaps.length; i++) {
      int gap = shellSortGaps[i];

      if (gap < array.length) {
        for (int k, j = gap; j < array.length; j++) {
          T element = array[j];

          for (k = j; k >= gap && array[k-gap].compareTo(element) > 0; k -= gap) {
            array[k] = array[k-gap];
            iterationCount++;
          }

          array[k] = element;
        }
      }
    }
  }

  public void testAlgorithm(String algorithm, boolean isReverse) {
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

  public Method getAlgorithmMethod(String algorithm) {
    for (Method method : algorithmMethods) {
      if (method.getName().equals(algorithm)) {
        return method;
      }
    }

    return null;
  }

  public <T extends Comparable<? super T>> void runAlgorithmMethod(Method method, T[] array) {
    try {
      method.invoke(this, new Object[]{array});
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
