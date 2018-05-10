
public class Algorithms {
  private static int[] shellSortGaps = new int[31];

  private Algorithms() {}

  static {
    for (int i = 0; i < shellSortGaps.length; i++) {
      shellSortGaps[i] = (int) Math.pow(2, shellSortGaps.length - i) - 1;
    }
  }

  private static <T> void swap(T[]array, int first, int second) {
    T element = array[first];
    array[first] = array[second];
    array[second] = element;
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
    switch (algorithm) {
      case "bubbleSort": break;
      case "insertionSort": break;
      case "selectionSort": break;
      case "shellSort": break;
      default:
        System.out.println("Unsupported algorithm: " + algorithm);
        return;
    }

    Integer[] array;
    int size = 20;

    if (isReverse) {
      array = ArrayUtilities.generateReverseIntegerArray(size);
    } else {
      array = ArrayUtilities.generateRandomIntegerArray(size, 0, size);
    }

    System.out.println("Testing algorithm \"" + algorithm + "\" with " +
                       (isReverse ? "reversed" : "randomized") + " array of size " + size);

    System.out.print("Before: ");
    ArrayUtilities.print(array);

    switch (algorithm) {
      case "bubbleSort": Algorithms.bubbleSort(array);
      case "insertionSort": Algorithms.insertionSort(array);
      case "selectionSort": Algorithms.selectionSort(array);
      case "shellSort": Algorithms.shellSort(array);
    }

    System.out.print("After:  ");
    ArrayUtilities.print(array);
  }

  public static void main(String[] args) {
    Algorithms.testAlgorithm("bubbleSort", false);
  }
}
