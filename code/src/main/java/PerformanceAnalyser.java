import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;

public class PerformanceAnalyser {
  private ThreadMXBean thread;

  public PerformanceAnalyser() {
    thread = ManagementFactory.getThreadMXBean();
  }

  public long runAnalysis(String algorithm, int testRepeatCount,  int arraySize) {
    Method algorithmMethod = Algorithms.getAlgorithmMethod(algorithm);

    if (algorithmMethod == null) {
      return -1;
    } else if (testRepeatCount < 1) {
      return -1;
    } else if (arraySize < 2) {
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

  public long[] runAnalysis(String[] algorithms, int testRepeatCount,  int arraySize) {
    long[] averageExecutionTimes = new long[algorithms.length];

    for (int i = 0; i < algorithms.length; i++) {
      averageExecutionTimes[i] = runAnalysis(algorithms[i], testRepeatCount, arraySize);
    }

    return averageExecutionTimes;
  }

  public void warmUp() {
    runAnalysis("bubbleSort", 2, 20000);
  }

  public static String formatNanoTime(long nanoTime) {
    return String.format("%.3fs", (double) nanoTime / 1000000000.0);
  }
}
