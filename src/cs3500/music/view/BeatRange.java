package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.JComponent;

/**
 * This class extends {@code JComponent} with the purpose of graphically representing the range of
 * beats in a piece of music. This component creates the range based on a concrete implementation of
 * {@code IMusicEditorOperations}. Based on the {@code measure}, the beats are evenly displaced to
 * fit the according piece of music. It creates a style-like header determined by the beats.
 *
 * @see JComponent
 * @see IMusicEditorOperations
 */
public class BeatRange extends JComponent {

  /**
   * Model which will be graphically represented by {@code BeatRange}.
   */
  private final IMusicEditorOperations model;

  /**
   * Coordinates used for scaling {@code BeatRange} object's graphics.
   */
  private final int x = 0;
  private final int fixedY = 20;

  /**
   * Dimensions and attributes of {@code BeatRange} object, to be used by graphics for the purpose
   * of stylizing output.
   */
  private final int measure = 4;
  private final int beatLength = 20;
  private final int fontSize = 18;
  private final int rowHeight = 25;
  private final String font = "Arial";
  private final int fontType = Font.BOLD;
  private final Font fontObject = new Font(font, fontType, fontSize);
  private int numBeats;

  /**
   * Constructor builds a {@code BeatRange} object to be based on {@code model} provided.
   *
   * @param model {@code IMusicEditorOperations} concrete implementation to base range on
   */
  public BeatRange(IMusicEditorOperations model) {
    Objects.requireNonNull(model, "Model must be non-null");
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    paintBeats(g);

  }

  /**
   * This method paints the graphical representation of the beats in {@code model}, defined by the
   * attributes of the {@code BeatRange} object.
   *
   * <p>It is important to note that there is an explicit call to get the {@code model}'s length
   * every time the method is called in order to represent the most recent beat range.</p>
   *
   * @param g {@code Graphics} object used to render beat range
   */
  private void paintBeats(Graphics g) {
    numBeats = model.getLength();
    if (numBeats % measure != 0) {
      numBeats += numBeats % measure;
    }

    g.setFont(fontObject);
    for (int i = 0; i < numBeats; i++) {
      if (i % measure == 0) {
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(i), x + (i * beatLength), fixedY);
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    numBeats = model.getLength();
    if (numBeats % measure != 0) {
      numBeats += (measure - (numBeats % measure));
    }
    return new Dimension((numBeats * beatLength) + 50, rowHeight);
  }
}
