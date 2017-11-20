package cs3500.music.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Pitch;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.sound.midi.MidiUnavailableException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class test MidiView in a variety of ways. The README.MD provides more details on purpose of
 * testing.
 */
public class MidiViewImplTest {

  MidiViewImpl midiView;
  IMusicEditorOperations<INote> modelGiven;
  IMusicEditorOperations<INote> modelThree;

  /**
   * Instantiates a midiView to test playback and exceptions thrown by class.
   */
  @Before
  public void init() {

    midiView = new MidiViewImpl(new MusicEditorModel());
  }

  /**
   * Test if initialization works with correct setup.
   */
  @Test
  public void testInitializeMidiPlayback() {
    try {
      midiView.initializeMidiPlayback();
    } catch (MidiUnavailableException e) {
      Assert.fail("Unexpected Exception");
    }
  }

  /**
   * Test an exception is thrown when we want to play a sequence that is not yet open.
   */
  @Test
  public void testStartPlayingAt_MidiNotInitialized() {
    try {
      midiView.startPlayingAt(0);
    } catch (MidiUnavailableException e) {
      assertTrue(e.getMessage().equals("Sequencer is not open."));
    }
  }

  /**
   * Tests that an exception is thrown is we want to stop a sequence that is not open.
   */
  @Test
  public void testStop_MidiNotInitialized() {
    try {
      midiView.stop();
    } catch (MidiUnavailableException e) {
      assertTrue(e.getMessage().equals("Sequencer is not open."));
    }
  }


  /**
   * Constructs model for testing.
   */
  public void constructGivenModel() {
    // Model with 3 notes
    this.modelThree = new MusicEditorModel();
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.A).setStartBeat(1).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.C).setStartBeat(2).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build());
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
   * Tests that the MIDI messages are made correctly by using the mock to create a list of the notes
   * in the sequence and compare them to the model's contents.
   */
  @Test
  public void testMidiMessagesSameAsSmallModel() {
    constructGivenModel();
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(modelThree);
    } catch (MidiUnavailableException e) {
      //do nothing
    }

    List<INote> notesMidi = mock.displayTrack();
    List<INote> notesModel = modelThree.getNotes();
    for (INote n : notesMidi) {
      assertTrue(notesModel.contains(n));
    }
  }

  /**
   * A bigger model that tests that MIDI messages are set and sent at the correct time using the
   * mock that provides the functionality of creating a list of notes from MIDI messages.
   */
  @Test
  public void testMidiMessagesSameAsGivenModel() {
    constructGivenModel();
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(modelGiven);
    } catch (MidiUnavailableException e) {
      //do nothing
    }
    List<INote> notesMidi = mock.displayTrack();
    List<INote> notesModel = modelGiven.getNotes();
    for (INote n : notesMidi) {
      assertTrue(notesModel.contains(n));
    }
  }

  /**
   * Test music reader and music composition.
   **/
  @Test
  public void testMaryLittleLambByMusicReader() {
    IMusicEditorOperations<INote> model = null;

    try {
      model = MusicReader.parseFile(
          new FileReader("mary-little-lamb.txt"),
          new MusicEditorModel.Builder());
    } catch (IOException e) {
      //do nothing
    }

    MockMidiViewImpl mock = null;

    try {
      mock = new MockMidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      //
    }

    List<INote> notes = mock.displayTrack();
    List<INote> modelNotes = model.getNotes();
    for (INote n : notes) {
      assertTrue(modelNotes.contains(n));
    }
  }

  /**
   * This test creates a log file from our data called midi-transcript.txt. It then reads from this
   * file and compares the contents to the mary-little-lamb.txt file. NOTE: The ordering of the
   * midi-transcript could not be guaranteed because notes that start on the same beat will be
   * ordered indiscriminately, but every "note" in mary-little-lamb.txt is also found in our
   * midi-transcript.txt.
   */
  @Test
  public void testMaryLittleLambLogFile() {

    // Create model.
    IMusicEditorOperations<INote> model = null;
    try {
      model = MusicReader.parseFile(
          new FileReader("mary-little-lamb.txt"),
          new MusicEditorModel.Builder());
    } catch (IOException e) {
      //do nothing
    }

    // Create mock.
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      //do nothing
    }

    // Write notes from mock to StringBuilder and then to file.
    List<INote> notes = mock.displayTrack();
    StringBuilder fromMock = new StringBuilder();
    fromMock.append("tempo " + model.getTempo() + "\n");

    for (INote n : notes) {
      fromMock.append(
          "note " + n.getStartBeat() + " " + (new Integer(n.getEndBeat() + 1).toString()) + " " + n
              .getInstrumentID() + " " + n
              .getPitch().getMidiValue() + " " + n.getVolume() + "\n");
    }

    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter("midi-transcript.txt"));
      writer.write(fromMock.toString());
      writer.close();
    } catch (Exception ex) {
      //do nothing
    }

    // Read from mary-little-lamb.txt.
    StringBuilder fromFile = new StringBuilder();
    try {
      Scanner scan = new Scanner(new File("mary-little-lamb.txt"));
      while (scan.hasNext()) {
        fromFile.append(scan.nextLine() + "\n");
      }
    } catch (Exception ex) {
      //do nothing
    }

    // Read back from midi-transcript.txt file.
    fromMock = new StringBuilder();
    try {
      Scanner scan = new Scanner(new File("midi-transcript.txt"));
      while (scan.hasNext()) {
        fromMock.append(scan.nextLine() + "\n");
      }
    } catch (Exception ex) {
      //do nothing
    }

    assertEquals(fromMock.length(), fromFile.length());
    List<String> stringsInSelfBuild = new ArrayList(Arrays.asList(fromMock.toString().split("\n")));
    List<String> stringsInFileBuild = new ArrayList(Arrays.asList(fromFile.toString().split("\n")));
    assertEquals(stringsInSelfBuild.size(), stringsInFileBuild.size());
    for (String s : stringsInFileBuild) {
      assertTrue(stringsInSelfBuild.contains(s));
    }
    for (String s : stringsInSelfBuild) {
      assertTrue(stringsInFileBuild.contains(s));
    }
  }

  /**
   * This tests that the sequencer returns 0 when it hasn't started playing yet.
   */
  @Test
  public void testGetBeatPosition() {
    constructGivenModel();
    MidiViewImpl givenMidi = new MidiViewImpl(modelGiven);
    try {
      givenMidi.initializeMidiPlayback();
    } catch (MidiUnavailableException e) {
      //do something
    }
    givenMidi.constructTrack();
    assertEquals(0, givenMidi.getBeatPosition());
  }

}