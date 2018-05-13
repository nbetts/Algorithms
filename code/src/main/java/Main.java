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
    if (arraySize < 2) {
      System.out.println("Array size must be at least 2");
      return;
    }

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Testing performance of " + algorithm + ", running 10 tests " +
                      "with a randomised integer array of size " + arraySize);

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.println("Running analysis...");

    PerformanceResult performanceResult = analyser.runAnalysis(algorithm, 10, arraySize);

    System.out.println("Analysis complete");
    System.out.println("Average execution time: " + PerformanceAnalyser.formatNanoTime(
                                                    performanceResult.getAverageExecutionTime(), 5));
    System.out.println("Average iteration count: " + performanceResult.getAverageIterationCount());
    System.out.println("Average swap count: " + performanceResult.getAverageSwapCount());
  }

  public static void testPerformance(String[] algorithms, int arraySize) {
    if (arraySize < 2) {
      System.out.println("Array size must be at least 2");
      return;
    }

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Testing performance of the following algorithms, running 10 tests " +
                      "each with a randomised integer array of size " + arraySize);

    for (int i = 0; i < algorithms.length; i++) {
      System.out.format("%d: %s\n", i+1, algorithms[i]);
    }

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.println("Running analysis...");

    PerformanceResult[] performanceResults = analyser.runAnalysis(algorithms, 10, arraySize);

    System.out.println("Analysis complete, average performance results:");
    System.out.println("Algorithm\tExecution time\tIteration count\tSwap count");

    for (int i = 0; i < algorithms.length; i++) {
      System.out.format("%s\t%s\t\t%d\t\t%d\n", algorithms[i],
                        PerformanceAnalyser.formatNanoTime(performanceResults[i].getAverageExecutionTime(), 4),
                        performanceResults[i].getAverageIterationCount(),
                        performanceResults[i].getAverageSwapCount());
    }
  }

  public static void main(String[] args) {
    // testAlgorithm("selectionSort", 20, false);
    // testPerformance("shellSort", 20000);

    String[] algorithms = {"bubbleSort", "insertionSort", "mergeSort", "selectionSort", "shellSort"};
    testPerformance(algorithms, 20000);
  }
}