package cs3500.music.model;

import java.util.Objects;

/**
 * This is an immutable class that implements an {@code IPitch} object. It's fields are public and
 * final, meaning that it cannot change. Using a reference to this object directly is harmless
 * because it cannot be mutated. A pitch has an octave and a key.
 *
 * @see Key
 */
public final class Pitch implements IPitch {

  private final int octave;
  private final Key key;

  /**
   * Constructor initializes parameters to those passed in.
   *
   * @param octave of the Pitch.
   * @param key of the Pitch.
   * @throws IllegalArgumentException if octave is less than 0 or larger than 10
   */
  public Pitch(int octave, Key key) throws IllegalArgumentException {
    Objects.requireNonNull(key);
    if (octave < 0 || octave > 10) {
      throw new IllegalArgumentException("Octave must be between 0 and 10\n");
    }
    this.octave = octave;
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pitch)) {
      return false;
    }
    Pitch other = (Pitch) o;
    return this.octave == other.octave && (this.key.compare(other.key) == 0);
  }

  @Override
  public int hashCode() {
    return new Integer(this.octave).hashCode() + this.key.toString().hashCode();
  }

  @Override
  public int compareTo(Object o) {
    if (!(o instanceof Pitch)) {
      return 1;
    }
    Pitch other = (Pitch) o;
    if (this.octave > other.octave) {
      return 1;
    }
    if (this.octave < other.octave) {
      return -1;
    }
    return this.key.compare(other.key);
  }

  @Override
  public String toString() {
    return this.key.toString() + Integer.toString(this.octave);
  }

  @Override
  public int getOctave() {
    return this.octave;
  }

  @Override
  public Key getKey() {
    return this.key;
  }

  @Override
  public boolean hasNext() {
    return true;
  }

  @Override
  public Pitch next() {
    if (this.key.compare(Key.B) == 0) {
      return new Pitch(this.octave + 1, this.key.next());
    }
    return new Pitch(this.octave, this.key.next());
  }

  @Override
  public int getMidiValue() {
    return this.key.ordinal() + (12 * (this.octave + 1));
  }

}
