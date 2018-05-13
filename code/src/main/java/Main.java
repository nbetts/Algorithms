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

    System.out.format("Testing algorithm \"%s\" with a %s array of size %d\n",
                      algorithm, isReverse ? "reversed" : "randomized", arraySize);

    System.out.println("Before: " + ArrayUtilities.toString(array));

    algorithms.runAlgorithmMethod(algorithmMethod, array);

    System.out.println("After:  " + ArrayUtilities.toString(array));
    System.out.println("Iterations: " + algorithms.getIterationCount());
    System.out.println("Swaps: " + algorithms.getSwapCount());
  }

  public static void testPerformance(String algorithm, int arraySize) {
    Algorithms algorithms = new Algorithms();
    Method algorithmMethod = algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      System.out.format("Unsupported algorithm: \"%s\"\n", algorithm);
      return;
    } else if (arraySize < 2) {
      System.out.println("Array size must be at least 2");
    }

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Testing performance of " + algorithm + ", running 10 tests " +
                      "with a randomised integer array of size " + arraySize);

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.println("Running analysis...");

    PerformanceResult result = analyser.runAnalysis(algorithm, 10, arraySize);

    System.out.println("Analysis complete");
    System.out.println("Average execution time: " + PerformanceAnalyser.formatNanoTime(result.averageExecutionTime, 5));
    System.out.println("Average iteration count: " + result.averageIterationCount);
    System.out.println("Average swap count: " + result.averageSwapCount);
  }

  public static void main(String[] args) {
    // testAlgorithm("shellSort", 20, false);
    testPerformance("shellSort", 20000);
  }
}