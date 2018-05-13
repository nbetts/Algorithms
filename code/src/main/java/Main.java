import java.lang.reflect.Method;

public class Main {
  public static void testAlgorithm(String algorithm, int arraySize, boolean isReverse) {
    Algorithms algorithms = new Algorithms();

    Method algorithmMethod = algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      System.out.format("Unsupported algorithm: \"%s\"\n", algorithm);
      return;
    } else if (arraySize < 2) {
      System.out.println("Array size must be at least 2");
    }

    Integer[] array;

    if (isReverse) {
      array = ArrayUtilities.generateReverseIntegerArray(arraySize);
    } else {
      array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);
    }

    System.out.format("Testing algorithm \"%s\" with %s array of size %d\n",
                      algorithm, isReverse ? "reversed" : "randomized", arraySize);

    System.out.println("Before: " + ArrayUtilities.toString(array));

    algorithms.runAlgorithmMethod(algorithmMethod, array);

    System.out.println("After:  " + ArrayUtilities.toString(array));
    System.out.println("Iterations: " + algorithms.getIterationCount());
    System.out.println("Swaps: " + algorithms.getSwapCount());
  }

  public static void main(String[] args) throws Exception {
    // testAlgorithm("shellSort", 1000, false);

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.format("Analysing performance of bubbleSort, running 10 tests " +
                      "with a randomised integer array of size 10000 ...\n");

    PerformanceResult result = analyser.runAnalysis("bubbleSort", 10, 200);

    System.out.println("averageExecutionTime: " + PerformanceAnalyser.formatNanoTime(result.averageExecutionTime, 5));
    System.out.println("averageIterationCount: " + result.averageIterationCount);
    System.out.println("averageSwapCount: " + result.averageSwapCount);
  }
}