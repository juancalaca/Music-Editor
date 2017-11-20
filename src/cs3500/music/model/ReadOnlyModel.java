package cs3500.music.model;

import java.util.List;

/**
 * Wrapper class for {@code IMusicEditorOperations<INote>} concrete implementations that throws
 * exceptions if it is attempted to mutate the {@code model} provided.
 *
 * @see IMusicEditorOperations
 * @see INote
 */
public class ReadOnlyModel implements IMusicEditorOperations<INote> {

  /**
   * Model to protect from mutation, but delegate for valid actions.
   */
  private final IMusicEditorOperations<INote> model;

  /**
   * Error message displayed if modification is attempted while on read-only mode.
   */
  private final String readOnly = "Read Only Model";

  public ReadOnlyModel(IMusicEditorOperations<INote> model) {
    this.model = model;
  }

  @Override
  public void addNote(INote note) {

    throw new IllegalArgumentException(readOnly);
  }

  @Override
  public Boolean removeNote(INote note) {

    throw new IllegalArgumentException(readOnly);

  }

  @Override
  public void combine(IMusicEditorOperations<INote> other) {

    throw new IllegalArgumentException(readOnly);

  }

  @Override
  public void combineSequentially(IMusicEditorOperations<INote> other) {

    throw new IllegalArgumentException(readOnly);

  }

  @Override
  public List<INote> getNotes() {
    return this.model.getNotes();
  }

  @Override
  public List<INote> getAllNotesAtBeat(int beat) throws IllegalArgumentException {
    return this.model.getAllNotesAtBeat(beat);
  }

  @Override
  public List<INote> getTopNotesAtBeat(int beat) throws IllegalArgumentException {
    return this.model.getTopNotesAtBeat(beat);
  }

  @Override
  public int getLength() {

    return this.model.getLength();

  }

  @Override
  public void setTempo(int tempo) throws IllegalArgumentException {

    throw new IllegalArgumentException(readOnly);

  }

  @Override
  public int getTempo() {
    return this.model.getTempo();
  }

  @Override
  public INote getLowest() throws IllegalArgumentException {
    return this.model.getLowest();
  }

  @Override
  public INote getHighest() throws IllegalArgumentException {
    return this.model.getHighest();
  }
}
