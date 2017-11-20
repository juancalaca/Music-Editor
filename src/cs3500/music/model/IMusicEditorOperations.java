package cs3500.music.model;

import java.util.List;

/**
 * This interface represents the basic functionality needed to write and edit a piece of music. The
 * basic unit for constructing a piece of music is a note. This interface is parametrized over
 * {@code K} which should be replaced in a concrete implementation of {@code IMusicModel}  with an
 * implementation of a note. A note can be described as having a pitch, a start beat, and a
 * duration. A note can have three statuses, head (starting beat), sustain (duration - start beat),
 * and rest (no note playing). A piece of music also has a length, and a measure. The length should
 * be trimmed to the beat of the last note, or note duration. The piece should be entirely defined
 * by the notes it contains. The implementor of this interface should be aware of what they return
 * for the user. When implementing something that returns a K, be sure to either return a copy of
 * this object, or make this object immutable.
 */
public interface IMusicEditorOperations<K> {

  /**
   * This method adds the {@code INote} in each beat that it is present. It should adjust the length
   * of the piece to fit the new note. This method should allow placing notes in beats with already
   * existing notes of same pitch, allowing both to be on the same beat.
   *
   * @param note note to add in piece
   */
  void addNote(K note);

  /**
   * Method removes note from piece, if the note is in the piece. This method should adjust the
   * length if the removed note changes it.
   *
   * @param note note to be removed from piece
   * @return true if it was removed, false otherwise
   */
  Boolean removeNote(K note);

  /**
   * This function combines two models together. This works by injecting all of the notes
   * from the other model into this model, while preserving the state of the other model for future
   * playing. This function makes use of the {@code getNotes} function to access the other model's
   * notes.
   *
   * @param other the other model to combine with.
   */
  void combine(IMusicEditorOperations<K> other);

  /**
   * This function combines two models together sequentially. This works by injecting all of the
   * notes from the other model into this model at the end of the current notes,
   * while preserving the state of the other model for future playing. This function makes use of
   * the {@code getNotes} function to access the other model's notes.
   *
   * @param other the other model to combine with
   */
  void combineSequentially(IMusicEditorOperations<K> other);

  /**
   * This function returns all of the notes in the model in the form of a list of a List of {@code
   * K}s. This contains all of the information about a model needed to combine two models together
   * or get information about the model without direct access to the model's data structure. It is
   * the responsibility of the implementor of the interface to return either copies or immutable
   * {@code K} objects.
   *
   * @return List of {@code K}
   */
  List<K> getNotes();

  /**
   * This function will return an ordered List of {@code K}s at a given beat in the piece.
   *
   * @param beat number
   * @return List of {@code K}s
   * @throws IllegalArgumentException if beat is outside of the range of beats in the model
   */
  List<K> getAllNotesAtBeat(int beat) throws IllegalArgumentException;

  /**
   * This function will get return an ordered List of {@code K}s the top notes added at a given beat
   * in the piece. This assumes that there is some sort of ordering of the notes at each beat. This
   * function is important for fast and proper display functionality.
   *
   * @param beat number
   * @return List of {@code K}s
   * @throws IllegalArgumentException if beat is outside of the range of beats in the model
   */
  List<K> getTopNotesAtBeat(int beat) throws IllegalArgumentException;

  /**
   * Gets the current length of the song. This length must be updated on each {@code addNote} and
   * {@code removeNote} call.
   *
   * @return length
   */
  int getLength();

  /**
   * Sets the tempo for the piece. The tempo can be used by any view that would represent the song.
   * The tempo should be in microseconds per quarter note.
   *
   * @param tempo to set
   * @throws IllegalArgumentException if the tempo is less than 0
   */
  void setTempo(int tempo) throws IllegalArgumentException;

  /**
   * Get the current tempo of the piece.
   *
   * @return tempo
   */
  int getTempo();

  /**
   * Gets the lowest {@code K} in the song. This will return a dummy {@code K} that is not actually
   * present in the song, but just provides information about the range of {@code K}s in the song.
   *
   * @return the lowest {@code K}
   * @throws IllegalArgumentException if model is empty
   */
  K getLowest() throws IllegalArgumentException;

  /**
   * Gets the highest {@code K} in the song. This will return a dummy {@code K} that is not actually
   * present in the song, but just provides information about the range of {@code K}s in the song.
   *
   * @return the highest {@code K}
   * @throws IllegalArgumentException if model is empty
   */
  K getHighest() throws IllegalArgumentException;
}
