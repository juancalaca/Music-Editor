package cs3500.music.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.music.controller.MouseListenerImpl;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for MouseListenerImpl class.
 */
public class MouseListenerImplTest {

  MouseListenerImpl listener;
  private boolean hit;
  private String str;

  @Before
  public void init() {
    listener = new MouseListenerImpl();
  }

  @Test
  public void testAddsMap() {
    hit = false;
    Map<Integer, Consumer<MouseEvent>> map = new HashMap<>();
    map.put(MouseEvent.BUTTON1, (e) -> {
      hit = !hit;
    });
    listener.setMousePressedMap(map);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 3, 4, 5, true, MouseEvent.BUTTON1);
    listener.mouseClicked(m);
    assertTrue(hit);
  }

  @Test
  public void testValidKeyEvent() {
    hit = false;
    Map<Integer, Consumer<MouseEvent>> map = new HashMap<>();
    map.put(MouseEvent.BUTTON3, (e) -> {
      hit = !hit;
    });
    listener.setMousePressedMap(map);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 3, 4, 5, true, MouseEvent.BUTTON3);
    listener.mouseClicked(m);
    assertTrue(hit);
  }

  @Test
  public void testInvalidKeyEvent() {
    hit = false;
    Map<Integer, Consumer<MouseEvent>> map = new HashMap<>();
    map.put(MouseEvent.BUTTON1, (e) -> {
      hit = !hit;
    });
    listener.setMousePressedMap(map);
    Button b = new Button("P");
    MouseEvent m = new MouseEvent(b, 1, 2, 3, 3, 4, 5, true, MouseEvent.BUTTON3);
    listener.mouseClicked(m);
    assertFalse(hit);
  }

  @Test
  public void testMapNotAssignedThrowsNoErrors() {
    try {
      Button b = new Button("P");
      MouseEvent m = new MouseEvent(b, 1, 2, 3, 3, 4, 5, true, MouseEvent.BUTTON1);
      listener.mouseClicked(m);
    } catch (Exception e) {
      Assert.fail("Unexpected exception.");
    }
  }

  @Test
  public void testMockMap_SequenceEvents() {
    str = "";
    Map<Integer, Consumer<MouseEvent>> m = new HashMap<>();
    m.put(MouseEvent.BUTTON1, (e) -> {
      str += "Left pressed ";
    });
    m.put(MouseEvent.BUTTON3, (e) -> {
      str += "Right pressed ";
    });
    m.put(MouseEvent.BUTTON2, (e) -> {
      str += "Center pressed ";
    });
    listener.setMousePressedMap(m);
    Button b = new Button();
    MouseEvent left = new MouseEvent(b, 1, 2, 3, 4, 5, 5, true, MouseEvent.BUTTON1);
    MouseEvent right = new MouseEvent(b, 1, 2, 3, 4, 5, 5, true, MouseEvent.BUTTON3);
    MouseEvent center = new MouseEvent(b, 1, 2, 3, 4, 5, 5, true, MouseEvent.BUTTON2);
    listener.mouseClicked(left);
    listener.mouseClicked(right);
    listener.mouseClicked(center);
    assertEquals("Left pressed Right pressed Center pressed ", str);
  }

}