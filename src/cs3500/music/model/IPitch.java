package cs3500.music.model;

import java.util.Iterator;

/**
 * An interface representing a Pitch. The interface enforces Comparable and Iterator, has methods
 * toString and getMidiValue, and overrides hashCode and equals. An {@code IPitch} contains an
 * octave and a {@code Key}.
 *
 * @see Key
 */
public interface IPitch extends Comparable, Iterator<IPitch> {

  @Override
  int hashCode();

  @Override
  boolean equals(Object o);

  @Override
  String toString();

  /**
   * Getter for the octave field of {@code this}.
   *
   * @return octave
   */
  int getOctave();

  /**
   * Getter for the Key field of {@code this}.
   *
   * @return Key
   */
  Key getKey();

  /**
   * Getter for the midi value of {@code this} calculated based on its Key and octave.
   *
   * @return octave
   */
  int getMidiValue();
}
