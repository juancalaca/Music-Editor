package cs3500.music.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.GuiController;
import cs3500.music.controller.IViewController;
import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Pitch;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeViewImpl;
import cs3500.music.view.ConsoleViewImpl;
import cs3500.music.view.GuiViewImpl;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import java.awt.Button;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.util.List;
import javax.sound.midi.MidiUnavailableException;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for the controller class.
 */
public class ControllerTest {

  IMusicEditorOperations<INote> model;
  IView consView;
  MidiViewImpl midiView;
  GuiViewImpl guiView;
  CompositeViewImpl compView;
  IViewController consCont;
  IViewController midiCont;
  StringBuffer sb;

  /**
   * Initialize variables for testing.
   */
  @Before
  public void init() {
    try {
      model = MusicReader.parseFile(new FileReader("mary-little-lamb.txt"),
          new MusicEditorModel.Builder());
    } catch (Exception ex) {
      // Do nothing
    }
    this.sb = new StringBuffer("");
    this.consView = new ConsoleViewImpl(model, sb);
    this.midiView = new MidiViewImpl(model);
    this.guiView = new GuiViewImpl(model);
    this.compView = new CompositeViewImpl(this.midiView, this.guiView);
    this.consCont = new IViewController(this.consView);
    this.midiCont = new IViewController(this.midiView);
  }


  /**
   * Test that the console controller displays an empty string when the model is empty.
   */
  @Test
  public void consoleContDisplaysEmpty() {
    this.consView = new ConsoleViewImpl(new MusicEditorModel(), this.sb);
    this.consCont = new IViewController(this.consView);
    this.consCont.display();
    assertEquals(this.sb.toString(), "");
  }

  /**
   * Test that the console controller displays mary-little-lamb.txt.
   */
  @Test
  public void consoleDisplaysMary() {
    this.consCont.display();
    assertEquals(this.sb.toString(),
        "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 \n"
            + " 0                 X                                            X                 \n"
            + " 1                 |                                            |                 \n"
            + " 2                 |                                  X                           \n"
            + " 3                 |                                  |                           \n"
            + " 4                 |                        X                                     \n"
            + " 5                 |                        |                                     \n"
            + " 6                 |                                  X                           \n"
            + " 7                                                    |                           \n"
            + " 8                 X                                            X                 \n"
            + " 9                 |                                            |                 \n"
            + "10                 |                                            X                 \n"
            + "11                 |                                            |                 \n"
            + "12                 |                                            X                 \n"
            + "13                 |                                            |                 \n"
            + "14                 |                                            |                 \n"
            + "15                                                                                \n"
            + "16                 X                                  X                           \n"
            + "17                 |                                  |                           \n"
            + "18                 |                                  X                           \n"
            + "19                 |                                  |                           \n"
            + "20                 |                                  X                           \n"
            + "21                 |                                  |                           \n"
            + "22                 |                                  |                           \n"
            + "23                 |                                  |                           \n"
            + "24                 X                                            X                 \n"
            + "25                 |                                            |                 \n"
            + "26                                                                             X  \n"
            + "27                                                                             |  \n"
            + "28                                                                             X  \n"
            + "29                                                                             |  \n"
            + "30                                                                             |  \n"
            + "31                                                                             |  \n"
            + "32                 X                                            X                 \n"
            + "33                 |                                            |                 \n"
            + "34                 |                                  X                           \n"
            + "35                 |                                  |                           \n"
            + "36                 |                        X                                     \n"
            + "37                 |                        |                                     \n"
            + "38                 |                                  X                           \n"
            + "39                 |                                  |                           \n"
            + "40                 X                                            X                 \n"
            + "41                 |                                            |                 \n"
            + "42                 |                                            X                 \n"
            + "43                 |                                            |                 \n"
            + "44                 |                                            X                 \n"
            + "45                 |                                            |                 \n"
            + "46                 |                                            X                 \n"
            + "47                 |                                            |                 \n"
            + "48                 X                                  X                           \n"
            + "49                 |                                  |                           \n"
            + "50                 |                                  X                           \n"
            + "51                 |                                  |                           \n"
            + "52                 |                                            X                 \n"
            + "53                 |                                            |                 \n"
            + "54                 |                                  X                           \n"
            + "55                 |                                  |                           \n"
            + "56  X                                       X                                     \n"
            + "57  |                                       |                                     \n"
            + "58  |                                       |                                     \n"
            + "59  |                                       |                                     \n"
            + "60  |                                       |                                     \n"
            + "61  |                                       |                                     \n"
            + "62  |                                       |                                     \n"
            + "63  |                                       |                       "
            + "              \n");
  }

