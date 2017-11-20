package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;

import cs3500.music.model.IPitch;
import cs3500.music.model.Key;
import cs3500.music.model.Pitch;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JPanel;

/**
 * This class is in charge of graphically representing a keyboard representation of the notes in a
 * piece of music. This class extends {@code JPanel} to manage how the notes are being displayed.
 * This "drawer" represents the notes in a concrete implementation {@code IMusicEditorOperations}
 * parametrized over {@code INote}s by representing all the octaves in a 10 octave range from 0-9.
 * All pressed keys in a given beat are rendered yellow, while others are preserved as either black
 * or white. The beat at which the piano is rendered is determined by the method {@code shiftView()}
 *
 * @see JPanel
 * @see IMusicEditorOperations
 * @see INote
 */
public class DrawPiano extends JPanel {

  /**
   * Dimensions and attributes of {@code this}, used to properly size the graphics.
   */
  private final int shiftX = 50;
  private final int gridWidth = 4;
  private final int gridHeight = 350;
  private final int sharpWidth = this.gridWidth * 2;
  private final int sharpHeight = 3 * this.gridHeight / 5;
  private final int wholeWidth = this.gridWidth * 4;
  private final int wholeHeight = this.gridHeight;

  /**
   * Instance variables that help in presenting the {@code model}'s content for the use of
   * {@code this} panel.
   */
  private Map<IPitch, Integer> keyMap;
  private Map<Integer, IPitch> inverseKeyMapSharp;
  private Map<Integer, IPitch> inverseKeyMapWhole;
  private IPitch lowest = new Pitch(0, Key.C);
  private IPitch highest = new Pitch(9, Key.B);
  private List<INote> noteList;

  /**
   * Model which will be graphically represented by {@code DrawPiano}.
   */
  private IMusicEditorOperations<INote> model;

  /**
   * Constructor to initialize local fields.
   *
   * @param model to render
   */
  public DrawPiano(IMusicEditorOperations<INote> model) {
    Objects.requireNonNull(model);
    this.model = model;
    this.setMapping();
    this.noteList = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintWholeNotesPressed(g, noteList);
    paintWholeNoteGrid(g);
    paintSharpNotes(g);
    paintSharpNotesPressed(g, noteList);
  }

  /**
   * Paints all whole notes that are pressed at the current beat yellow.
   *
   * @param g Graphics from the panel
   * @param noteList list of notes at a given beat
   */
  private void paintWholeNotesPressed(Graphics g, List<INote> noteList) {
    for (INote n : noteList) {
      IPitch p = n.getPitch();
      int startPos = shiftX + (this.gridWidth * this.keyMap.get(p));
      if (!this.isSharp(p)) {
        g.setColor(Color.YELLOW);
        g.fillRect(startPos, 0, wholeWidth, wholeHeight);
      }
    }
  }

  /**
   * Paints all whole keys on the piano in a 10 octave range.
   *
   * @param g graphics from the panel
   */
  private void paintWholeNoteGrid(Graphics g) {
    for (IPitch p : this.keyMap.keySet()) {
      int startPos = shiftX + (gridWidth * this.keyMap.get(p));
      if (!this.isSharp(p)) {
        g.setColor(Color.BLACK);
        g.drawRect(startPos, 0, wholeWidth, wholeHeight);
      }
    }
  }

  /**
   * Paints all sharp keys on the piano in a 10 octave range.
   *
   * @param g graphics from the panel
   */
  private void paintSharpNotes(Graphics g) {
    for (IPitch p : this.keyMap.keySet()) {
      int startPos = (shiftX + gridWidth * this.keyMap.get(p));
      if (this.isSharp(p)) {
        g.setColor(Color.BLACK);
        g.fillRect(startPos, 0, sharpWidth, sharpHeight);
      }
    }
  }

  /**
   * Paints all sharp notes that are pressed at the current beat yellow.
   *
   * @param g Graphics from the panel
   * @param noteList list of notes at a given beat
   */
  private void paintSharpNotesPressed(Graphics g, List<INote> noteList) {
    for (INote n : noteList) {
      IPitch p = n.getPitch();
      int startPos = shiftX + (this.gridWidth * this.keyMap.get(p));
      if (this.isSharp(p)) {
        g.setColor(Color.YELLOW);
        g.fillRect(startPos, 0, sharpWidth, sharpHeight);
        g.setColor(Color.BLACK);
        g.drawRect(startPos, 0, sharpWidth, sharpHeight);
      }
    }
  }

