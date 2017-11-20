package cs3500.music.view;

import cs3500.music.model.IPitch;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This interface extends {@code IView} providing more methods expected from a GUI view that will
 * ultimately represent the contents of a piece of music in a user-friendly and logical sense. This
 * is the basic framework expected from the view to allow the user to scroll through the contents of
 * the piece to be displaced. It is important to mention that the concrete implementation adheres to
 * the invariants of this class since other objects depend on the set functionality described in the
 * methods.
 */
public interface IGuiView extends IView {

  /**
   * Adds an object that implements {@code KeyListener} in order to manage how the content is
   * displayed to the user. This listener should allow to scroll through the contents of the piece
   * using the right and left key. Any additional functionality should make sense given the state of
   * the view.
   *
   * @param listener handles events and dictates how the view responds
   */
  void addKeyListener(KeyListener listener);

  /**
   * Adds a mouse event handler that dictates the actions given the triggered event. This listener
   * should modify how the view is managed.
   *
   * @param listener {@code MouseListener} that handles mouse events
   */
  void addMouseListener(MouseListener listener);

  /**
   * Shifts the view given the value of position. It is important that all views us this method in
   * unison. The expected behavior of this method is that the view will be shifted by {@code
   * position}, where this parameter describes the beat at which the view should be shifted to. Any
   * concrete implementation should keep this beat in view for the user in a comprehensive way.
   */
  void shiftView(int position);

  /**
   * Returns the {@code IPitch} associated to the position of the {@code MouseEvent}. This is a
   * method used for allowing different positioning and dimensions of the view, in exchange to
   * mapping the pitches to the position of the mouse.
   *
   * @param e {@code MouseEvent} to used to return associated pitch
   * @return {@code IPitch} relative to the position of the mouse and dimensions of view
   * @throws IllegalArgumentException if {@code MouseEvent} out of view bounds
   */
  IPitch clickNote(MouseEvent e) throws IllegalArgumentException;

  /**
   * Beat position can be described as the beat where the view is currennlty focusing on to display.
   *
   * @return beat in focus by view
   */
  int getBeatPosition();
}
