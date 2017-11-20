package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is configurable by the controller that
 * instantiates it.
 *
 * <p>
 * The map stores a key mapping. A key mapping is a pair
 * (keystroke, code to be executed with that keystroke)
 * The latter part of that pair is actually a function object, i.e. an object of a class
 * that implements the {@code Runnable} interface </p>
 *
 * <p>
 * This class implements the {@code KeyListener} interface, so that its object can be used as a
 * valid keyListener for Java Swing. NOTE: This code is based off of the example MVC code
 * provided at https://course.ccs.neu.edu/cs3500/lec/16/code.zip.</p>
 *
 * @see KeyListener
 * @see Runnable
 */
public class KeyListenerImpl implements KeyListener {

  /**
   * Map that dictates actions given the pressed key.
   */
  private Map<Integer, Runnable> keyPressedMap;

  /**
   * Empty default constructor.
   */
  public KeyListenerImpl() {
    this.keyPressedMap = new HashMap<>();
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
   *
   * @param map map that dictates what to do given a key pressed event
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    // Do nothing.
  }

  /**
   * This is called when the view detects that a key has been pressed.
   * Find if anything has been mapped to this key code and if so, execute it.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Do nothing.
  }

}
