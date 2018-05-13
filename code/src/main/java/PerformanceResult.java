
public class PerformanceResult {
  public final long averageExecutionTime;
  public final long averageIterationCount;
  public final long averageSwapCount;

  public PerformanceResult(long averageExecutionTime, long averageIterationCount, long averageSwapCount) {
    this.averageExecutionTime = averageExecutionTime;
    this.averageIterationCount = averageIterationCount;
    this.averageSwapCount = averageSwapCount;
  }
}