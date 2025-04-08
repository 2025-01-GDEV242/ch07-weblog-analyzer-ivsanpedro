import java.util.HashMap;
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    //Count the frequency of each element
    private int[] frequency;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }

    /**
     * Return total number of accesses recorded
     *
     * @param  none
     * @return    The number of accesses recoreded in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        //Add the value in each element of hourCounts to total
        for (int count = 0; count < hourCounts.length; count++){
            total += hourCounts[count];
        }
        return total;
    }

    /**
     * Returns the hour with the most accesses
     *
     * @param  none
     * @return    the hour with the biggest count
     */
    public int busiestHour()
    {
        int busiestHour = hourCounts[0];
        int maxcount = 1;

        for (int i = 0; i < hourCounts.length; i++){
            int currentHour = hourCounts[i];
            int currentCount = 0;
            for (int j = 0; j < hourCounts.length; j++){
                if (hourCounts[j] == currentHour){
                    currentCount++;
                }
            }
            if (currentCount > maxcount) {
                busiestHour = currentHour;
                maxcount = currentCount;
            }
        }
        return busiestHour;
    }

    /**
     * Returns the hour with the smallest count
     *
     * @param  none
     * @return    the hour with the smallest count 
     */
    public int quietestHour()
    {
        int quietestHour = hourCounts[0];
        int minCount = 30000;
        
        for (int i = 0; i < hourCounts.length; i++){
            int currentElement = hourCounts[i];
            int currentCount = 0;
            for (int j = 0; j< hourCounts.length; j++){
                if (hourCounts[j] == currentElement) {
                    currentCount++;
                }
            }
            if (currentCount < minCount){
                quietestHour = currentElement;
                minCount = currentCount;
            }
        }
        return quietestHour;
    }

}
