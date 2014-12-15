import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RadarTest.
 *
 * @ Paul Beckman
 * @ 12/13/14
 */
public class RadarTest
{
    /**
     * Default constructor for test class RadarTest
     */
    public RadarTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    /**
     * Velocity [1,1] with low noise
     */
    @Test
    public void test1(){
        Radar radar = new Radar(100, 100);
        radar.setNoiseFraction(.0001);
        radar.setMonsterLocation(50,50,1,1);
        radar.scan();
        for(int i = 0; i < 100; i++)
        {
            radar.scan();
        }
        assertEquals(1,radar.getVelocity()[0],1e-6);
        assertEquals(1,radar.getVelocity()[1],1e-6);
    }
    
    /**
     * Velocity [-3,4] with medium noise
     */
    @Test
    public void test2(){
        Radar radar = new Radar(100, 100);
        radar.setNoiseFraction(.001);
        radar.setMonsterLocation(30,70,-3,4);
        radar.scan();
        for(int i = 0; i < 100; i++)
        {
            radar.scan();
        }
        assertEquals(-3,radar.getVelocity()[0],1e-6);
        assertEquals(4,radar.getVelocity()[1],1e-6);
    }
    
    /**
     * Velocity [2,-5] with med-high noise
     */
    @Test
    public void test3(){
        Radar radar = new Radar(100, 100);
        radar.setNoiseFraction(.01);
        radar.setMonsterLocation(80,10,2,-5);
        radar.scan();
        for(int i = 0; i < 100; i++)
        {
            radar.scan();
        }
        assertEquals(2,radar.getVelocity()[0],1e-6);
        assertEquals(-5,radar.getVelocity()[1],1e-6);
    }
}
