package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.IPitch;
import cs3500.music.model.Key;
import cs3500.music.model.Pitch;
import org.junit.Test;

/**
 * Tester class for {@code Pitch} class.
 */
public class PitchTest {

  /**
   * Test equals.
   */
  @Test
  public void equalsTest() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test2 = new Pitch(2, Key.A);
    Pitch test3 = new Pitch(3, Key.A);
    Pitch test4 = new Pitch(2, Key.B);
    assertTrue(test.equals(test2));
    assertFalse(test.equals(test3));
    assertFalse(test.equals(test4));
  }

  /**
   * Test hashcode.
   */
  @Test
  public void hashCodeTest() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test2 = new Pitch(2, Key.A);
    Pitch test3 = new Pitch(3, Key.A);
    Pitch test4 = new Pitch(2, Key.B);
    assertEquals(test.hashCode(), test2.hashCode());
    assertNotEquals(test.hashCode(), test3.hashCode());
    assertNotEquals(test.hashCode(), test4.hashCode());
  }

  /**
   * Test compare method, Pitch that Key ordering is used.
   */
  @Test
  public void compareTest() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test2 = new Pitch(2, Key.A);
    Pitch test3 = new Pitch(3, Key.A);
    Pitch test4 = new Pitch(2, Key.B);
    Pitch test5 = new Pitch(2, Key.G);
    assertTrue(test.compareTo(test) == 0);
    assertTrue(test.compareTo(test2) == 0);
    assertTrue(test.compareTo(test3) < 0);
    assertTrue(test.compareTo(test4) < 0);
    assertTrue(test.compareTo(test5) > 0);
  }

  /**
   * Test toString.
   */
  @Test
  public void toStringTest() {
    IPitch test = new Pitch(2, Key.A);
    assertEquals(test.toString(), "A2");
    IPitch test2 = new Pitch(1, Key.DSHARP);
    assertEquals(test2.toString(), "D#1");
  }

  /**
   * Test iterator, Pitch that Key ordering is used.
   */
  @Test
  public void iteratorTest() {
    IPitch test = new Pitch(2, Key.A);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(2, Key.ASHARP));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(2, Key.B));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.C));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.CSHARP));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.D));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.DSHARP));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.E));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.F));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.FSHARP));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.G));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.GSHARP));
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, new Pitch(3, Key.A));
    assertTrue(test.hasNext());
  }

  /**
   * Get Octave test.
   */
  @Test
  public void getOctave() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test1 = new Pitch(3, Key.A);
    assertEquals(test.getOctave(), 2);
    assertEquals(test1.getOctave(), 3);
  }

  /**
   * Get Key test.
   */
  @Test
  public void getKey() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test1 = new Pitch(3, Key.B);
    assertEquals(test.getKey(), Key.A);
    assertEquals(test1.getKey(), Key.B);
  }

  /**
   * Get Midi value test.
   */
  @Test
  public void getMidi() {
    Pitch test = new Pitch(2, Key.A);
    Pitch test1 = new Pitch(3, Key.B);
    assertEquals(test.getMidiValue(), 45);
    assertEquals(test1.getMidiValue(), 59);
  }
}
