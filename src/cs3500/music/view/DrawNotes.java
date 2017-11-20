package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;

import cs3500.music.model.IPitch;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;

/**
 * This class is in charge of graphically representing the notes in a piece of music. This class
 * extends {@code JPanel} to manage how the notes are being displayed. This "drawer" represents the
 * notes in a concrete implementation {@code IMusicEditorOperations} parametrized over {@code
 * INote}s by representing all the octaves present in the {@code model}'s piece. It represents the
 * range from highest (top) to lowest (bottom), painting the onset of a note black and it's
 * remaining duration in green. All drawn on a grid, separating octaves, respecting the measure
 * which is set to 4. This object also draws a marker, that visually indicates all the notes in the
 * current beat. The position of this marker is determined by the amount to shift.
 *
 * @see JPanel
 * @see IMusicEditorOperations
 * @see INote
 */
public class DrawNotes extends JPanel {

  /**
   * Model which will be graphically represented by {@code DrawNotes}.
   */
  private IMusicEditorOperations<INote> model;

  /**
   * Attributes of the {@code model} needed to accurately represent the most recent {@code INote}s
   * in it.
   */
  private List<String> notes;
  private int gridNum;

  /**
   * Coordinates with the purpose of scaling, and positioning {@code DrawNotes}' graphics.
   */
  private int x = 1;
  private int y = 1;
  private int markerX = 0;

  /**
   * Dimensions and attributes of {@code DrawNotes} object, used to properly size the graphics.
   */
  private int measure = 4;
  private int heightWeight = 20;
  private int beatLength = 20;
  private int markerWidth = 2;
  private int barHeight = 4;

  /**
   * Public constructor that constructs a {@code DrawNotes} object to be based, and updated by the
   * provided {@code model}'s content.
   *
   * @param model concrete implementation of {@code IMusicEditorOperations}
   */
  public DrawNotes(IMusicEditorOperations<INote> model) {
    Objects.requireNonNull(model, "Model must be non-null.");
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    getRange();
    getGridNum();

    drawNotes(g);
    drawGrid(g);
    drawMarker(g);
  }

  /**
   * This method draws all the {@code INote}s in the {@code model}. This method places the highest
   * notes at the top, and positions them horizontally according to their starting beat in the piece
   * of music. This method draws the onset of a note in black, while drawing the remaining duration
   * in green. All of these coordinates and dimensions are determined by {@code this} object's
   * attributes.
   *
   * @param g {@code Graphics} used to paint the notes
   */
  private void drawNotes(Graphics g) {
    for (INote n : model.getNotes()) {
      g.setColor(Color.GREEN);
      g.fillRect(x + (n.getStartBeat() * beatLength),
          y + (this.getYNote(n.getPitch().toString()) * heightWeight),
          ((n.getDuration()) * beatLength), heightWeight);
      g.setColor(Color.BLACK);
      g.fillRect(x + (n.getStartBeat() * beatLength),
          y + (this.getYNote(n.getPitch().toString()) * heightWeight), beatLength, heightWeight);
    }
  }

  /**
   * Helper function that returns the associated y-coordinate of the {@code pitch} based on the
   * range of {@code notes} currently in the {@code model}. For appropriate positioning in chromatic
   * scale.
   *
   * @param pitch {@code String} representation of pitch
   */
  private int getYNote(String pitch) {
    return ((notes.size() - 1) - notes.indexOf(pitch));
  }

  /**
   * Draws the grid  based on the number of beats, and the range of pitches currently in
   * the {@code model}'s piece of music. Drawing a separator between octaves.
   *
   * @param g {@code Graphics} to be used to paint grid
   */
  private void drawGrid(Graphics g) {
    int rangeNum = this.notes.size();
    g.setColor(Color.BLACK);
    for (int i = 0; i < rangeNum; ++i) {
      if (notes.get(rangeNum - 1 - i).charAt(0) == 'B' && notes.get(i).charAt(1) != '#') {
        g.fillRect(x, y + (i * heightWeight) - (barHeight / 2), gridNum * beatLength * measure,
            barHeight);
      }
      for (int j = 0; j < gridNum; ++j) {
        g.drawRect(x + (j * beatLength * measure), y + (i * heightWeight), beatLength * measure,
            heightWeight);
      }
    }
  }

  /**
   * This method draws the marker that visually indicates the current notes at it's position. The
   * "beat" position of the markers is determined by the {@code markerX} and {@code KeyListener}
   * events.
   *
   * @param g {@code Graphics} used to paint marker
   */
  private void drawMarker(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(markerX, y, markerWidth, notes.size() * heightWeight);
  }

  /**
   * Helper method that populates {@code notes} with {@code String} representation of the pitches in
   * the piece. Using the {@code model}'s methods to determine the bounds.
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

  /**
   * Helper method that determines the number of grids to be painted based on the length of the
   * piece provided by the {@code model}. The grid number is adjusted by the {@code measure} of
   * {@code this} object.
   */
  private void getGridNum() {
    int length = model.getLength();
    gridNum = (length / measure);
    if (length % measure != 0) {
      ++gridNum;
    }
  }

  @Override
  public Dimension getPreferredSize() {
    getGridNum();
    getRange();
    int gridNumAdj = measure * gridNum;
    return new Dimension((gridNumAdj * beatLength) + 50, notes.size() * heightWeight);
  }

  /**
   * Shifts the position of the marker according to the parameter and the {@code beatLength} of this
   * component.
   *
   * @param shift amount {@code markerX} will be shifted, moving red graphic marker
   */
  public void shiftView(int shift) {
    markerX = shift * beatLength;
    repaint();
  }

  public int getMarkerX() {
    return markerX;
  }
}
