
public class Main {
  public static void main(String[] args) throws Exception {
    // Algorithms.testAlgorithm("bubbleSort", false);

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.format("Analysing performance of all algorithms, running 10 tests " +
                      "each with a randomised integer array of size 10000 ...\n");

    String[] algorithms = {"bubbleSort", "insertionSort", "selectionSort", "shellSort"};
    long[] averageExecutionTimes = analyser.runAnalysis(algorithms, 10, 10000);

    System.out.println("Analysis complete, average execution times:");

    for (int i = 0; i < algorithms.length; i++) {
      System.out.format("%s\t%s\n", algorithms[i], PerformanceAnalyser.formatNanoTime(averageExecutionTimes[i]));
    }
  }
}