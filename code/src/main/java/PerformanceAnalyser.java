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

    Integer[] array;
    PerformanceResult performanceResult = new PerformanceResult();
    long startTime, endTime;

    for (int i = 0; i < testRepeatCount; i++) {
      array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);

      startTime = thread.getCurrentThreadCpuTime();
      algorithms.runAlgorithmMethod(algorithmMethod, array);
      endTime = thread.getCurrentThreadCpuTime() - startTime;

      performanceResult.addTestResult(endTime, algorithms.getIterationCount(), algorithms.getSwapCount());
    }

    return performanceResult;
  }

  public PerformanceResult[] runAnalysis(String[] algorithmList, int testRepeatCount,  int arraySize) {
    Method[] algorithmMethods = new Method[algorithmList.length];

    if (testRepeatCount < 1) {
      return null;
    } else if (arraySize < 2) {
      return null;
    }

    for (int i = 0; i < algorithmList.length; i++) {
      algorithmMethods[i] = algorithms.getAlgorithmMethod(algorithmList[i]);

      if (algorithmMethods[i] == null) {
        return null;
      }
    }

    Integer[] array, testArray;
    PerformanceResult[] performanceResults = new PerformanceResult[algorithmList.length];
    long startTime, endTime;

    for (int i = 0; i < algorithmList.length; i++) {
      performanceResults[i] = new PerformanceResult();
    }

    for (int i = 0; i < testRepeatCount; i++) {
      array = ArrayUtilities.generateRandomIntegerArray(arraySize, 0, arraySize);

      for (int j = 0; j < algorithmList.length; j++) {
        testArray = array.clone();

        startTime = thread.getCurrentThreadCpuTime();
        algorithms.runAlgorithmMethod(algorithmMethods[j], testArray);
        endTime = thread.getCurrentThreadCpuTime() - startTime;

        performanceResults[j].addTestResult(endTime, algorithms.getIterationCount(), algorithms.getSwapCount());
      }
    }

    return performanceResults;
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
