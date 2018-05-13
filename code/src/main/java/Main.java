
public class Main {
  public static void main(String[] args) throws Exception {
    // Algorithms algorithms = new Algorithms();
    // algorithms.testAlgorithm("shellSort", false);
    // System.out.println("Iterations: " + algorithms.getIterationCount());
    // System.out.println("Swaps: " + algorithms.getSwapCount());

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();

    System.out.format("Analysing performance of bubbleSort, running 10 tests " +
                      "with a randomised integer array of size 10000 ...\n");

    PerformanceResult result = analyser.runAnalysis("bubbleSort", 10, 200);

    System.out.println("averageExecutionTime: " + PerformanceAnalyser.formatNanoTime(result.averageExecutionTime));
    System.out.println("averageIterationCount: " + result.averageIterationCount);
    System.out.println("averageSwapCount: " + result.averageSwapCount);
  }
}