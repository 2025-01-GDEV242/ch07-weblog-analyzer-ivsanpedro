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
     * Return the number of accesses recorded in the log file.
     *
     */
    public int numberOfAccesses()
    {
        analyzeHourlyData();
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
        int busiestHour = 0;
        for (int hour = 1; hour < hourCounts.length; hour++){
            int hourCount = hourCounts[hour];
            if (hourCount > hourCounts[busiestHour]){
                busiestHour = hour;
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
        int quietestHour = 0;
        for (int hour = 1; hour < hourCounts.length; hour++){
            int hourCount = hourCounts[hour];
            if (hourCount < hourCounts[quietestHour]){
                quietestHour = hour;
            }
        }
        return quietestHour;
    }

    /**
     * Return the first hour of the busiest two-hour period 
     *
     * @param  none
     * @return    the first hour of the busiest two-hour period
     */
    public int busiestTwoHour()
    {
        int busiestHour = 0; 
        int maxCount = 0;
        for (int hour = 0; hour < hourCounts.length-3; hour+=3){
            //System.out.println("Busiest hour: " + busiestHour);
            //System.out.println("Max Count: " + maxCount);
            //System.out.println("Hour: " + hour);
            int total = 0;
            int currentHour = hour;
            //System.out.println("Current Hour: " + currentHour);
            for (int hr = 0; hr < 3; hr++){
                total += hourCounts[currentHour];
                currentHour++;
                //System.out.println("Hr: " + hr);
                //System.out.println("Hour Counts Hr: " + hourCounts[hr]);
                //System.out.println("Total: " + total);
            }
            
            //System.out.println("Final Total: " + total);
            if (total > maxCount){
                busiestHour = hour;
                maxCount = total;
            }
        }
        
        return busiestHour;
    }


}
