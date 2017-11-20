package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.Key;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@code Key}.
 */
public class KeyTest {

  /**
   * Test toString() method.
   */
  @Test
  public void toStringTest() {
    Assert.assertEquals(Key.A.toString(), "A");
    assertEquals(Key.ASHARP.toString(), "A#");
    assertEquals(Key.B.toString(), "B");
    assertEquals(Key.C.toString(), "C");
    assertEquals(Key.CSHARP.toString(), "C#");
    assertEquals(Key.D.toString(), "D");
    assertEquals(Key.DSHARP.toString(), "D#");
    assertEquals(Key.E.toString(), "E");
    assertEquals(Key.F.toString(), "F");
    assertEquals(Key.FSHARP.toString(), "F#");
    assertEquals(Key.G.toString(), "G");
    assertEquals(Key.GSHARP.toString(), "G#");
  }

  /**
   * Test Iterator methods, hasNext and next.
   */
  @Test
  public void IterateTest() {
    Key test = Key.A;
    assertEquals(test, Key.A);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.ASHARP);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.B);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.C);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.CSHARP);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.D);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.DSHARP);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.E);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.F);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.FSHARP);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.G);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.GSHARP);
    assertTrue(test.hasNext());
    test = test.next();
    assertEquals(test, Key.A);
    assertTrue(test.hasNext());
  }

  /**
   * Test compare method.
   */
  @Test
  public void compareTest() {
    assertTrue(Key.A.compare(Key.B) < 0);
    assertTrue(Key.A.compare(Key.A) == 0);
    assertTrue(Key.B.compare(Key.A) > 0);
    assertTrue(Key.A.compare(Key.ASHARP) < 0);
    assertTrue(Key.A.compare(Key.G) > 0);
    assertTrue(Key.A.compare(Key.F) > 0);
    assertTrue(Key.B.compare(Key.D) > 0);
    assertTrue(Key.C.compare(Key.CSHARP) < 0);
    assertTrue(Key.CSHARP.compare(Key.D) < 0);
    assertTrue(Key.B.compare(Key.C) > 0);
  }

  /**
   * Tests getKey method.
   */
  @Test
  public void getKeyTest() {
    assertEquals(Key.getKey(0), Key.C);
    assertEquals(Key.getKey(1), Key.CSHARP);
    assertEquals(Key.getKey(2), Key.D);
    assertEquals(Key.getKey(3), Key.DSHARP);
    assertEquals(Key.getKey(4), Key.E);
    assertEquals(Key.getKey(5), Key.F);
    assertEquals(Key.getKey(6), Key.FSHARP);
    assertEquals(Key.getKey(7), Key.G);
    assertEquals(Key.getKey(8), Key.GSHARP);
    assertEquals(Key.getKey(9), Key.A);
    assertEquals(Key.getKey(10), Key.ASHARP);
    assertEquals(Key.getKey(11), Key.B);
  }

  /**
   * Error throw if outside of bounds for getKey.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getKeyErrorNeg() {
    Key.getKey(-1);
  }


  /**
   * Error throw if outside of bounds for getKey.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getKeyError12() {
    Key.getKey(12);
  }
}
