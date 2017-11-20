package cs3500.music.controller;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.view.CompositeViewImpl;
import cs3500.music.view.GuiViewImpl;
import cs3500.music.view.ViewFactory;

/**
 * This factory produces the requested control-view pair specified by the type. It uses a
 * {@code ViewFactory} to create the view, and modifies that produced view in order to
 * create the controller paired to it that will provide and handle the needed functionality
 * specified by the type. It is important to note that this single point of control allows to manage
 * the type of views, and how we create the control-view pairs by allowing to create dynamically.
 * This also allows to hide all the types that can be provided and how they work relative to the
 * view they are assigned.
 *
 * @see ViewFactory
 */
public class ControllerFactory {

  /**
   * Gets a view based on the view type string specified. View options supported are console view,
   * visual view, and midi view.
   *
   * @param type of view to create
   * @param model model to control
   *
   * @return IView of the specified type
   *
   * @throws IllegalArgumentException if view type is not recognized
   */
  public static IController getController(String type, IMusicEditorOperations model)
      throws IllegalArgumentException {
    ViewFactory fact = new ViewFactory(model);
    switch (type) {
      case "console": {
        return new IViewController(fact.getView(type));
      }
      case "midi": {
        return new IViewController(fact.getView(type));
      }
      case "visual": {
        return new GuiController(model, (GuiViewImpl) fact.getView(type));
      }
      case "composite": {
        return new CompositeController(model, (CompositeViewImpl) fact.getView(type));
      }
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }
}
