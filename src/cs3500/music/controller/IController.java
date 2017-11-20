package cs3500.music.controller;

/**
 * This is a general interface for a controller to be used with the model. This controller should,
 * in the simplest of means display the view provided to it. As well as provided additional
 * functionality, all dependant on concrete implementations of this type. It is important to note
 * that this allows to display the model in various ways, and hides the complexity behind that
 * drives the views used to represent the contents of a piece of music. The concrete implementations
 * of this controller need to provide what type of view they are controlling and presenting to the
 * user. This interface encapsulates the work behind provided to display the contents of the model
 * through a simple method call.
 */
public interface IController {

  /**
   * Display the appropriate representation of the model dictated by the controller and the view or
   * views it may contain. This method expects all concrete implementations to control the display
   * of contents of the model. It is important to mention that this method can fulfill many duties,
   * but at a minimum it should allow to display the contents of the model according to the
   * view that is associated to the type of controller. It is expected that this interface is used
   * to pair views with controllers and allow to provide a single method, that simply invokes the
   * pair and proceeds. The controller should provide logical views, as well as logical controls to
   * modify the view or the contents of the model.
   */
  void display();

}
