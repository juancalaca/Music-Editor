package cs3500.music.controller;

import cs3500.music.view.IView;
import java.io.IOException;
import javax.sound.midi.MidiUnavailableException;

/**
 * This class is a simple controller for {@code IView}s, that simply display the view provided.
 * Given the interface, exceptions are handled in a more specific sense to provide more detailed
 * information to the user.
 *
 * @see IView
 */
public class IViewController implements IController {

  /**
   * View to display and handle.
   */
  private final IView view;

  /**
   * Constructs an {@code IViewController} to display provided view.
   *
   * @param view to be displayed by controller
   */
  public IViewController(IView view) {
    this.view = view;
  }

  @Override
  public void display() {

    try {
      this.view.display();
    } catch (IOException e) {
      throw new IllegalArgumentException("Output for view not initialized properly.");
    } catch (MidiUnavailableException e) {
      throw new IllegalArgumentException("MIDI provided not initialized properly.");
    }
  }
}
