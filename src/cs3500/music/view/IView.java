package cs3500.music.view;

import java.io.IOException;
import javax.sound.midi.MidiUnavailableException;

/**
 * This is an interface for the views that is kept fairly general to allow for a variety of view
 * types. It contains a single function {@code display} which will display appropriate
 * representation for the model based on the view. Every view that implements this interface should
 * provide a way to represent the notes in the piece, at their specified start, with their correct
 * duration. There must be some way in each view to handle overlapping notes. This is an
 * implementation detail that is left to the implementor, but the paradigm used must be clearly
 * documented and explained. It is important for all concrete implementations to display as much
 * information possible gathered from the contents of the model in a logical and user-friendly way.
 */
public interface IView {

  /**
   * Display the appropriate representation of the model based on the respective view type. This
   * representation should provide information about the piece and the notes contained within. Clear
   * documentation about what this display shows must be provided to the user so that the output is
   * note misinterpreted. It should be clear what happens when the are overlapping notes, notes
   * outside of the view, and how the display should be interpreted.
   *
   * @throws IOException              if the implementation requires IO and cannot access IO
   * @throws MidiUnavailableException if the implementation requires MIDI and cannot access MIDI
   */
  void display() throws IOException, MidiUnavailableException;
}