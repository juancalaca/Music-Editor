package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * This class graphically represents the notes that represent the range of pitches in piece of
 * music. It extends {@code JComponent} with the purpose of using graphics to render the notes. This
 * component bases the note range based on a concrete implementation of {@code
 * IMusicEditorOperations} parametrized over {@code INote}. Based on the contents of the concrete
 * implementation of the interface, the component creates a style-like column header representing
 * the notes in the pitch range.
 *
 * @see JComponent
 * @see IMusicEditorOperations
 * @see INote
 */
public class NoteRange extends JComponent {

  /**
   * Model which will be graphically represented by {@code NoteRange}.
   */
  private IMusicEditorOperations<INote> model;

  /**
   * Coordinates for the purpose of placing graphics relative to a base position.
   */
  private int startY = 15;
  private int fixedX = 0;

  /**
   * Dimensions and attributes of {@code NoteRange} object used to stylize the graphics.
   */
  private int measure = 4;
  private int fontSize = 18;
  private int beatLength = 20;
  private int columnWidth = 40;
  private String fontType = "Arial";
  private int fontStyle = Font.BOLD;
  private Font fontObject = new Font(fontType, fontStyle, fontSize);
  private List<String> notes;

  /**
   * Constructor builds component to graphically represent the provided {@code model} in the desired
   * manner.
   *
   * @param model {@code IMusicEditorOperations} concrete implementation to base note range on
   */
  public NoteRange(IMusicEditorOperations<INote> model) {
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintNoteRange(g);
  }

  /**
   * This method paints the note range of the {@code model}, by creating a column and determining
   * the position by placing the highest pitch at the top and decreasing notes underneath.
   *
   * @param g {@code Graphics} object used to render note range
   */
  private void paintNoteRange(Graphics g) {
    getRange();
    g.setFont(fontObject);
    for (int i = notes.size() - 1; i >= 0; --i) {
      g.drawString(notes.get(i), fixedX, startY + ((notes.size() - 1 - (i)) * beatLength));
    }
  }

  @Override
  public Dimension getPreferredSize() {

    getRange();
    return new Dimension(columnWidth, notes.size() * beatLength);
  }

  /**
   * Helper function that creates a {@code List} of {@code String}s that represent the range of
   * pitches in the {@code model}'s piece.
   */
  private void getRange() {
    notes = new ArrayList<>();
    IPitch lowest = model.getLowest().getPitch();
    IPitch highest = model.getHighest().getPitch();
    while (!lowest.equals(highest.next())) {
      notes.add(lowest.toString());
      lowest = lowest.next();
    }
  }

}
