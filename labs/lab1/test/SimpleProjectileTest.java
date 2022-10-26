import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the SimpleProjectile class.
 */
public class SimpleProjectileTest {

  private SimpleProjectile proj;

  // only for special case where vertical velocity is 0
  private SimpleProjectile zeroVertProj;

  @Before
  public void setUp() {

    proj = new SimpleProjectile(3.23f, 1.21f, 3.5f, 3.7f);
    zeroVertProj = new SimpleProjectile(3.23f, 1.21f, 3.5f, 0);

  }

  @Test
  public void testZeroVertVel() {
    String expectedString = "At time 5.24: position is (21.57,1.21)";
    String outputString = zeroVertProj.getState(5.24f);
    assertEquals(expectedString, outputString);
  }

  @Test
  public void testNegativeTime() {
    String expectedString = "At time -4.00: position is (3.23,1.21)";
    String outputString = proj.getState(-4);
    assertEquals(expectedString, outputString);
  }

  @Test
  public void testZeroTime() {
    String expectedString = "At time 0.00: position is (3.23,1.21)";
    String outputString = proj.getState(0);
    assertEquals(expectedString, outputString);
  }

  @Test
  public void testBeforeGround() {
    String expectedString = "At time 0.00: position is (3.23,1.21)";
    String outputString = proj.getState(0);
    assertEquals(expectedString, outputString);
  }

  @Test
  public void testAtGround() {
    String expectedString = "At time 0.00: position is (3.23,1.21)";
    String outputString = proj.getState(0);
    assertEquals(expectedString, outputString);
  }

  @Test
  public void testAfterGround() {
    String expectedString = "At time 0.00: position is (3.23,1.21)";
    String outputString = proj.getState(0);
    assertEquals(expectedString, outputString);
  }
}
