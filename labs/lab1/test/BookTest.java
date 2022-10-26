
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the Book class.
 */

public class BookTest {
  private Book hungerGames;

  @Before
  public void setUp() {
    Person collins = new Person("Suzanne", "Collins", 1960);
    hungerGames = new Book("The Hunger Games", collins, 20.54f);
  }

  @Test
  public void testFirst() {
    assertEquals("The Hunger Games", hungerGames.getTitle());
  }

  @Test
  public void testSecond() {
    assertEquals(20.54, hungerGames.getPrice(),0.01);
  }

  @Test
  public void testThird() {
    Person author = hungerGames.getAuthor();
    String authorName = author.getFirstName();
    assertEquals("Suzanne", authorName);
  }
}
