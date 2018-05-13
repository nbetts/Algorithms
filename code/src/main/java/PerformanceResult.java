import java.util.ArrayList;

public class PerformanceResult {
  private ArrayList<Long> executionTimes;
  private ArrayList<Long> iterationCounts;
  private ArrayList<Long> swapCounts;

  private long averageExecutionTime;
  private long averageIterationCount;
  private long averageSwapCount;

  public PerformanceResult() {
    executionTimes = new ArrayList<>();
    iterationCounts = new ArrayList<>();
    swapCounts = new ArrayList<>();

    averageExecutionTime = 0;
    averageIterationCount = 0;
    averageSwapCount = 0;
  }

  public void addTestResult(long executionTime, long iterationCount, long swapCount) {
    if (executionTime < 0 || iterationCount < 0 || swapCount < 0) {
      return;
    }

    executionTimes.add(executionTime);
    iterationCounts.add(iterationCount);
    swapCounts.add(swapCount);

    long resultCount = (long) executionTimes.size();

    averageExecutionTime += (executionTime - averageExecutionTime) / resultCount;
    averageIterationCount += (iterationCount - averageIterationCount) / resultCount;
    averageSwapCount += (swapCount - averageSwapCount) / resultCount;
  }

  public ArrayList<Long> getExecutionTimes() {
    return executionTimes;
  }

  public ArrayList<Long> getIterationCounts() {
    return iterationCounts;
  }

  public ArrayList<Long> getSwapCounts() {
    return swapCounts;
  }

  public long getAverageExecutionTime() {
    return averageExecutionTime;
  }

  public long getAverageIterationCount() {
    return averageIterationCount;
  }

  public long getAverageSwapCount() {
    return averageSwapCount;
  }
}
