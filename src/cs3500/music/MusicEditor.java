package cs3500.music;

import cs3500.music.controller.ControllerFactory;
import cs3500.music.controller.IController;
import cs3500.music.model.IMusicEditorOperations;
import cs3500.music.model.INote;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.MusicReader;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Main runner class that serves as the base point for the creation of the model and the appropriate
 * view specified by the arguments when the document is marked.
 */
public class MusicEditor {

  /**
   * Main function that runs the program specified by the arguments when it is invoked.
   *
   * @param args arguments to creat composition from mile and display view, the first argument is
   *        the file name and the second argument is a keyword "midi, console, visual" to create the
   *        appropriate view.
   * @throws IOException if file is not found or can't be open
   * @throws MidiUnavailableException if the MIDI playback engine can't be initialized
   */
  public static void main(String[] args) throws IOException, MidiUnavailableException {

    if (args.length != 2) {
      throw new IllegalArgumentException("Expected: <file name> <view type>");
    }

    IMusicEditorOperations<INote> model = null;
    try {
      model = MusicReader.parseFile(new FileReader(args[0]),
          new MusicEditorModel.Builder());
    } catch (Exception ex) {
      throw new IllegalArgumentException("Error reading \"" + args[0] + "\"");
    }
    IController cont = null;
    try {
      cont = ControllerFactory.getController(args[1], model);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException("Incorrect view specification: \"" + args[1]
          + "\". Must be one of \"console\", \"visual\", or \"midi\"");
    }
    cont.display();
  }
}