package cs3500.music.tests;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.ReadOnlyModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the read only model. Tests for all the methods that mutate the model to
 * throw exceptions. We check that no exception is thrown for a method that performs read-only
 * actions.
 */
public class ReadOnlyModelTest {

  IMusicEditorOperations readOnlyModel;

  @Before
  public void init() {
    readOnlyModel = new ReadOnlyModel(new MusicEditorModel());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddThrows() {
    readOnlyModel.addNote(INote.builder().build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveThrows() {
    readOnlyModel.removeNote(INote.builder().build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineThrows() {
    readOnlyModel.combine(new MusicEditorModel());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSeqThrows() {
    readOnlyModel.combineSequentially(new MusicEditorModel());
  }

  @Test
  public void testGetSongsNoThrow() {
    try {
      readOnlyModel.getNotes();
    } catch (IllegalArgumentException e) {
      Assert.fail("Unexpected exception");
    }
  }

}