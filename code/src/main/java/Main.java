
public class Main {
  public static void main(String[] args) throws Exception {
    // Algorithms.testAlgorithm("bubbleSort", false);

    PerformanceAnalyser analyser = new PerformanceAnalyser();

    System.out.println("Warming up performance analyser...");
    analyser.warmUp();
    System.out.println("Warmed up");

    System.out.format("Analysing performance of algorithm \"bubbleSort\"," +
                      " running 20 tests using an array size of 1000 ...\n");

    long averageExecutionTime = analyser.runAnalysis("bubbleSort", 10000, 20);

    System.out.format("Analysis complete, average execution time: %s\n",
                      PerformanceAnalyser.formatNanoTime(averageExecutionTime));
  }
}