package cs3500.music.tests;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.Pitch;
import cs3500.music.view.MidiViewImpl;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * This class's purpose is to test the {@code MidiViewImpl} class. This mock works by extending the
 * desired class to be tested and uses the parent class functionality as a base, where the mock has
 * methods to make sense of the output of the parent class and confirm its functionality. We decided
 * for the Mock to create a list of {@code INote}s from the messages stored in the track. This
 * mock is stateless, and entirely defined by its parent class. The only purpose of this mock is to
 * construct notes from the sequences created by the parent class, allowing us to create more
 * comprehensible tests.
 */
public class MockMidiViewImpl extends MidiViewImpl {

  /**
   * Mock constructor creates an instance of the mock and delegates all the required functionality
   * to the {@code MidiViewImpl} parent. This allows for testing its functionality.
   *
   * @param model {@code IMusicEditorOperations} concrete implementation to base MIDI on
   * @throws MidiUnavailableException if there is a problem initializing playback
   */
  public MockMidiViewImpl(IMusicEditorOperations<INote> model)
      throws MidiUnavailableException {
    super(model);
    super.initializeMidiPlayback();
    super.constructTrack();
  }

  /**
   * This method construct notes from MidiEvent messages placed on the tracks from the sequence
   * created in the parent class.
   *
   * <p>Overview of its basic operations:</p> <t>Method iterates through all the tracks in the array
   * of tracks.</t> <t>We then iterate through all the messages in that track.</t> <t>We are
   * interested in mapping every NOTE_ON message to its according NOTE_OFF and Instrument ID, thus
   * searching for the NOTE_OFF relevant to that note and calculating the duration.</t><t>This
   * method allows us to test that all the notes are actually in the model as specified by the
   * messages, as well as comparing them to the provided files to test they describe the same
   * notes/piece of music.</t>
   *
   * @return {@code List} that contains all the notes in the piece
   */
  public List<INote> displayTrack() {
    List<INote> notes = new ArrayList<>();
    Track[] t = super.getSeq().getTracks();

    for (int i = 0; i < t.length; ++i) {

      for (int j = 0; j < t[i].size(); ++j) {

        if (t[i].get(j).getMessage() instanceof ShortMessage) {

          long tick = t[i].get(j).getTick();
          ShortMessage s = (ShortMessage) t[i].get(j).getMessage();
          int duration = 0;
          int instrumentID = -1;
          int key = s.getData1() % 12;
          int octave = ((s.getData1() - key) / 12) - 1;
          if (s.getCommand() == ShortMessage.NOTE_ON) {

            long instrumentTick = 0;
            int instrumentIndex = 0;
            for (int x = j + 1; x < t[i].size(); ++x) {

              if (t[i].get(x).getMessage() instanceof ShortMessage) {
                ShortMessage after = (ShortMessage) t[i].get(x).getMessage();
                instrumentTick = t[i].get(x).getTick();
                if (after.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                  if (instrumentTick == tick) {
                    instrumentID = after.getData1();
                    instrumentIndex = x;
                    x = t[i].size();
                  }
                }
              }
            }

            for (int x = instrumentIndex; x < t[i].size(); ++x) {
              if (t[i].get(x).getMessage() instanceof ShortMessage) {
                ShortMessage after = (ShortMessage) t[i].get(x).getMessage();
                long endTick = t[i].get(x).getTick();
                if (after.getCommand() == ShortMessage.NOTE_OFF) {
                  if (after.getData1() == s.getData1() && after.getData2() == s.getData2()) {
                    duration = (int) (endTick - tick);
                    x = t[i].size();
                  }
                }
              }
            }

            INote n = INote.builder().setPitch(new Pitch(octave, Key.getKey(key)))
                .setStartBeat((int) tick).setDuration(duration).setInstrumentID(instrumentID)
                .setVolume(s.getData2()).build();

            notes.add(n);
          }
        }
      }
    }
    return notes;
  }

}