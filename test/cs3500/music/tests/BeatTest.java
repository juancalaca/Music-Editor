package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.Beat;
import cs3500.music.model.IBeat;
import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.Pitch;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for the {@code Beat} class.
 */
public class BeatTest {

  IBeat beatEmpty;
  IBeat beat1;

  /**
   * Initialize fields.
   */
  @Before
  public void init() {
    this.beatEmpty = new Beat();
    this.beat1 = new Beat();
    this.beat1.add(INote.builder().setPchOctStrDur(Key.A, 2, 0, 5).build());
    this.beat1.add(INote.builder().setPchOctStrDur(Key.A, 3, 0, 5).build());
    this.beat1.add(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build());
    this.beat1.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
  }

  /**
   * Test that a Beat with no args will return an empty TreeMap.
   */
  @Test
  public void constructorNoArgsTest() {
    IBeat beat = new Beat();
    assertEquals(beat.getAllNotes().size(), 0);
  }

  /**
   * Test that beat1 had notes added.
   */
  @Test
  public void constructorWithNotesTest() {
    List test = this.beat1.getAllNotes();
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.A, 2, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.A, 3, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertEquals(test.size(), 4);
  }

  /**
   * Test that adding notes to a beat actually works.
   */
  @Test
  public void addTest() {
    assertEquals(this.beatEmpty.getAllNotes().size(), 0);
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
    List test = this.beatEmpty.getAllNotes();
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertEquals(this.beatEmpty.getAllNotes().size(), 1);
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 7, 0, 5).build());
    test = this.beatEmpty.getAllNotes();
    assertEquals(this.beatEmpty.getAllNotes().size(), 2);
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 7, 0, 5).build()));
  }

  /**
   * Test that add works when notes heads are at the same beat is already mapped.
   */
  @Test
  public void addHeadAlreadyMapped() {
    assertEquals(this.beatEmpty.getAllNotes().size(), 0);
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 3).build());
    List test = this.beatEmpty.getAllNotes();
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 3).build()));
    assertEquals(this.beatEmpty.getAllNotes().size(), 2);
  }

  /**
   * Test that add works when placing note on a sustain that is already mapped.
   */
  @Test
  public void addHeadOnSustainTest() {
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 2, 5).build());
    List test = this.beatEmpty.getAllNotes();
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 2, 5).build()));
    assertEquals(this.beatEmpty.getAllNotes().size(), 2);
  }

  /**
   * Test that adding a note that continues into another note's head still works.
   */
  @Test
  public void addHeadOverLapOtherHead() {
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 2, 5).build());
    this.beatEmpty.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
    List test = this.beatEmpty.getAllNotes();
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 2, 5).build()));
    assertEquals(this.beatEmpty.getAllNotes().size(), 2);
  }

  /**
   * Test that getAllNotes works.
   */
  @Test
  public void getAllNotesWorks() {
    this.beat1.add(INote.builder().setPchOctStrDur(Key.A, 2, 0, 6).build());
    List test = this.beat1.getTopNotes();
    assertEquals(test.size(), 4);
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.A, 2, 0, 6).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.A, 3, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build()));
    assertTrue(test.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
  }

  /**
   * Test that getTopNotes works.
   */
  @Test
  public void getTopNotes() {
    List test1 = this.beatEmpty.getAllNotes();
    List test2 = this.beat1.getAllNotes();
    assertEquals(test1.size(), 0);
    assertEquals(test2.size(), 4);
    assertTrue(test2.contains(INote.builder().setPchOctStrDur(Key.A, 2, 0, 5).build()));
    assertTrue(test2.contains(INote.builder().setPchOctStrDur(Key.A, 3, 0, 5).build()));
    assertTrue(test2.contains(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build()));
    assertTrue(test2.contains(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
  }

  /**
   * Remove works.
   */
  @Test
  public void removeTest() {
    assertEquals(this.beat1.getAllNotes().size(), 4);
    assertTrue(this.beat1.remove(INote.builder().setPchOctStrDur(Key.A, 2, 0, 5).build()));
    assertTrue(this.beat1.remove(INote.builder().setPchOctStrDur(Key.A, 3, 0, 5).build()));
    assertEquals(this.beat1.getAllNotes().size(), 2);
  }

  /**
   * Remove an element that doesn't exist returns false.
   */
  @Test
  public void removeElementDoesntExistTest() {
    assertEquals(this.beat1.getAllNotes().size(), 4);
    assertFalse(this.beat1.remove(INote.builder().setPchOctStrDur(Key.FSHARP, 2, 2, 5).build()));
    assertEquals(this.beat1.getAllNotes().size(), 4);
  }

  /**
   * Test that maxPitch returns the appropriate value and the it adjusts on a remove and add.
   */
  @Test
  public void maxPitchTest() {
    Assert.assertEquals(this.beat1.maxPitch(), new Pitch(3, Key.B));
    assertTrue(this.beat1.remove(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build()));
    assertEquals(this.beat1.maxPitch(), new Pitch(3, Key.A));
    this.beat1.add(INote.builder().setPchOctStrDur(Key.B, 3, 0, 5).build());
    assertEquals(this.beat1.maxPitch(), new Pitch(3, Key.B));
  }

  /**
   * Test maxPitch on empty.
   */
  @Test(expected = NoSuchElementException.class)
  public void maxPitchEmptyTest() {
    this.beatEmpty.maxPitch();
  }

  /**
   * Test that minPitch returns the appropriate value and the it adjusts on a remove and add.
   */
  @Test
  public void minPitchTest() {
    assertEquals(this.beat1.minPitch(), new Pitch(2, Key.G));
    assertTrue(this.beat1.remove(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build()));
    assertEquals(this.beat1.minPitch(), new Pitch(2, Key.A));
    this.beat1.add(INote.builder().setPchOctStrDur(Key.G, 2, 0, 5).build());
    assertEquals(this.beat1.minPitch(), new Pitch(2, Key.G));
  }

  /**
   * Test minPitch on empty.
   */
  @Test(expected = NoSuchElementException.class)
  public void minPitchEmptyTest() {
    this.beatEmpty.minPitch();
  }
}
