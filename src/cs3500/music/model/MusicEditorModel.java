package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * * Class that implements the {@code
 * IMusicPlayerModel} interface by using an {@code ArrayList} to map the
 * notes to the beats of the piece. This implementation uses {@code INote}s to construct a piece of
 * music. This implementation follows the note precedence rule where the last note of the same pitch
 * added to the piece holds precedence over any note already there.
 *
 * <p>Invariants of class:</p> <p><t>{@code this} maps a given song by notes and their position in a
 * list of beats. </t></p> <p><t>{@code length} is determined by the length of the arraylist storing
 * the beats, and this length will always be the length of the actual note in the piece.</t></p>
 * <p><t>The index of a beat will always be its "beat number" in the song.</t></p>
 *
 * @see IMusicEditorOperations
 * @see INote
 * @see IPitch
 */

public class MusicEditorModel implements IMusicEditorOperations<INote> {

  private final List<IBeat> beats;
  private IPitch highest;
  private IPitch lowest;
  private int tempo;

  /**
   * This constructor sets the highest and lowest fields to null. Storing these fields is a
   * performance consideration, so that they are updated only when appropriate and every display
   * doesn't require searching the entire song.
   */
  public MusicEditorModel() {
    this.beats = new ArrayList<>();
    this.highest = null;
    this.lowest = null;
    this.tempo = 4;
  }

  /**
   * Constructor getting parameters from {@code Builder}.
   *
   * @param builder for the MusicEditorModel class
   */
  private MusicEditorModel(Builder builder) {
    this();
    for (INote n : builder.notesToAdd) {
      this.addNote(n);
    }
    this.tempo = builder.tempo;
  }

  @Override
  public void addNote(INote note) {
    Objects.requireNonNull(note);
    if (note.getDuration() == 0) {
      return;
    }
    if (note.getEndBeat() >= this.beats.size()) {
      this.growList(note.getEndBeat());
    }
    for (int i = note.getStartBeat(); i <= note.getEndBeat(); i++) {
      IBeat b = this.beats.get(i);
      b.add(note);
    }
    this.adjustModelAdd(note);
  }

  /**
   * This function is used to grow the size of the beats array. This is used when a {@code INote} is
   * added that extends longer than the current size of the song.
   *
   * @param desired final length.
   */
  private void growList(int desired) {
    if (desired < this.beats.size()) {
      return;
    }
    int prevSize = this.beats.size();
    for (int i = prevSize; i <= desired; i++) {
      this.beats.add(new Beat());
    }
  }

  /**
   * Adjusts the highest and lowest {@code IPitch} stored in the model based on adding the given
   * {@code INote}. This function exists for performance reasons. It prevents unnecessary global
   * searches for highest and lowest notes in the piece.
   *
   * @param note to adjust the model based on.
   */
  private void adjustModelAdd(INote note) {
    Objects.requireNonNull(note);
    if (note.getPitch().compareTo(this.highest) >= 0) {
      this.highest = note.getPitch();
    }
    if (note.getPitch().compareTo(this.lowest) <= 0 || this.lowest == null) {
      this.lowest = note.getPitch();
    }
  }

  @Override
  public Boolean removeNote(INote note) {
    Objects.requireNonNull(note);
    int beat = note.getStartBeat();
    if (beat >= this.beats.size()) {
      return false;
    }
    for (int i = beat; i <= note.getEndBeat(); i++) {
      IBeat b = this.beats.get(i);
      b.remove(note);
    }
    this.adjustModelRemove(note);
    return true;
  }

  /**
   * Adjusts the highest and lowest {@code IPitch} stored in the model based on removing the given
   * {@code INote}. This function exists for performance reasons. It prevents unnecessary global
   * searches for highest and lowest notes in the piece.
   *
   * @param note to adjust the model based on.
   */
  private void adjustModelRemove(INote note) {
    Objects.requireNonNull(note);
    if (this.highest.equals(note)) {
      this.resetHighest();
    }
    if (this.lowest.equals(note)) {
      this.resetLowest();
    }
    this.shrinkList();
  }

  /**
   * This function will resize the {@code ArrayList} based on the minimum length necessary to
   * represent all of the notes in the song.
   */
  private void shrinkList() {
    for (int i = this.beats.size() - 1; i >= 0; i--) {
      IBeat beat = this.beats.get(i);
      if (beat.getAllNotes().size() == 0) {
        this.beats.remove(i);
      }
    }
  }

