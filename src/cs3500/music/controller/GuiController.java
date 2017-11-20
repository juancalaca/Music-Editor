package cs3500.music.controller;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import cs3500.music.view.IGuiView;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class is a controller for {@code IGuiView}s, that supports key presses and mouse clicks.
 * This controller allows for left and right movement through the piece with the left and right
 * arrow keys, a jump to the end of a song with the end key, a jump to the beginning of the song
 * with the home key, and adding a note by clicking on a key on the piano at the current beat.
 * This class contains several protected fields allowing for the expansion or alteration of the key
 * and mouse mappings in subclasses.
 *
 * @see IGuiView
 */
public class GuiController implements IController {

  /**
   * Private and protected fields.
   */
  private IMusicEditorOperations model;
  private IGuiView view;
  protected int beatNum = 0;
  protected Map<Integer, Runnable> keyPresses = new HashMap<>();
  protected Map<Integer, Consumer<MouseEvent>> mousePresses = new HashMap<>();

  /**
   * This constructor initializes the local fields and initializes the key and mouse mappings.
   *
   * @param m    model to store and update data
   * @param view to display information about the model
   */
  public GuiController(IMusicEditorOperations m, IGuiView view) {
    this.model = m;
    this.view = view;
    this.keyPresses = new HashMap<>();
    this.mousePresses = new HashMap<>();
    this.configureKeyBoardListener();
    this.configureMouseListener();
  }

  /**
   * This function populates and assigns the key mappings. It adds functionality to left, right,
   * home, and end keys, then adds the key listener to the view.
   */
  private void configureKeyBoardListener() {
    keyPresses.put(KeyEvent.VK_RIGHT, () -> {
          beatNum++;
          normalizeBeatnum();
          view.shiftView(beatNum);
        }
    );
    keyPresses.put(KeyEvent.VK_LEFT, () -> {
          beatNum--;
          normalizeBeatnum();
          view.shiftView(beatNum);
        }
    );
    keyPresses.put(KeyEvent.VK_HOME, () -> {
      beatNum = 0;
      view.shiftView(beatNum);
    });
    keyPresses.put(KeyEvent.VK_END, () -> {
      beatNum = this.model.getLength();
      view.shiftView(beatNum);
    });

    KeyListenerImpl kbd = new KeyListenerImpl();
    kbd.setKeyPressedMap(keyPresses);

    view.addKeyListener(kbd);
  }

  /**
   * This function populates and assigns the mouse mappings. It adds functionality to mouse allowing
   * a click on the piano to add a note to the piece, then adds the key listener to the view.
   */
  private void configureMouseListener() {
    mousePresses.put(MouseEvent.BUTTON1, (MouseEvent e) -> {
          IPitch pitch = null;
          try {
            pitch = view.clickNote(e);
          } catch (Exception ex) {
            return;
          }
          if (pitch != null) {
            this.model.addNote(
                INote.builder().setPitch(pitch).setStartBeat(beatNum).setInstrumentID(5)
                    .setVolume(60).setDuration(1).build());
            this.beatNum++;
            this.normalizeBeatnum();
            this.view.shiftView(beatNum);
          }
        }
    );
    MouseListenerImpl ml = new MouseListenerImpl();
    ml.setMousePressedMap(mousePresses);
    view.addMouseListener(ml);
  }

  /**
   * This function is used to normalize the beat number so that left and right moves do not go
   * before or beyond what is displayed by the view. This will stop the left moves at 0, and the
   * right moves at the end on the piece.
   */
  protected void normalizeBeatnum() {
    if (beatNum > model.getLength()) {
      beatNum = model.getLength();
    } else if (beatNum < 0) {
      beatNum = 0;
    }
  }


  @Override
  public void display() {
    try {
      this.view.display();
    } catch (Exception e) {
      //do something
    }
  }
}