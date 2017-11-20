package cs3500.music.controller;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import cs3500.music.view.CompositeViewImpl;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

/**
 * This class is a controller for {@code CompositeViewImpl}s, that supports key presses and mouse
 * clicks for composite views. This controller allows for left and right movement through the piece
 * with the left and right arrow keys, a jump to the end of a song with the end key, a jump to the
 * beginning of the song with the home key, pause and play with the space bar, and adding a note
 * with by clicking on a key on the piano at the current beat only when the song is paused. This
 * class is declared final and extends {@code GuiController}, accessing several of its fields. Pause
 * and play is implemented such that Midi will drive the position of the composition. A timer
 * continuously checks the midi's position, and updates the gui accordingly.
 *
 * @see CompositeViewImpl
 * @see GuiController
 */
public final class CompositeController extends GuiController {

  private CompositeViewImpl view;
  private IMusicEditorOperations model;
  private Timer timer;

  /**
   * This constructor calls the super constructor and then initializes local fields and adjusts the
   * mappings.
   *
   * @param model to store and update data
   * @param view  to display the data from the model
   */
  public CompositeController(IMusicEditorOperations model,
      CompositeViewImpl view) {
    super(model, view);
    this.view = view;
    this.model = model;
    this.setTimer();
    this.configureCompositeMouseListener();
    this.configureCompositeKeyBoardListener();
  }

  /**
   * This method mutates the protected field keyPresses in the GuiController and adds functionality
   * for the space bar. There is no need to remap because the already added previous mapping is
   * just mutated with the new functionality.
   */
  private void configureCompositeKeyBoardListener() {
    keyPresses.put(KeyEvent.VK_SPACE, () -> {
      if (!timer.isRunning()) {
        view.startPlayingAt(beatNum);
        timer.start();
      } else {
        view.stop();
        timer.stop();
      }
    });
  }

  /**
   * This method changes the mapping for a left click with an added check to make sure that the
   * piece is paused. It does this by mutating the protected mousePresses in GuiController,
   * replacing the mapped value for a {@code BUTTON1} (left click). There is no need to add the
   * listener because it is already added to the view.
   */
  private void configureCompositeMouseListener() {
    mousePresses.put(MouseEvent.BUTTON1, (MouseEvent e) -> {
          if (this.timer.isRunning()) {
            return;
          }
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
  }

  /**
   * This method initializes the timer that is used to update the current position of the piece. The
   * timer will drive a continuous checking of the midi's position, and will then use that to shift
   * the gui representation of the piece. The timer checks at the highest granularity possible.
   */
  private void setTimer() {
    timer = new Timer(1, (ActionEvent e) -> {
      beatNum = view.getBeatPosition();
      view.shiftView(beatNum);
      if (this.beatNum == this.model.getLength()) {
        timer.stop();
      }
    });
  }

  @Override
  public void display() {
    super.display();
  }

}
