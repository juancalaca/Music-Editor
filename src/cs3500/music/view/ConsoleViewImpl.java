package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import cs3500.music.model.NoteStatus;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * This view renders the entire model. If the model has no notes, an empty string is
 * returned. Otherwise, the rendering follows the following technical specification:
 *
 * <p>The output should consist of:</p> <p><t>A column of numbers representing the beats in the
 * piece, printed right-justified and padded with leading spaces, exactly as wide as
 * necessary.</t> <p><t>A sequence of columns, each five characters wide, representing each pitch.
 * The first line prints out the names of the pitches, centered within the five-character
 * column.</t></p> <p><t>The number of columns is determined by the range of notes starting from
 * the lowest note of the piece to the highest.</t></p> <p><t>A note head is rendered as a {@code
 * "  X  "}, a sustain as {@code "  |  "}, and rest as five spaces.</t></p> <p><t>Every line
 * should be exactly the same length, every item ending in a newline, including the last
 * one.</t></p> The pound sign {@code #} is used to represent sharps.<p><t></t></p>
 *
 * <p>When printing notes of the same pitch, the last note added has precedence over a
 * the note added previously. More specifically, if two note of the same pitch coincide either in
 * head/duration the note that was added last has precedence over which note is printed entirely
 * in text.</p>
 */
public class ConsoleViewImpl implements IView {

  /**
   * Objects used to construct and display the console rendering of the {@code model}.
   */
  private final IMusicEditorOperations<INote> model;
  private final Appendable ap;

  /**
   * Determines positioning of keys in terms of characters.
   */
  private final int NOTEDISPLAYWIDTH = 5;

  /**
   * Constructor initializes the local fields.
   *
   * @param model to display as specified
   * @param ap    appendable to which the view is outputted to
   */
  public ConsoleViewImpl(IMusicEditorOperations<INote> model, Appendable ap) {
    this.model = Objects.requireNonNull(model);
    this.ap = Objects.requireNonNull(ap);
  }

  @Override
  public void display() throws IOException {
    this.ap.append(this.getView());
  }

  /**
   * This function creates a {@code String} representation of the console view for the model. If the
   * model is empty, it will return an empty {@code String}.
   *
   * @return String representation of the model.
   */
  private String getView() {
    StringBuilder str = new StringBuilder("");
    if (this.model.getLength() == 0) {
      return "";
    }
    str.append(this.headerString());
    str.append(this.notesString());
    return str.toString();
  }

  /**
   * This function returns a {@code String} representation of the header portion of the model. This
   * consists of the notes displayed across the top.
   *
   * @return header portion of the view
   */
  private String headerString() {
    StringBuilder retString = new StringBuilder(String.format(this.getNumberFormat(), ""));
    IPitch curPitch = this.model.getLowest().getPitch();
    while (!curPitch.equals(this.model.getHighest().getPitch().next())) {
      retString.append(this.displayStringCentered(curPitch.toString()));
      curPitch = curPitch.next();
    }
    retString.append("\n");
    return retString.toString();
  }

  /**
   * This function returns a {@code String} representation of the header portion of the model. This
   * consists of the notes displayed underneath the top portion of the view.
   *
   * @return body portion of the view
   */
  private String notesString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < this.model.getLength(); i++) {
      str.append(String.format(this.getNumberFormat(), i));
      List<INote> list = this.model.getTopNotesAtBeat(i);
      IPitch curPitch = this.model.getLowest().getPitch();
      while (!curPitch.equals(this.model.getHighest().getPitch().next())) {
        NoteStatus status = NoteStatus.REST;
        for (INote n : list) {
          if (n.getPitch().equals(curPitch)) {
            status = n.getStatusAtBeat(i);
          }
        }
        str.append(this.displayStringCentered(status.toString()));
        curPitch = curPitch.next();
      }
      str.append("\n");
    }
    return str.toString();
  }

  /**
   * This is a helper function that will take in a string and render it evenly in the space of
   * {@code NOTEDISPLAYWIDTH}. It is used to display the header and body aesthetically in a table
   * manner.
   *
   * @param str String to be displayed
   *
   * @return String formatted appropriately
   */
  private String displayStringCentered(String str) {
    String retString = str;
    while (retString.length() <= this.NOTEDISPLAYWIDTH - 2) {
      retString = " " + retString + " ";
    }
    if (retString.length() == this.NOTEDISPLAYWIDTH - 1) {
      retString = " " + retString;
    }
    return retString;
  }

  /**
   * This function produces a string which is used in the number formatting of the columns on the
   * left side. The width of the columns need to be the exact width necessary. I.e. the width of the
   * numbers for a song that is 999 beats long is three.
   *
   * @return a format specifier for the width of the numbers.
   */
  private String getNumberFormat() {
    return "%" + Integer.toString(String.valueOf(this.model.getLength() - 1).length()) + "s";
  }
}
