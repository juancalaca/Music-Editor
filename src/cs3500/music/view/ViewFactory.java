package cs3500.music.view;

import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.ReadOnlyModel;

/**
 * This factory produces the requested view for a given model. The view options supported by this
 * factory are console, which will print to System.out, visual which will display the GUI view of
 * the song, and midi which will start the midi audio. Using a factory makes refactoring the code
 * much easier, as if a new view is created it can just be added to this switch statement and
 * integrated easily. We have added a new view, the composite view which allows to synchronously
 * display the model's contents visually and audibly.
 */
public class ViewFactory {

  private final IMusicEditorOperations model;

  /**
   * Constructor takes in a ready only model and uses it to create the view.
   *
   * @param model to render from
   */
  public ViewFactory(IMusicEditorOperations model) {
    this.model = new ReadOnlyModel(model);
  }

  /**
   * Gets a view based on the view type string specified. View options supported are console view,
   * visual view, midi view, and composite view.
   *
   * @param type of view to create
   * @return IView of the specified type
   * @throws IllegalArgumentException if view type is not recognized
   */
  public IView getView(String type) throws IllegalArgumentException {
    switch (type) {
      case "console": {
        return new ConsoleViewImpl(this.model, System.out);
      }
      case "visual": {
        return new GuiViewImpl(this.model);
      }
      case "midi": {
        return new MidiViewImpl(this.model);
      }
      case "composite": {
        return new CompositeViewImpl(new MidiViewImpl(this.model), new GuiViewImpl(this.model));
      }
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }
}