
public class Algorithms {
  private Algorithms() {}

  public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = 0; j < array.length - 1; j++) {
        if (array[j].compareTo(array[j+1]) > 0) {
          T element = array[j];
          array[j] = array[j+1];
          array[j+1] = element;
        }
      }
    }
  }

  public static void main(String[] args) {
    Integer[] array = ArrayUtilities.generateRandomIntegerArray(20, 1, 30);

    ArrayUtilities.print(array);
    Algorithms.bubbleSort(array);
    ArrayUtilities.print(array);
  }
}
