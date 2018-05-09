
public class Algorithms {
  private Algorithms() {}

  private static <T> void swap(T[]array, int first, int second) {
    T element = array[first];
    array[first] = array[second];
    array[second] = element;
  }

  public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = 0; j < array.length - 1; j++) {
        if (array[j].compareTo(array[j+1]) > 0) {
          swap(array, j, j+1);
        }
      }
    }
  }

  public static <T extends Comparable<? super T>> void insertionSort(T[] array) {
    for (int i = 1; i < array.length; i++) {
      for (int j = i; j > 0; j--) {
        if (array[j].compareTo(array[j-1]) < 0) {
          swap(array, j, j-1);
        }
      }
    }
  }

  public static <T extends Comparable<? super T>> void selectionSort(T[] array) {
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

  public static void main(String[] args) {
    // Integer[] array = ArrayUtilities.generateRandomIntegerArray(20, 1, 30);
    Integer[] array = ArrayUtilities.generateReverseIntegerArray(20);

    ArrayUtilities.print(array);
    // Algorithms.bubbleSort(array);
    // Algorithms.insertionSort(array);
    Algorithms.selectionSort(array);
    ArrayUtilities.print(array);
  }
}
