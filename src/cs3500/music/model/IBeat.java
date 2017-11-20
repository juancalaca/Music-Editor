package cs3500.music.model;

import java.util.List;

/**
 * This interface represents a beat in a piece of music. Each beat has the ability to add or remove
 * notes, get the highest and lowest pitch, and access the notes in the beat. The beat will store
 * all notes passed to it regardless of whether they are either a head or sustaining at that beat,
 * there is no error checking of the notes themselves. Notes that are added will preserve
 * chronological ordering. The method {@code getTopNotes} will return only the last added notes
 * added of each {@code IPitch}, enforcing this paradigm. {@code IBeat} depends on an implementation
 * of {@code INote}.
 *
 * @see INote
 * @see IPitch
 */
public interface IBeat {

  /**
   * Add the {@code INote} to {@code this} beat. This function will always add the note, regardless
   * of its pitch or duration.
   *
   * @param note to be added
   */
  void add(INote note);

  /**
   * Remove the {@code INote} from the beat. Only removes the note if the beat contains the note
   * and the note is equal to the INote passed in.
   *
   * @param note to be removed
   * @return true if removed, false otherwise
   */
  Boolean remove(INote note);

  /**
   * Returns a list of all {@code INote}s in the beat regardless of pitch. The returned list will be
   * ordered from lowest IPitch to Highest.
   *
   * @return List of the {@code INote}s
   */
  List<INote> getAllNotes();

  /**
   * Returns a list of all of the last added {@code INote}s at each {@code IPitch}. The returned
   * list will be ordered based on {@code IPitch} from lowest to Highest.
   *
   * @return List of the {@code INote}s
   */
  List<INote> getTopNotes();


  /**
   * Gets the maximum {@code IPitch} in {@code this}.
   *
   * @return max pitch
   */
  IPitch maxPitch();

  /**
   * Gets the minimum {@code IPitch} in the {@code this}.
   *
   * @return min pitch
   */
  IPitch minPitch();

}
