package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.music.controller.KeyListenerImpl;
import java.awt.Button;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the KeyListenerImpl class.
 */
public class KeyListenerImplTest {

  KeyListenerImpl listener;
  private boolean hit;
  private String str;

  @Before
  public void init() {
    listener = new KeyListenerImpl();
  }

  @Test
  public void testAddsMap() {
    hit = false;
    Map<Integer, Runnable> map = new HashMap<>();
    map.put(KeyEvent.VK_P, () -> {
      hit = !hit;
    });
    listener.setKeyPressedMap(map);
    Button b = new Button("P");
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_P);
    listener.keyPressed(p);
    assertTrue(hit);
  }

  @Test
  public void testValidKeyEvent() {
    hit = false;
    Map<Integer, Runnable> m = new HashMap<>();
    m.put(KeyEvent.VK_0, () -> {
      hit = !hit;
    });
    listener.setKeyPressedMap(m);
    Button b = new Button("0");
    KeyEvent zero = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_0);
    listener.keyPressed(zero);
    assertTrue(hit);
  }

  @Test
  public void testInvalidKeyEvent() {
    hit = false;
    Map<Integer, Runnable> m = new HashMap<>();
    m.put(KeyEvent.VK_L, () -> {
      hit = !hit;
    });
    listener.setKeyPressedMap(m);
    Button b = new Button("R");
    KeyEvent r = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_R);
    listener.keyPressed(r);
    assertFalse(hit);
  }

  @Test
  public void testMapNotAssignedThrowsNoErrors() {
    try {
      Button b = new Button();
      listener.keyPressed(new KeyEvent(b, 1, 2, 3, KeyEvent.VK_R));
      KeyEvent left = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_LEFT);
      listener.keyPressed(left);
    } catch (Exception e) {
      Assert.fail("Unexpected exception.");
    }

  }

  @Test
  public void testMockMap_SequenceEvents() {
    str = "";
    Map<Integer, Runnable> m = new HashMap<>();
    m.put(KeyEvent.VK_LEFT, () -> {
      str += "Left pressed ";
    });
    m.put(KeyEvent.VK_RIGHT, () -> {
      str += "Right pressed ";
    });
    m.put(KeyEvent.VK_P, () -> {
      str += "P pressed ";
    });
    m.put(KeyEvent.VK_S, () -> {
      str += "S pressed";
    });
    listener.setKeyPressedMap(m);
    Button b = new Button();
    KeyEvent left = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_LEFT);
    KeyEvent right = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_RIGHT);
    KeyEvent p = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_P);
    KeyEvent s = new KeyEvent(b, 1, 2, 3, KeyEvent.VK_S);
    listener.keyPressed(left);
    listener.keyPressed(right);
    listener.keyPressed(p);
    listener.keyPressed(s);
    assertEquals("Left pressed Right pressed P pressed S pressed", str);
  }

}