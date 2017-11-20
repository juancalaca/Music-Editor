package cs3500.music.tests;

import static org.junit.Assert.assertTrue;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ControllerFactory;
import cs3500.music.controller.GuiController;
import cs3500.music.controller.IViewController;
import cs3500.music.model.MusicEditorModel;
import org.junit.Test;

/**
 * Test class for the controller factory class.
 */
public class ControllerFactoryTest {

  /**
   * Test the type of Controller returned by the factory.
   */
  @Test
  public void testType() {
    ControllerFactory cf = new ControllerFactory();
    assertTrue(cf.getController("console", new MusicEditorModel()) instanceof IViewController);
    assertTrue(cf.getController("midi", new MusicEditorModel()) instanceof IViewController);
    assertTrue(cf.getController("visual", new MusicEditorModel()) instanceof GuiController);
    assertTrue(
        cf.getController("composite", new MusicEditorModel()) instanceof CompositeController);
  }

  /**
   * Test that an error is thrown on a bad type specifier.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testError() {
    ControllerFactory cf = new ControllerFactory();
    cf.getController("bad", new MusicEditorModel());
  }
}
