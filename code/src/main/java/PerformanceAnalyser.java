import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;

public class PerformanceAnalyser {
  private ThreadMXBean thread;

  public PerformanceAnalyser() {
    thread = ManagementFactory.getThreadMXBean();
  }

  public long runAnalysis(String algorithm, int arraySize, int testRepeatCount) {
    Method algorithmMethod = Algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      return -1;
    } else if (arraySize < 2) {
      return -1;
    } else if (testRepeatCount < 1) {
      return -1;
    }

    long startTime, endTime, averageExecutionTime = 0;

    for (int i = 1; i <= testRepeatCount; i++) {
      Integer[] array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);

      startTime = thread.getCurrentThreadCpuTime();
      Algorithms.runAlgorithmMethod(algorithmMethod, array);
      endTime = thread.getCurrentThreadCpuTime() - startTime;
      averageExecutionTime += (endTime - averageExecutionTime) / (long) i;
    }

    return averageExecutionTime;
  }

  public void warmUp() {
    runAnalysis("bubbleSort", 20000, 2);
  }

  public static String formatNanoTime(long nanoTime) {
    return String.format("%.3fs", (double) nanoTime / 1000000000.0);
  }
}