  /**
   * This function is used to update the global max {@code IPitch} for the song. It searches through
   * each beat and gets the highest {@code IPitch}s to update itself.
   */
  private void resetHighest() {
    this.highest = null;
    List<IPitch> list = new ArrayList<>();
    for (IBeat b : this.beats) {
      try {
        list.add(b.maxPitch());
      } catch (Exception ex) {
        continue;
      }
    }
    if (list.size() != 0) {
      this.highest = Collections.max(list);
    }
  }

  /**
   * This function is used to update the global min {@code IPitch} for the song. It searches through
   * each beat and gets the lowest {@code IPitch}s to update itself.
   */
  private void resetLowest() {
    this.lowest = null;
    List<IPitch> list = new ArrayList<>();
    for (IBeat b : this.beats) {
      try {
        list.add(b.minPitch());
      } catch (Exception ex) {
        continue;
      }
    }
    if (list.size() != 0) {
      this.lowest = Collections.min(list);
    }
  }

  @Override
  public void combine(IMusicEditorOperations<INote> other) {
    this.combineHelp(0, other);
  }

  @Override
  public void combineSequentially(IMusicEditorOperations<INote> other) {
    this.combineHelp(this.getLength(), other);
  }

  /**
   * Helper function for combining. This function works by calling the other model's {@code
   * getNotes} function and then adding each note to this model. The start point specifies where to
   * begin adding the notes.
   *
   * @param startPoint Start point for adding the notes.
   * @param other Other model.
   */
  private void combineHelp(int startPoint, IMusicEditorOperations other) {
    Objects.requireNonNull(other);
    List<INote> otherList = other.getNotes();
    for (INote n : otherList) {
      this.addNote(INote.builder().fromNote(n).setStartBeat(n.getStartBeat() + startPoint).build());
    }
    this.resetLowest();
    this.resetHighest();
  }

  @Override
  public List<INote> getNotes() {
    List returnList = new ArrayList();
    for (int i = 0; i < this.beats.size(); i++) {
      List<INote> list = this.beats.get(i).getAllNotes();
      for (INote n : list) {
        if (n.getStatusAtBeat(i).equals(NoteStatus.HEAD)) {
          returnList.add(n);
        }
      }
    }
    return returnList;
  }

  @Override
  public List<INote> getAllNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat >= this.getLength() || beat < 0 || (beat == 0 && this.getLength() == 0)) {
      throw new IllegalArgumentException("Beat index out of bounds");
    }
    List<INote> list = this.beats.get(beat).getAllNotes();
    return list;
  }

  @Override
  public List<INote> getTopNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat >= this.getLength() || beat < 0 || (beat == 0 && this.getLength() == 0)) {
      throw new IllegalArgumentException("Beat index out of bounds");
    }
    List<INote> list = this.beats.get(beat).getTopNotes();
    return list;
  }

  @Override
  public int getLength() {
    return this.beats.size();
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setTempo(int tempo) throws IllegalArgumentException {
    if (tempo < 1) {
      throw new IllegalArgumentException("Tempo must be greater than 0!");
    }
    this.tempo = tempo;
  }

  @Override
  public INote getHighest() throws IllegalArgumentException {
    if (this.highest == null) {
      throw new IllegalArgumentException("Empty model, highest doesn't exist");
    }
    return INote.builder().setPitch(this.highest).build();
  }

  @Override
  public INote getLowest() throws IllegalArgumentException {
    if (this.lowest == null) {
      throw new IllegalArgumentException("Empty model, lowest doesn't exist");
    }
    return INote.builder().setPitch(this.lowest).build();
  }

  /**
   * This builder streamlines the process of creating a new model. It implements the {@code
   * CompositionBuilder} interface and provides setters for the tempo and adding new {@code
   * INote}s.
   */
  public static final class Builder implements CompositionBuilder<IMusicEditorOperations> {

    int tempo;
    List<INote> notesToAdd = new ArrayList<>();

    @Override
    public IMusicEditorOperations build() {
      return new MusicEditorModel(this);
    }

    @Override
    public CompositionBuilder<IMusicEditorOperations> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditorOperations> addNote(int start, int end, int instrument,
        int pitch, int volume) {
      if (start == end) {
        return this;
      }
      int duration = end - start;
      int key = pitch % Key.values().length;
      int octave = ((pitch - key) / Key.values().length) - 1;
      notesToAdd.add(
          INote.builder().setStartBeat(start).setDuration(duration).setInstrumentID(instrument)
              .setPitch(new Pitch(octave, Key.getKey(key))).setVolume(volume).build());
      return this;
    }
  }
}