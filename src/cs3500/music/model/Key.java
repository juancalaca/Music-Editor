package cs3500.music.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Pitch enum representing the english note system (A-GSHARP). This enum is iterable, and overrides
 * hasNext() / next(). This is used in the model in rendering.
 */
public enum Key implements Iterator {
  C("C"), CSHARP("C#"), D("D"), DSHARP("D#"), E("E"), F("F"), FSHARP("F#"), G("G"), GSHARP("G#"), A(
      "A"), ASHARP("A#"), B("B");

  private final String pitch;

  /**
   * Constructor assigns pitch.
   */
  private Key(String pitch) {
    this.pitch = pitch;
  }

  /**
   * Gets a string representation.
   *
   * @return string representation.
   */
  public String toString() {
    return this.pitch;
  }

  @Override
  public boolean hasNext() {
    return true;
  }

  /**
   * Uses pitch ordering, circling from B back to C again.
   *
   * @return next Pitch
   */
  @Override
  public Key next() {
    Key[] pitches = Key.values();
    for (int i = 0; i < pitches.length; i++) {
      if (this.equals(pitches[i]) && !this.pitch.equals("B")) {
        return pitches[i + 1];
      }
    }
    return Key.C;
  }

  /**
   * Used to compare two pitches together in pitch ordering.
   *
   * @param other pitch to compare against.
   * @return - number if this is less, 0 if equal, + number if greater.
   */
  public int compare(Key other) {
    Objects.requireNonNull(other);
    String thisString = this.adjustString(this.pitch);
    String otherString = this.adjustString(other.toString());
    return thisString.compareTo(otherString);
  }

  /**
   * Helper method for compare that defines pitch ordering for comparisons. Because each scale
   * goes from C to B, the values must be adjusted as string comparisons cannot be directly used.
   *
   * @param pitch to be compared to {@code this}
   * @return the adjusted pitch
   */
  private String adjustString(String pitch) {
    if (pitch.equals("A") || pitch.equals("A#") || pitch.equals("B")) {
      char increment = pitch.charAt(0);
      increment += 7;
      pitch = Character.toString(increment) + pitch.substring(1);
    }
    return pitch;
  }

  /**
   * Gets the key from a given value between 0 and 12.
   *
   * @param ordinal of the desired key
   * @return the key
   */
  public static Key getKey(int ordinal) throws IllegalArgumentException {
    if (ordinal < 0 || ordinal > 11) {
      throw new IllegalArgumentException("Ordinal must be between 0 and 12");
    }
    Map<Integer, Key> m = new HashMap<>();
    for (Key k : Key.values()) {
      m.put(k.ordinal(), k);
    }
    return m.get(ordinal);
  }
}
