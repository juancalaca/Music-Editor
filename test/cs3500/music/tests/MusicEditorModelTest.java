package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Pitch;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the {@code MusicEditorModel} class.
 */
public class MusicEditorModelTest {

  IMusicEditorOperations modelEmpty;
  IMusicEditorOperations modelThree;
  IMusicEditorOperations modelFour;
  IMusicEditorOperations modelGiven;

  /**
   * Runs before each test, initializes values.
   */
  @Before
  public void init() {
    this.modelEmpty = new MusicEditorModel();

    // Model with 3 notes
    this.modelThree = new MusicEditorModel();
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.A).setStartBeat(0).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build());

    // Model with 4 notes
    this.modelFour = new MusicEditorModel();
    this.modelFour
        .addNote(INote.builder().setOctave(2).setKey(Key.B).setStartBeat(2).setDuration(4).build());
    this.modelFour.addNote(
        INote.builder().setOctave(2).setKey(Key.CSHARP).setStartBeat(2).setDuration(4).build());
    this.modelFour.addNote(
        INote.builder().setOctave(2).setKey(Key.DSHARP).setStartBeat(2).setDuration(4).build());
    this.modelFour
        .addNote(INote.builder().setOctave(2).setKey(Key.E).setStartBeat(3).setDuration(4).build());

    // The given model on the assignments page
    this.modelGiven = new MusicEditorModel();

    // E3
    this.modelGiven.addNote(
        INote.builder().setPitch(new Pitch(3, Key.E)).setStartBeat(56).setDuration(8).build());

    // G3
    Pitch g3 = new Pitch(3, Key.G);
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(0).setDuration(7).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(8).setDuration(7).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(16).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(24).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(32).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(40).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(48).setDuration(8).build());

    // C4
    Pitch c4 = new Pitch(4, Key.C);
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(4).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(36).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(56).setDuration(8).build());

    // D4
    Pitch d4 = new Pitch(4, Key.D);
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(2).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(6).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(16).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(18).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(20).setDuration(4).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(34).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(38).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(48).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(50).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(54).setDuration(2).build());

    // E4
    Pitch e4 = new Pitch(4, Key.E);
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(0).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(8).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(10).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(12).setDuration(3).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(24).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(32).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(40).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(42).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(44).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(46).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(52).setDuration(2).build());

    // G4
    Pitch g4 = new Pitch(4, Key.G);
    this.modelGiven.addNote(INote.builder().setPitch(g4).setStartBeat(26).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(g4).setStartBeat(28).setDuration(4).build());
  }


  /**
   * Tests that adding a note beyond the size of the editor will grow the editor.
   */
  @Test
  public void addNoteBeyondSizeOfEditor() {
    this.modelThree.addNote(
        INote.builder().setPitch(new Pitch(2, Key.G)).setStartBeat(5).setDuration(2).build());
    List list = this.modelThree.getNotes();
    assertEquals(list.size(), 4);
  }

  /**
   * Test that remove note works.
   */
  @Test
  public void removeNoteTest() {
    List list = this.modelThree.getNotes();
    assertEquals(list.size(), 3);
    assertTrue(this.modelThree.removeNote(
        INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build()));
    list = this.modelThree.getNotes();
    assertEquals(list.size(), 2);
  }

  /**
   * Test that removing a note that is not there will not work.
   */
  @Test
  public void removeNoteNoNoteThere() {
    assertFalse(this.modelThree.removeNote(
        INote.builder().setOctave(2).setKey(Key.C).setStartBeat(34).setDuration(4).build()));
    List list = this.modelThree.getNotes();
    assertEquals(list.size(), 3);
  }

  /**
   * Test combining two empty models.
   */
  @Test
  public void combineTwoEmptiesTest() {
    this.modelEmpty.combine(new MusicEditorModel());
    assertEquals(this.modelEmpty.getNotes().size(), 0);
  }

  /**
   * Combine an empty model with a full model.
   */
  @Test
  public void combineAddEmptyToFullTest() {
    this.modelThree.combine(this.modelEmpty);
    assertEquals(this.modelEmpty.getNotes().size(), 0);
    assertEquals(this.modelThree.getNotes().size(), 3);
  }

  /**
   * Combine a full model with an empty model.
   */
  @Test
  public void combineAddFullToEmptyTest() {
    this.modelEmpty.combine(this.modelThree);
    assertEquals(this.modelEmpty.getNotes().size(), 3);
    assertEquals(this.modelThree.getNotes().size(), 3);
  }

  /**
   * Combine two full models.
   */
  @Test
  public void combineModelFourWithModelThreeTest() {
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 3);
    this.modelFour.combine(this.modelThree);
    assertEquals(this.modelFour.getNotes().size(), 7);
    assertEquals(this.modelThree.getNotes().size(), 3);

  }

  /**
   * Combine two full models.
   */
  @Test
  public void combineModelThreeWithModelFourTest() {
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 3);
    this.modelThree.combine(this.modelFour);
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 7);
  }

  /**
   * Test combining two empty models sequentially.
   */
  @Test
  public void combineSequentiallyTwoEmptiesTest() {
    this.modelEmpty.combineSequentially(new MusicEditorModel());
    assertEquals(this.modelEmpty.getNotes().size(), 0);
  }

  /**
   * Test combining full with an empty sequentially.
   */
  @Test
  public void combineSequentiallyAddEmptyToFullTest() {
    this.modelThree.combineSequentially(this.modelEmpty);
    assertEquals(this.modelEmpty.getNotes().size(), 0);
    assertEquals(this.modelThree.getNotes().size(), 3);
  }

  /**
   * Test combining empty with a full sequentially.
   */
  @Test
  public void combineSequentiallyAddToEmptyTest() {
    this.modelEmpty.combineSequentially(this.modelThree);
    assertEquals(this.modelEmpty.getNotes().size(), 3);
    assertEquals(this.modelThree.getNotes().size(), 3);
  }

  /**
   * Test combining two full models sequentially.
   */
  @Test
  public void combineSequentiallyModelFourWithModelThreeTest() {
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 3);
    this.modelFour.combineSequentially(this.modelThree);
    assertEquals(this.modelFour.getNotes().size(), 7);
    assertEquals(this.modelThree.getNotes().size(), 3);
  }

  /**
   * Test combining two full models sequentially.
   */
  @Test
  public void combineSequentiallyModelThreeWithModelFourTest() {
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 3);
    this.modelThree.combineSequentially(this.modelFour);
    assertEquals(this.modelFour.getNotes().size(), 4);
    assertEquals(this.modelThree.getNotes().size(), 7);
  }

  /**
   * Test behavior after add and remove.
   */
  @Test
  public void testBehaviorAfterAddAndRemove() {
    assertEquals(this.modelEmpty.getLength(), 0);
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.C, 8, 0, 3).build());
    assertEquals(this.modelEmpty.getHighest(),
        INote.builder().setPitch(new Pitch(8, Key.C)).build());
    assertEquals(this.modelEmpty.getLowest(),
        INote.builder().setPitch(new Pitch(8, Key.C)).build());
    assertEquals(this.modelEmpty.getNotes().size(), 1);
    assertEquals(this.modelEmpty.getLength(), 3);
    assertTrue(this.modelEmpty.removeNote(INote.builder().setPchOctStrDur(Key.C, 8, 0, 3).build()));
    assertEquals(this.modelEmpty.getLength(), 0);
  }

  /**
   * Test getLength.
   */
  @Test
  public void testGetLength() {
    assertEquals(this.modelEmpty.getNotes().size(), 0);
    assertEquals(this.modelEmpty.getLength(), 0);
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.C, 8, 0, 3).build());
    assertEquals(this.modelEmpty.getNotes().size(), 1);
    assertEquals(this.modelEmpty.getLength(), 3);
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.C, 3, 3, 1).build());
    assertEquals(this.modelEmpty.getLength(), 4);
  }

  /**
   * Test error thrown if getHighest with empty model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testErrorThrownOnEmptyModelHighest() {
    this.modelEmpty.getHighest();
  }

  /**
   * Test error thrown if getHighest with empty model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testErrorThrownOnEmptyModelLowest() {
    this.modelEmpty.getLowest();
  }

  /**
   * Test get tempo/setTempo.
   */
  @Test
  public void testGetSetTempo() {
    assertEquals(this.modelEmpty.getTempo(), 4);
    this.modelEmpty.setTempo(5);
    assertEquals(this.modelEmpty.getTempo(), 5);
  }

  /**
   * Test set tempo error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetSetTempoError() {
    this.modelEmpty.setTempo(-1);
  }

  /**
   * Test that error is thrown when accessing beat outside of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAllNotesAtBeatError() {
    this.modelFour.getAllNotesAtBeat(27);
  }

  /**
   * Test that error is thrown when accessing beat outside of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetTopNotesAtBeatError() {
    this.modelThree.getTopNotesAtBeat(-2);
  }

  /**
   * Test that error is thrown when accessing beat outside of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAllNotesAtBeatEmpty() {
    this.modelEmpty.getAllNotesAtBeat(0);
  }

  /**
   * Test getAllNotesAtBeat for valid beats.
   */
  @Test
  public void testGetAllNotesAtBeat() {
    List test = this.modelThree.getAllNotesAtBeat(1);
    assertEquals(test.size(), 3);
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.A).setStartBeat(0).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build()));
  }

  /**
   * Test getTopNotesAtBeat for valid beats.
   */
  @Test
  public void testGetTopNotesAtBeat() {
    List test = this.modelThree.getTopNotesAtBeat(3);
    assertEquals(test.size(), 3);
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.A).setStartBeat(0).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build()));
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.A).setStartBeat(2).setDuration(4).build());
    test = this.modelThree.getTopNotesAtBeat(3);
    assertEquals(test.size(), 3);
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.A).setStartBeat(2).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build()));
    assertTrue(test.contains(
        INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build()));
  }
}
