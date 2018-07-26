package gdprdashboard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GDPRDashboardTest {
    
    public GDPRDashboardTest() {
    }
    
    @Before
    public void setUp() {
        System.out.println("Setting up method");
    }
    
    @After
    public void tearDown() {
        System.out.println("Tearing down method");
    }

    /**
     * Test of main method, of class GDPRDashboard.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        InteractiveGDPRDashboard.main(args);
    }
    
}
