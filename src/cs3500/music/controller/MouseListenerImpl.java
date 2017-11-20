package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class represents a mouse listener. This mouse listener is to be configured by the controller
 * that instantiates it.  The map stores a key mapping, where the key is the integer value of the
 * mouse event (either something relevant to the position of the mouse given a component, or a
 * clicking of the mouse). The value is a {@code Consumer} which is an object that works similar to
 * a {@code Runnable} by executing with a parameter value. This class implements the {@code
 * MouseListener} interface to be used in Java Swing.
 *
 * @see MouseListener
 * @see Consumer
 * @see Runnable
 */
public class MouseListenerImpl implements MouseListener {

  /**
   * Map dictating the action to be taken given any action of the mouse.
   */
  private Map<Integer, Consumer<MouseEvent>> mousePressedMap;

  /**
   * Empty default constructor.
   */
  public MouseListenerImpl() {
    this.mousePressedMap = new HashMap<>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getButton())) {
      mousePressedMap.get(e.getButton()).accept(e);
    }
  }

  /**
   * Sets the map, usually the duty of the object that created {@code this}, that defines actions
   * to be taken.
   *
   * @param map used to dictate the behavior of given any {@code MouseEvent}
   */
  public void setMousePressedMap(Map<Integer, Consumer<MouseEvent>> map) {
    this.mousePressedMap = map;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Do nothing.
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Do nothing.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Do nothing.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Do nothing.
  }
}
