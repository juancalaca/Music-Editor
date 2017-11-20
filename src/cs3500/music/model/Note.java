package cs3500.music.model;

/**
 * Concrete implementation of {@code INote} interface, providing necessary functionality specified
 * by interface.
 *
 * <p>Class Invariants:</p> <p><t>A {@code Note}'s volume, start beat, octave, and instrument id are
 * strictly greater than or equal to 0.</t></p>
 *
 * @see Pitch
 */
public class Note implements INote {

  /**
   * An enum was chosen to represent a {@code Note}'s pitch since it limited the valid values, and
   * provided additional functionality.
   */
  private final IPitch pitch;
  private final int startBeat;
  private final int duration;
  private final int volume;
  private final int instrumentID;

  /**
   * Private constructor in order for clients to strictly use the builder to create a {@code Note}
   * object.
   *
   * @param builder {@code NoteBuilder} used to construct the particular instance of this {@code
   * Note}
   */
  private Note(NoteBuilder builder) {
    this.pitch = builder.pitch;
    this.startBeat = builder.startBeat;
    this.duration = builder.duration;
    this.volume = builder.volume;
    this.instrumentID = builder.instrumentID;
  }

  public static final class NoteBuilder {

    /**
     * Default values as specified by {@code INote} interface.
     */
    private IPitch pitch = new Pitch(4, Key.C);
    private int startBeat = 0;
    private int duration = 0;
    private int volume = 0;
    private int instrumentID = 0;

    /**
     * Sets the pitch of the note.
     *
     * @param pitch {@code Pitch} enum value
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setPitch(IPitch pitch) {
      this.pitch = pitch;
      return this;
    }

    /**
     * Sets the octave of the note.
     *
     * @param octave the octave (positive)
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setOctave(int octave) {
      this.pitch = new Pitch(octave, this.pitch.getKey());
      return this;
    }

    public NoteBuilder setKey(Key key) {
      this.pitch = new Pitch(this.pitch.getOctave(), key);
      return this;
    }

    /**
     * Sets the start beat of the note.
     *
     * @param startBeat the starting beat (positive)
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setStartBeat(int startBeat) {
      this.startBeat = startBeat;
      return this;
    }

    /**
     * Sets the duration of the note.
     *
     * @param duration the duration of the note (greater than zero)
     * @return {@code} this, for method chaining
     */
    public NoteBuilder setDuration(int duration) {
      this.duration = duration;
      return this;
    }

    /**
     * Sets the volume of the note.
     *
     * @param volume the volume (positive)
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setVolume(int volume) {
      this.volume = volume;
      return this;
    }

    /**
     * Sets the instrument id of the note.
     *
     * @param id the instrument id
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setInstrumentID(int id) {
      this.instrumentID = id;
      return this;
    }

    /**
     * Constructs a note from another note.
     *
     * @param aNote {@code INote} to be copied
     * @return {@code this}, for method chaining
     */
    public NoteBuilder fromNote(INote aNote) {
      this.pitch = aNote.getPitch();
      this.startBeat = aNote.getStartBeat();
      this.duration = aNote.getDuration();
      this.volume = aNote.getVolume();
      this.instrumentID = aNote.getInstrumentID();
      return this;
    }

    /**
     * Convenience method to set four most important attributes of note.
     *
     * @param key {@code Key} of note
     * @param octave the octave of note (positive)
     * @param startBeat the starting beat of note (positive)
     * @param duration the duration of the note (greater than zero)
     * @return {@code this}, for method chaining
     */
    public NoteBuilder setPchOctStrDur(Key key, int octave, int startBeat, int duration) {
      this.pitch = new Pitch(octave, key);
      this.startBeat = startBeat;
      this.duration = duration;
      return this;
    }

    /**
     * Builds and returns the specified {@code Note}, by first checking all values for validity.
     *
     * @return the new {@code Note}
     */
    public INote build() {
      checkValue(0, this.duration, this.startBeat, this.volume, this.instrumentID);
      return new Note(this);
    }

    /**
     * Helper method to determine if any value is outside of allowed lower-limit.
     *
     * @param limit smallest value, the {@code values} can have
     * @param values values to check
     * @throws IllegalArgumentException if any value is
     */
    private void checkValue(int limit, int... values) throws IllegalArgumentException {
      for (int v : values) {
        if (v < limit) {
          throw new IllegalArgumentException(String.format("Value can't be less than %d", limit));
        }
      }
    }
  }

  @Override
  public String toString() {
    return this.pitch.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }
    Note other = (Note) o;
    return this.pitch.equals(other.pitch) && this.startBeat == other.startBeat
        && this.duration == other.duration && this.instrumentID == other.instrumentID
        && this.volume == other.volume;
  }

  @Override
  public int hashCode() {
    return this.pitch.hashCode();
  }

  @Override
  public IPitch getPitch() {
    return this.pitch;
  }

  @Override
  public int getOctave() {
    return this.pitch.getOctave();
  }

  @Override
  public Key getKey() {
    return this.pitch.getKey();
  }

  @Override
  public int getStartBeat() {
    return this.startBeat;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }

  @Override
  public int getInstrumentID() {
    return this.instrumentID;
  }

  @Override
  public int getEndBeat() {
    if (this.duration == 0) {
      return this.startBeat;
    }
    return this.startBeat + this.duration - 1;
  }

  @Override
  public NoteStatus getStatusAtBeat(int beat) {
    if (beat == this.startBeat) {
      return NoteStatus.HEAD;
    }
    if (beat > this.startBeat && beat <= this.getEndBeat()) {
      return NoteStatus.SUSTAIN;
    }
    return NoteStatus.REST;
  }
}