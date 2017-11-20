package cs3500.music.model;

import cs3500.music.model.Note.NoteBuilder;

/**
 * This interface represents the functionality expected from a note. This interface provides a
 * static {@code builder()} method expected from concrete implementations of this class. Given the
 * various attributes of a note, the {@code NoteBuilder} is a convenient way to create {@code INote}
 * s. Every concrete implementation of this interface must implement a method to construct an {@code
 * INote} from another {@code INote}. The getter methods of this interface infer the minimal
 * attributes expected from a concrete implementation of this interface. The pitch of a note is
 * defined in enum {@code Pitch}.
 *
 * @see Pitch
 */
public interface INote {

  /**
   * Constructs a builder for configuring and then creating an {@code INote}. Defaults to a middle
   * C, that is, a note of pitch C, in octave 4, at starting beat 0, with a duration of 0, a volume
   * of 0 and an instrumentID of 0.
   */
  static NoteBuilder builder() {
    return new NoteBuilder();
  }

  @Override
  String toString();

  @Override
  boolean equals(Object o);

  /**
   * Method that returns the pitch of the {@code INote}.
   *
   * @return {@code Pitch} of the note
   */
  IPitch getPitch();

  /**
   * Getter for the octave of a {@code INote}.
   *
   * @return octave
   */
  int getOctave();

  /**
   * Getter for the octave of a {@code INote}.
   *
   * @return octave
   */
  Key getKey();

  /**
   * Getter method that returns the start beat of the {@code INote}.
   *
   * @return start beat of note, strictly greater than or equal to zero
   */
  int getStartBeat();

  /**
   * Returns the duration of the {@code INote} in beats.
   *
   * @return duration of note in beats, strictly greater than 0
   */
  int getDuration();

  /**
   * Method that gets volume of note.
   *
   * @return volume of note, strictly positive
   */
  int getVolume();

  /**
   * Method that returns instrument id associated to the {@code INote}.
   *
   * @return instrument ID of note, strictly greater than or equal to zero
   */
  int getInstrumentID();

  /**
   * Returns the endBeat of the {@code INote}.
   *
   * @return endBeat of note in beats, strictly greater than 0
   */
  int getEndBeat();

  /**
   * Returns the status of a note at a given beat.
   *
   * @param beat to check status at
   * @return note status
   */
  NoteStatus getStatusAtBeat(int beat);

}
