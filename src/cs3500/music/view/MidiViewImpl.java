package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * This class constructs and plays a MIDI sequence, adhering to General MIDI Specifications. It
 * allows for the notes in a concrete implementation of {@code IMusicEditorOperations}, parametrized
 * over {@code INote}, to be arranged and played based on the characteristics of the {@code
 * IMusicEditorOperations} piece. It uses the {@code Sequencer} provided by Java's {@code
 * MidiSystem} to create a track and play it. This class throws {@code MidiUnavailableException}'s
 * to restrict playback if the object is not properly initialized, at any point.
 *
 * @see IMusicEditorOperations
 * @see INote
 * @see MidiSystem
 * @see Sequencer
 */
public class MidiViewImpl implements IView {

  /**
   * Midi objects driving the actual audio representation of the notes in the given {@code model}.
   */
  private Sequencer player;
  private Sequence seq;

  /**
   * Model used to construct a sequence of {@code MidiEvent}s describing it's contents.
   */
  private final IMusicEditorOperations model;

  /**
   * {@code String} for common sequencer error.
   */
  private final String errSqncr = "Sequencer is not open.";

  /**
   * Map that holds tracks according to the instruments of the current piece in the model.
   */
  private final Map<Integer, Track> instrumentToTrack;

  /**
   * Constructs a {@code MidiViewImpl} to be based on the provided model's content.
   *
   * @param model {@code IMusicEditorOperations} concrete implementation to base MIDI on
   */
  public MidiViewImpl(IMusicEditorOperations<INote> model) {
    Objects.requireNonNull(model);
    this.model = model;
    instrumentToTrack = new TreeMap<>();
  }

  @Override
  public void display() throws MidiUnavailableException {

    initializeMidiPlayback();

    constructTrack();

    startPlayingAt(0);
  }

  public int getBeatPosition() {
    return (int) player.getTickPosition();
  }

  /**
   * Initializes MIDI Playback engine by using the {@code Sequencer} setup. This method uses the
   * default {@code MidiSystem} to create and play tracks specialized and constructed by
   * {@code this}.
   *
   * @throws MidiUnavailableException if Sequence {@code seq} can not be initialized
   */
  public void initializeMidiPlayback() throws MidiUnavailableException {
    this.player = MidiSystem.getSequencer();
    this.player.open();

    try {
      this.seq = new Sequence(Sequence.PPQ, 1);
    } catch (InvalidMidiDataException e) {
      throw new MidiUnavailableException("Sequence could not be initialized");
    }

  }

  /**
   * Construct tracks according to the instrument id in order to preserve a track for each voice in
   * the piece of music provided by the {@code model}.
   */
  public void constructTrack() {
    List<INote> notes = this.model.getNotes();
    for (INote n : notes) {
      if (!instrumentToTrack.containsKey(n.getInstrumentID())) {
        instrumentToTrack.put(n.getInstrumentID(), seq.createTrack());
      }
      this.addNote(n, instrumentToTrack.get(n.getInstrumentID()));
    }
  }

  /**
   * Adds all the related messages of the {@code INote} to the specified {@code Track}.
   *
   * @param n {@code INote} to create messages of
   * @param tr {@code Track} to add messages to specified by {@code instrumentToTrack}
   */
  private void addNote(INote n, Track tr) {

    ShortMessage instr = new ShortMessage();
    ShortMessage noteOn = new ShortMessage();
    ShortMessage noteOff = new ShortMessage();

    try {
      instr.setMessage(ShortMessage.PROGRAM_CHANGE, 0, n.getInstrumentID(), 0);
      noteOn.setMessage(ShortMessage.NOTE_ON, 0, n.getPitch().getMidiValue(), n.getVolume());
      noteOff.setMessage(ShortMessage.NOTE_OFF, 0, n.getPitch().getMidiValue(), n.getVolume());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    MidiEvent noteOnEvent = new MidiEvent(noteOn, n.getStartBeat());
    MidiEvent noteOffEvent = new MidiEvent(noteOff, n.getEndBeat() + 1);
    MidiEvent changeInstrEvent = new MidiEvent(instr, n.getStartBeat());

    tr.add(noteOnEvent);
    tr.add(noteOffEvent);
    tr.add(changeInstrEvent);
  }


  /**
   * Starts playing the sequence at the desired beat, if beat greater than model length we return,
   * since we know this will throw an unchecked exception, and conflict with synching.
   *
   * @param beat tick or beat at which the {@code Sequence} should start playing at
   * @throws MidiUnavailableException if the {@code Sequencer} is null or not open
   */
  public void startPlayingAt(int beat) throws MidiUnavailableException {

    if (this.player == null || !this.player.isOpen() || this.player.isRunning()) {
      throw new MidiUnavailableException(errSqncr);
    }

    if (beat >= this.model.getLength()) {
      return;
    }

    try {
      this.player.setSequence(seq);
    } catch (InvalidMidiDataException e) {
      throw new MidiUnavailableException("Sequence could not be set in sequencer");
    }

    this.player.setTickPosition(beat);
    this.player.addMetaEventListener(new CreateTrackListener(player));

    this.player.start();
    this.player.setTempoInMPQ(model.getTempo());
  }

  /**
   * Nested inner class that implements {@code MetaEventListener} to control the track based on the
   * end-of-track {@code MetaMessage} sent when the whole sequence has been played. This listener
   * design, exits the program gracefully once the whole sequence has been played.
   */
  private class CreateTrackListener implements MetaEventListener {

    final Sequencer player;

    /**
     * Constructs listener by assigning {@coden Sequencer} which will be "listened" to.
     *
     * @param player {@code Sequencer} to keep track of
     */
    public CreateTrackListener(Sequencer player) {
      this.player = player;
    }

    @Override
    public void meta(MetaMessage meta) {
      if (this.player != null) {
        if (meta.getType() == 0x2F) {
          this.player.stop();
        }
      }
    }
  }

  /**
   * This method stops the track at any given moment when it is called, if and only if the {@code
   * player} is open and initialized. This method does not close the {@code player} allowing it to
   * be replayed at a certain position.
   *
   * @throws MidiUnavailableException if the {@code player} is not initialized, or is not open
   */
  public void stop() throws MidiUnavailableException {

    if (this.player == null || !this.player.isOpen()) {
      throw new MidiUnavailableException(errSqncr);
    }

    this.player.stop();
  }

  /**
   * Method used to mock {@code this} object with the purpose of testing. This has access modifier
   * protected for classes to extend the {@code MidiViewImpl} and test accordingly.
   */
  public Sequence getSeq() {
    return seq;
  }

}