  /**
   * Test that displaying an empty midi will not render anything from
   * the midi view.
   */
  @Test
  public void midiDisplayWorksEmpty() {
    IMusicEditorOperations<INote> modelE = new MusicEditorModel();
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(modelE);
    } catch (MidiUnavailableException e) {
      // Do nothing.
    }
    IViewController cont = new IViewController(mock);
    List<INote> notes = mock.displayTrack();
    assertEquals(notes.size(), 0);
    cont.display();
    notes = mock.displayTrack();
    List<INote> modelNotes = modelE.getNotes();
    assertEquals(modelNotes.size(), notes.size());
    assertEquals(modelNotes.size(), 0);
  }

  /**
   * Test that displaying the midi through the controller will call display on midi and
   * send the notes to be played by the midi view.
   */
  @Test
  public void midiDisplayWorksMary() {
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      // Do nothing.
    }
    List<INote> notes = mock.displayTrack();
    List<INote> modelNotes = model.getNotes();
    assertEquals(modelNotes.size(), notes.size());
    for (INote n : notes) {
      assertTrue(modelNotes.contains(n));
    }
    IViewController cont = new IViewController(mock);
    cont.display();
    notes = mock.displayTrack();
    assertEquals(0, notes.size());
  }

  /**
   * Gui display test move left and right.
   */
  @Test
  public void guiMoveLeftRight() {
    GuiController guiCont = new GuiController(this.model, this.guiView);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_RIGHT);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 1);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 2);
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_LEFT);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), 1);
  }

  /**
   * Gui display test move at position 0 throws no errors.
   */
  @Test
  public void guiLeftWhileAt0Works() {
    GuiController guiCont = new GuiController(this.model, this.guiView);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_LEFT);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 0);
  }

  /**
   * Test that hitting the home and end buttons will shift the position to the beginning and end
   * of the piece.
   */
  @Test
  public void guiHomeButtonAdjustsBeatPosition() {
    GuiController guiCont = new GuiController(this.model, this.guiView);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_END);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_HOME);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), 0);
  }

  /**
   * Test that hitting right at the end of the piece throws no errors.
   */
  @Test
  public void guiRightAtEndOfPiece() {
    GuiController guiCont = new GuiController(this.model, this.guiView);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_END);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_RIGHT);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
  }

  /**
   * Test that right and left work on Composite view.
   */
  @Test
  public void compositeRightLeftWorks() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    assertEquals(this.compView.getBeatPosition(), 0);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_RIGHT);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 1);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 2);
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_LEFT);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), 1);
  }

  /**
   * Gui display test move at position 0 throws no errors.
   */
  @Test
  public void compositeLeftWhileAt0Works() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    assertEquals(this.compView.getBeatPosition(), 0);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_LEFT);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), 0);
  }

  /**
   * Test that hitting the home and end buttons will shift the position to the beginning and end
   * of the piece.
   */
  @Test
  public void compositeHomeButtonAdjustsBeatPosition() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    assertEquals(this.compView.getBeatPosition(), 0);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_END);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_HOME);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), 0);
  }

  /**
   * Test that hitting right at the end of the piece throws no errors.
   */
  @Test
  public void compositeRightAtEndOfPiece() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    assertEquals(this.compView.getBeatPosition(), 0);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_END);
    kl.keyPressed(p);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
    Button b2 = new Button("");
    KeyEvent p2 = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_RIGHT);
    kl.keyPressed(p2);
    assertEquals(this.guiView.getBeatPosition(), this.model.getLength());
  }

  /**
   * Test that hitting space on the composite piece starts a timer and progresses the piece forward,
   * and that hitting space again will stop the progression.
   */
  @Test
  public void compositeSpaceWorksMidiDrivesView() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    assertEquals(this.compView.getBeatPosition(), 0);
    assertEquals(this.guiView.getBeatPosition(), 0);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_SPACE);
    kl.keyPressed(p);
    try {
      Thread.sleep(500);
    } catch (Exception ex) {
      // Do nothing.
    }
    assertEquals(this.guiView.getBeatPosition(), 2);
    kl.keyPressed(p);
    try {
      Thread.sleep(500);
    } catch (Exception ex) {
      // Do nothing.
    }
    assertEquals(this.guiView.getBeatPosition(), 2);
  }

  /**
   * Test that displaying the midi through the composite controller will call display on midi and
   * send the notes to be played by the midi view.
   */
  @Test
  public void compositeSpaceStartsMidi() {
    MockMidiViewImpl mock = null;
    try {
      mock = new MockMidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      // Do nothing.
    }
    List<INote> notes = mock.displayTrack();
    List<INote> modelNotes = model.getNotes();
    assertEquals(modelNotes.size(), notes.size());
    for (INote n : notes) {
      assertTrue(modelNotes.contains(n));
    }
    CompositeViewImpl comp = new CompositeViewImpl(mock, this.guiView);
    CompositeController compCont = new CompositeController(this.model, comp);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b = new Button("");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_SPACE);
    kl.keyPressed(p);
    notes = mock.displayTrack();
    assertEquals(0, notes.size());
  }

  /**
   * Test click note functionality on Gui controller.
   */
  @Test
  public void guiClickAddsNote() {
    GuiController guiCont = new GuiController(this.model, this.guiView);
    MouseListener[] mouse = this.guiView.getMouseListeners();
    assertEquals(mouse.length, 1);
    MouseListener ml = mouse[0];
    assertEquals(this.model.getAllNotesAtBeat(0).size(), 2);
    assertEquals(this.model.getNotes().size(), 34);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 700, 275, 5, true, MouseEvent.BUTTON1);
    ml.mouseClicked(m);
    assertEquals(this.model.getNotes().size(), 35);
    assertEquals(this.model.getAllNotesAtBeat(0).size(), 3);
    assertTrue(this.model.getNotes().contains(
        INote.builder().setStartBeat(0).setInstrumentID(5).setVolume(60).setDuration(1)
            .setPitch(new Pitch(5, Key.A)).build()));
  }

  /**
   * Test click note functionality on Composite controller.
   */
  @Test
  public void compositeClickAddsNote() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    MouseListener[] mouse = this.guiView.getMouseListeners();
    assertEquals(mouse.length, 1);
    MouseListener ml = mouse[0];
    assertEquals(this.model.getAllNotesAtBeat(0).size(), 2);
    assertEquals(this.model.getNotes().size(), 34);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 700, 275, 5, true, MouseEvent.BUTTON1);
    ml.mouseClicked(m);
    assertEquals(this.model.getNotes().size(), 35);
    assertEquals(this.model.getAllNotesAtBeat(0).size(), 3);
    assertTrue(this.model.getNotes().contains(
        INote.builder().setStartBeat(0).setInstrumentID(5).setVolume(60).setDuration(1)
            .setPitch(new Pitch(5, Key.A)).build()));
  }

  /**
   * Test that click note functionality on Composite controller only works when paused.
   */
  @Test
  public void compositeClickDoesntAddNoteWhileRunning() {
    CompositeController compCont = new CompositeController(this.model, this.compView);
    MouseListener[] mouse = this.guiView.getMouseListeners();
    assertEquals(mouse.length, 1);
    MouseListener ml = mouse[0];
    assertEquals(this.model.getNotes().size(), 34);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 700, 275, 5, true, MouseEvent.BUTTON1);
    KeyListener[] key = this.guiView.getKeyListeners();
    assertEquals(key.length, 1);
    KeyListener kl = key[0];
    Button b2 = new Button("");
    KeyEvent p = new KeyEvent(b2, 1, 2, 3, KeyEvent.VK_SPACE);
    kl.keyPressed(p);
    ml.mouseClicked(m);
    kl.keyPressed(p);
    assertEquals(this.model.getNotes().size(), 34);
  }

}
