package cs3500.music.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.NoteStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tester class for {@code NoteStatus} enum.
 */
public class NoteStatusTest {

  /**
   * Test toString method.
   */
  @Test
  public void toStringTest() {
    Assert.assertEquals(NoteStatus.SUSTAIN.toString(), "|");
    assertEquals(NoteStatus.HEAD.toString(), "X");
    assertEquals(NoteStatus.REST.toString(), " ");
  }

  /**
   * Test equals method.
   */
  @Test
  public void equalsTest() {
    NoteStatus nt1 = NoteStatus.SUSTAIN;
    NoteStatus nt2 = NoteStatus.SUSTAIN;
    assertTrue(nt1.equals(nt2));
    NoteStatus nt3 = NoteStatus.HEAD;
    NoteStatus nt4 = NoteStatus.HEAD;
    assertTrue(nt3.equals(nt4));
    NoteStatus nt5 = NoteStatus.REST;
    NoteStatus nt6 = NoteStatus.REST;
    assertTrue(nt5.equals(nt6));
    assertFalse(nt1.equals(nt3));
    assertFalse(nt3.equals(nt1));
    assertFalse(nt5.equals(nt1));
    assertFalse(nt1.equals(nt5));
    assertFalse(nt5.equals(nt3));
    assertFalse(nt3.equals(nt5));
  }
}
