package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.NoteStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for {@code Notes} class.
 */
public class NoteTest {

  INote n1;
  INote n2;
  INote n3;
  INote n4;

  /**
   * Helper functions instantiates all instance variables.
   */
  @Before
  public void init() {
    n1 = INote.builder().build();
    n2 = INote.builder().setKey(Key.CSHARP).setOctave(4).setStartBeat(4).setDuration(5)
        .build();
    n3 = INote.builder().setPchOctStrDur(Key.A, 3, 7, 3).build();
    n4 = INote.builder().setInstrumentID(3).setVolume(5).build();
  }

  /**
   * Tests various getters for the note class.
   */
  @Test
  public void testNoteBuilder_DifferentParams() {
    INote test = INote.builder().build();
    assertTrue(test.getKey() == Key.C);
    assertEquals(4, test.getOctave());
    assertEquals(0, test.getStartBeat());
    assertEquals(0, test.getDuration());
    assertEquals(0, test.getInstrumentID());
    assertEquals(0, test.getVolume());
    assertEquals(0, test.getEndBeat());

    test = INote.builder().setPchOctStrDur(Key.D, 5, 2, 5).build();
    assertTrue(test.getKey() == Key.D);
    assertEquals(5, test.getOctave());
    assertEquals(2, test.getStartBeat());
    assertEquals(5, test.getDuration());
    assertEquals(0, test.getInstrumentID());
    assertEquals(0, test.getVolume());
    assertEquals(6, test.getEndBeat());

    INote copyTest = INote.builder().fromNote(test).build();
    assertTrue(copyTest.getKey() == Key.D);
    assertEquals(5, copyTest.getOctave());
    assertEquals(2, copyTest.getStartBeat());
    assertEquals(5, copyTest.getDuration());
    assertEquals(0, copyTest.getInstrumentID());
    assertEquals(0, copyTest.getVolume());
    assertEquals(6, test.getEndBeat());

    copyTest = INote.builder().fromNote(test).setOctave(6).setVolume(3).build();
    assertTrue(copyTest.getKey() == Key.D);
    assertEquals(6, copyTest.getOctave());
    assertEquals(2, copyTest.getStartBeat());
    assertEquals(5, copyTest.getDuration());
    assertEquals(0, copyTest.getInstrumentID());
    assertEquals(3, copyTest.getVolume());
    assertEquals(6, test.getEndBeat());
  }

  /**
   * Tests that builder checks that each parameter is greater than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuilderChecks_GreaterZero() {
    INote.builder().setStartBeat(-1).build();
  }

  /**
   * Test getStatus at beat.
   */
  @Test
  public void testGetStatus() {
    INote test = INote.builder().setPchOctStrDur(Key.D, 5, 2, 5).build();
    Assert.assertEquals(test.getStatusAtBeat(0), NoteStatus.REST);
    assertEquals(test.getStatusAtBeat(2), NoteStatus.HEAD);
    assertEquals(test.getStatusAtBeat(3), NoteStatus.SUSTAIN);
    assertEquals(test.getStatusAtBeat(6), NoteStatus.SUSTAIN);
    assertEquals(test.getStatusAtBeat(7), NoteStatus.REST);
  }
}
