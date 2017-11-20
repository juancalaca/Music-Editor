package cs3500.music.model;

/**
 * Allowed statuses for a note in a piece, where {@code Head} is the attack portion of the note,
 * {@code Sustain} is the duration beat of the note (usually after a {@code Head}), and {@code
 * Rest} is when the note is not playing at all.
 */
public enum NoteStatus {
  HEAD("X"), SUSTAIN("|"), REST(" ");

  private final String textRep;

  /**
   * Constructs initializes valid note statuses with textual representation.
   *
   * @param textRep {@code String} representation of {@code NoteStatus}
   */
  NoteStatus(String textRep) {
    this.textRep = textRep;
  }

  @Override
  public String toString() {
    return textRep;
  }
}