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

  public PerformanceResult runAnalysis(String algorithm, int testRepeatCount, int arraySize) {
    Method algorithmMethod = algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      return null;
    } else if (testRepeatCount < 1) {
      return null;
    } else if (arraySize < 2) {
      return null;
    }

    PerformanceResult performanceResult = new PerformanceResult();
    long startTime, endTime;

    for (int i = 1; i <= testRepeatCount; i++) {
      Integer[] array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);

      startTime = thread.getCurrentThreadCpuTime();
      algorithms.runAlgorithmMethod(algorithmMethod, array);
      endTime = thread.getCurrentThreadCpuTime() - startTime;

      performanceResult.addTestResult(endTime, algorithms.getIterationCount(), algorithms.getSwapCount());
    }

    return performanceResult;
  }

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
