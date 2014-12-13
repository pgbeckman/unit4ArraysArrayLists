
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class Radar
{

    // stores whether each cell triggered detection for the current scan of the radar
    private boolean[][] currentScan;
    private boolean[][] pastScan;

    //stores velocity objects
    //private Velocity[][] firstScanVelocity;

    // value of each cell is incremented for each scan in which that cell triggers detection 
    private int[][] accumulator;

    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;
    private int xVel = 3;
    private int yVel = 3;

    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;

    // number of scans of the radar since construction
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        currentScan = new boolean[rows][cols]; // elements will be set to false
        pastScan = new boolean[rows][cols];
        accumulator = new int[11][11]; // elements will be set to 0
        
        numScans= 0;
    }

    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * 
     */
    public void scan()
    {
        // zero the current scan grid
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                currentScan[row][col] = false;
            }
        }

        monsterLocationCol+=xVel;
        monsterLocationRow+=yVel;
        
        // detect the monster
        if(monsterLocationCol>94){
            monsterLocationCol = 5;
            monsterLocationRow++;
        }
        else if(monsterLocationCol<5){
            monsterLocationCol = 94;
            monsterLocationRow--;
        }
        if(monsterLocationRow>94){
            monsterLocationRow = 5;
            monsterLocationCol++;
        }
        else if(monsterLocationRow<5){
            monsterLocationRow = 94;
            monsterLocationCol--;
        }
        
        currentScan[monsterLocationRow][monsterLocationCol] = true;

        // inject noise into the grid
        injectNoise();
        
        

        // udpate the accumulator advanced
        /*
        for(int row = 0; row < currentScan.length; row++) {
        for(int col = 0; col < currentScan[0].length; col++) {
        for(int i = 0; i < firstScanVelocity[row][col].getLength(); i++) {
        if(!currentScan[col+firstScanVelocity[row][col].getDx(i)*this.getNumScans()][row+firstScanVelocity[row][col].getDy(i)*this.getNumScans()]){
        firstScanVelocity[row][col].removeVector(col,row);
        }
        }
        }
        }
         */
        
        // udpate the accumulator basic
        for(int row = 5; row < currentScan.length-5; row++) {
            for(int col = 5; col < currentScan[0].length-5; col++) {
                if(pastScan[row][col]){
                    for(int i = -5; i <= 5; i++) {
                        for(int j = -5; j <= 5; j++) {
                            if(currentScan[row+i][col+j]){
                                accumulator[i+5][j+5]++;
                                System.out.println(j + " " + i + "Acc: " + accumulator[i+5][j+5]);
                            }
                        }
                    }
                }
            }
        }
        
        for(int row = 0; row < currentScan.length; row++) {
            for(int col = 0; col < currentScan[0].length; col++) {
                pastScan[row][col] = currentScan[row][col];
            }
        }
        
        // keep track of the total number of scans
        numScans++;
    }

    /**
     * Sets the location and velocity of the monster
     * 
     * @param   row     the row in which the monster is located
     * @param   col     the column in which the monster is located
     * @pre row and col must be within the bounds of the radar grid
     */
    public void setMonsterLocation(int row, int col, int dx, int dy)
    {
        // remember the row and col of the monster's location
        monsterLocationRow = row;
        monsterLocationCol = col;
        xVel = dx;
        yVel = dy;
        
        currentScan[row][col] = true;
    }

    /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a false detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }

    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }

    /**
     * Returns the most common dx value from the accumulator
     * 
     */
    public int[] getVelocity()
    {
        int maxCount = 0;
        int maxDx = 0;
        int maxDy = 0;
        
        for(int row = 0; row < accumulator.length; row++) {
            for(int col = 0; col < accumulator[0].length; col++) {
                if(accumulator[row][col] > maxCount){
                    maxDx = col - 5;
                    maxDy = row - 5;
                    maxCount = accumulator[row][col];
                }
            }
        } 
        
        int[] maxArray = {maxDx, maxDy};
        return maxArray;
    }
    
    /**
     * Prints the accumulator array
     * 
     */
    public void printAccumulator()
    {
        System.out.println("");
        for(int row = 0; row < accumulator.length; row++) {
            for(int col = 0; col < accumulator[0].length; col++) {
                System.out.print(accumulator[row][col]);
            }
            System.out.println("");
        } 
    }
    
    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }

    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }

    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }

    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    private void injectNoise()
    {
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                // each cell has the specified probablily of being a false positive
                if(Math.random() < noiseFraction)
                {
                    currentScan[row][col] = true;
                }
            }
        }
    }

}
