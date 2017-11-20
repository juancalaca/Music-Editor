package cs3500.music.tests;

import static org.junit.Assert.assertTrue;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.CompositeViewImpl;
import cs3500.music.view.ConsoleViewImpl;
import cs3500.music.view.GuiViewImpl;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.ViewFactory;
import org.junit.Test;

/**
 * Tester class for the ViewFactory.
 */
public class ViewFactoryTest {

  /**
   * Test the type of view returned by the factory.
   */
  @Test
  public void testType() {
    ViewFactory vf = new ViewFactory(new MusicEditorModel());
    assertTrue(vf.getView("console") instanceof ConsoleViewImpl);
    assertTrue(vf.getView("midi") instanceof MidiViewImpl);
    assertTrue(vf.getView("visual") instanceof GuiViewImpl);
    assertTrue(vf.getView("composite") instanceof CompositeViewImpl);
  }

  /**
   * Test that an error is thrown on a bad type specifier.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testError() {
    ViewFactory vf = new ViewFactory(new MusicEditorModel());
    vf.getView("Incorrect");
  }
}
