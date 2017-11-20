package cs3500.music.view;


import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.IPitch;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * This class implements interface {@code IView} to display a GUI view of the provided model. This
 * class can be considered as the aggregator for our views. That is, it takes care of instantiating
 * the several components utilized to represent the given {@code model} and takes care of placing
 * the components, in their respective places. This class creates a {@code JFrame}, with a {@code
 * GridLayout} in order display the {@code JScrollPane} and {@code JPanel} created by what this
 * class considers "drawers." This class creates two views for the provided model that implements
 * {@code IMusicEditorOperations} parametrized over {@code INote}. The top view is a holistic view
 * of the piece, with all the notes, providing a marker that moves according to the specified Key
 * Board Events. The bottom portion of the frame is a piano, displaying 10 octaves, where it shows
 * the keys being pressed directly related to the position of said marker.
 *
 * @see IView
 * @see JFrame
 * @see GridLayout
 * @see JScrollPane
 * @see JPanel
 * @see IMusicEditorOperations
 * @see INote
 */
public class GuiViewImpl extends JFrame implements IGuiView {

  /**
   * Model which will be graphically represented by {@code GuiViewImpl} and all its components.
   */
  IMusicEditorOperations<INote> model;

  /**
   * Swing objects that work together in order to display model as desired.
   */
  private JScrollPane scroll;
  private final DrawNotes noteDrawer;
  private final DrawPiano pianoDrawer;
  private int beatPosition = 0;
  private JViewport l;
  private Point p = new Point();

  /**
   * Attributes of {@code JFrame} created by this object in order to scale frame size to center the
   * components in it.
   */
  int width;
  int height = 900;
  int scrollSpeed = 250;
  int beatLength = 20;

  /**
   * Constructs a {@code GuiViewImpl} to be based on provided {@code model}. This "aggregator" class
   * passes on the reference of the provided {@code model} to all its components in order for them
   * to always display the most current and precise information provided by the {@code model}.
   *
   * @param model {@code IMusicEditorOperations} concrete implementation to base GUI on
   */
  public GuiViewImpl(IMusicEditorOperations<INote> model) {
    super();
    this.model = model;
    this.noteDrawer = new DrawNotes(model);
    noteDrawer.shiftView(0);
    this.pianoDrawer = new DrawPiano(model);
    this.pianoDrawer.shiftView(0);
    this.pianoDrawer.setPreferredSize(new Dimension(width, height / 2));
    constructScroller();
    buildFrame();
    setVisible(false);
  }

  @Override
  public void display() {
    setVisible(true);
  }

  /**
   * This method constructs the {@code JScrollPane} by using the {@code DrawNotes} drawer as a view,
   * and utilizing the components {@code BeatRange} and {@code NoteRange}, to populate the column
   * header and the row header. Providing a convenient way for user's to "surf" the contents of the
   * {@code model}.
   */
  private void constructScroller() {
    this.scroll = new JScrollPane(noteDrawer);
    this.scroll.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
    this.scroll.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);
    BeatRange br = new BeatRange(model);
    NoteRange nc = new NoteRange(model);
    this.scroll.setRowHeaderView(nc);
    this.scroll.setColumnHeaderView(br);
    this.scroll.setPreferredSize(new Dimension(width, height / 2));
    this.l = this.scroll.getViewport();
  }

  /**
   * This method takes care of putting all the components together and instantiating the actual
   * frame seen by users. It does so by positioning the {@code DrawPiano} drawer and the {@code
   * JScrollPane} "{@code scroll}" in a frame and setting the scroll on the top half, and the piano
   * in the lower-half. The frame created is not resizable, in order to keep our components in their
   * correct position, based on the width and height of the current frame's dimensions.
   */
  private void buildFrame() {
    setTitle("Music Editor");
    setSize(new Dimension(pianoDrawer.getWidthCentered(), height));
    setLayout(new GridLayout(2, 1));
    add(scroll, BorderLayout.NORTH);
    add(pianoDrawer, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(false);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.pianoDrawer.addMouseListener(listener);
  }

  @Override
  public MouseListener[] getMouseListeners() {
    return this.pianoDrawer.getMouseListeners();
  }


  @Override
  public void shiftView(int position) {
    this.beatPosition = position;
    this.pianoDrawer.shiftView(position);
    this.noteDrawer.shiftView(position);
    p.setLocation(getVisibleX(), scroll.getVerticalScrollBar().getValue());
    l.setViewPosition(p);
  }

  private int getVisibleX() {
    if (noteDrawer.getMarkerX() - (4 * beatLength) < 0) {
      return 0;
    } else {
      return noteDrawer.getMarkerX() - (4 * beatLength);
    }
  }

  @Override
  public IPitch clickNote(MouseEvent e) throws IllegalArgumentException {
    return this.pianoDrawer.getNoteFromClick(e);
  }

  @Override
  public int getBeatPosition() {
    return this.beatPosition;
  }
}
