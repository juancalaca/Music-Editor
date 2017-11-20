package cs3500.music.view;

import cs3500.music.model.IPitch;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.sound.midi.MidiUnavailableException;

/**
 * This class implements the {@code IGuiView} interface, with additional methods to provide needed
 * functionality to harmonize the audible and visual view for the model. This class delegates to
 * objects of {@code MidiViewImpl} and {@code GuiViewImpl} to synchronize the sound and visual
 * views. This class serves as a single point of control for the views. This class is controlled by
 * the {@code CompositeController} which takes advantage of the delegation in order to unify the
 * visual and audible views.
 *
 * @see IGuiView
 * @see MidiViewImpl
 * @see GuiViewImpl
 * @see cs3500.music.controller.CompositeController
 */
public class CompositeViewImpl implements IGuiView {

  /**
   * View delegates that make up the {@code CompositeView}.
   */
  private final MidiViewImpl midi;
  private final IGuiView gui;

  /**
   * {@code String} for common MIDI errors.
   */
  private final String errorMidi = "MIDI not initialized correctly";

  /**
   * Constructs a {@code CompositeViewImpl} that uses {@code MidiViewImpl} and {@code IGuiView} as
   * delegates to make an interactive GUI view with visual and audible representations of the model.
   * Configures MIDI playback to properly initialize the audible view.
   *
   * @param midi {@code MidiViewImpl} to be used as {@code this} audible view
   * @param gui concrete implementation of {@code IGuiView} to serve as {@code this} visual view
   */
  public CompositeViewImpl(MidiViewImpl midi, IGuiView gui) {
    this.midi = midi;
    this.gui = gui;

    try {
      this.midi.initializeMidiPlayback();
    } catch (MidiUnavailableException e) {
      throw new IllegalArgumentException("Invalid MIDI Playback Engine");
    }
  }

  @Override
  public void display() throws IOException, MidiUnavailableException {

    gui.display();
  }

  @Override
  public void addKeyListener(KeyListener listener) {

    gui.addKeyListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {

    gui.addMouseListener(listener);
  }

  @Override
  public void shiftView(int position) {

    gui.shiftView(position);
  }

  @Override
  public IPitch clickNote(MouseEvent e) throws IllegalArgumentException {

    return this.gui.clickNote(e);
  }

  @Override
  public int getBeatPosition() {

    return midi.getBeatPosition();
  }

  /**
   * Starts playing contents of {@code mid} at desired beat, delegating to the audible view in order
   * to provide playback.
   *
   * @param beat position at which to start playing contents of {@code midi}
   */
  public void startPlayingAt(int beat) {

    try {
      midi.constructTrack();
      midi.startPlayingAt(beat);
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException(errorMidi);
    }
  }

  /**
   * Stops {@code midi} playback, important to note that stopping the playback does not necessitate
   * to initialize MIDI playback again.
   */
  public void stop() {

    try {
      midi.stop();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException(errorMidi);
    }
  }
}