  /**
   * This function assigns a mapping of each pitch to its starting position. This is done by
   * considering a whole white note 2 ticks long, and a sharp note 1 tick long. As the pitches are
   * iterated through the notes are assigned a number of ticks relative to the starting point at the
   * far left side. This mapping will eventually be used to place the representation of the keys on
   * the view.
   */
  private void setMapping() {
    this.keyMap = new HashMap<>();
    int i = 0;
    IPitch p = this.lowest;
    while (!p.equals(this.highest.next())) {
      if (!this.isSharp(p)) {
        this.keyMap.put(p, i);
        i += 4;
      }
      p = p.next();
    }
    i = 2;
    p = this.lowest;
    while (!p.equals(this.highest.next())) {
      if (this.isSharp(p)) {
        this.keyMap.put(p, i + 1);
        if (p.getKey().equals(Key.DSHARP) || p.getKey().equals(Key.ASHARP)) {
          i += 4;
        }
        i += 4;
      }
      p = p.next();
    }
    this.inverseKeyMapSharp = new HashMap<>();
    this.inverseKeyMapWhole = new HashMap<>();
    for (Map.Entry<IPitch, Integer> e : this.keyMap.entrySet()) {
      this.inverseKeyMapSharp.put(e.getValue(), e.getKey());
      this.inverseKeyMapSharp.put(e.getValue() + 1, e.getKey());
      if (!this.isSharp(e.getKey())) {
        this.inverseKeyMapWhole.put(e.getValue(), e.getKey());
        this.inverseKeyMapWhole.put(e.getValue() + 1, e.getKey());
        this.inverseKeyMapWhole.put(e.getValue() + 2, e.getKey());
        this.inverseKeyMapWhole.put(e.getValue() + 3, e.getKey());
      }
    }
  }

  /**
   * This helper method checks an {@code IPitch} in order to draw the piano and the beat status
   * correctly.
   *
   * @param pitch {@code IPitch} to determine if it's sharp or not
   * @return true if {@code pitch} is a sharp note, false otherwise
   */
  private Boolean isSharp(IPitch pitch) {
    Key key = pitch.getKey();
    return key.equals(Key.ASHARP) || key.equals(Key.CSHARP) || key.equals(Key.DSHARP) || key
        .equals(Key.FSHARP) || key.equals(Key.GSHARP);
  }

  public int getWidthCentered() {
    return (2 * shiftX) + (70 * wholeWidth);
  }

  /**
   * Grabs the notes at the position specified by the value of the parameter, that is used to render
   * keys being pressed.
   *
   * @param position in beats to repaint {@code this} component
   */
  public void shiftView(int position) {
    if (position >= this.model.getLength()) {
      this.noteList = new ArrayList<>();
    } else {
      this.noteList = this.model.getAllNotesAtBeat(position);
    }
    repaint();
  }

  /**
   * This function converts a mouse event into a pitch based on the mapping on the notes drawn on
   * the keyboard.
   *
   * @param e MouseEvent to process
   * @return Pitch at the specified mouse click position
   * @throws IllegalArgumentException if the mouse click is outside of the mapped section
   */
  public IPitch getNoteFromClick(MouseEvent e) throws IllegalArgumentException {
    if (e.getX() < shiftX || e.getX() > (shiftX + 70 * this.wholeWidth) || e.getY() > wholeHeight) {
      throw new IllegalArgumentException("Outside of legal range");
    }
    int relativeX = (e.getX() - shiftX) / this.gridWidth;
    if (e.getY() > sharpHeight) {
      if (this.inverseKeyMapWhole.containsKey(relativeX)) {
        return this.inverseKeyMapWhole.get(relativeX);
      }
    } else {
      if (this.inverseKeyMapSharp.containsKey(relativeX)) {
        return this.inverseKeyMapSharp.get(relativeX);
      }
    }
    throw new IllegalArgumentException("Clicked in an unmapped area");
  }

}