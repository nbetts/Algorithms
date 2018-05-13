import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;

public class PerformanceAnalyser {
  private Algorithms algorithms;
  private ThreadMXBean thread;

  public PerformanceAnalyser() {
    algorithms = new Algorithms();
    thread = ManagementFactory.getThreadMXBean();
  }

  public PerformanceResult runAnalysis(String algorithm, int testRepeatCount,  int arraySize) {
    Method algorithmMethod = algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      return null;
    } else if (testRepeatCount < 1) {
      return null;
    } else if (arraySize < 2) {
      return null;
    }

    long startTime, endTime;
    long averageExecutionTime = 0;
    long averageIterationCount = 0;
    long averageSwapCount = 0;

    for (int i = 1; i <= testRepeatCount; i++) {
      Integer[] array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);

      startTime = thread.getCurrentThreadCpuTime();
      algorithms.runAlgorithmMethod(algorithmMethod, array);
      endTime = thread.getCurrentThreadCpuTime() - startTime;

      averageExecutionTime += (endTime - averageExecutionTime) / (long) i;
      averageIterationCount += (algorithms.getIterationCount() - averageIterationCount) / (long) i;
      averageSwapCount += (algorithms.getSwapCount() - averageSwapCount) / (long) i;
    }

    return new PerformanceResult(averageExecutionTime, averageIterationCount, averageSwapCount);
  }

  // public long[] runAnalysis(String[] algorithms, int testRepeatCount,  int arraySize) {
  //   long[] averageExecutionTimes = new long[algorithms.length];

  //   for (int i = 0; i < algorithms.length; i++) {
  //     averageExecutionTimes[i] = runAnalysis(algorithms[i], testRepeatCount, arraySize);
  //   }

  //   return averageExecutionTimes;
  // }

  public void warmUp() {
    runAnalysis("bubbleSort", 2, 20000);
  }

  public static String formatNanoTime(long nanoTime, int significantFigures) {
    if (significantFigures < 1) {
      significantFigures = 1;
    }

    return String.format("%." + significantFigures + "fs", (double) nanoTime / 1000000000.0);
  }
}
