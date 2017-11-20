package cs3500.music.tests;

import static org.junit.Assert.assertEquals;

import cs3500.music.model.INote;
import cs3500.music.model.Key;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Pitch;
import cs3500.music.view.ConsoleViewImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for {@code ConsoleViewImpl}.
 */
public class ConsoleViewTest {

  StringBuffer out;
  MusicEditorModel modelEmpty;
  MusicEditorModel modelThree;
  MusicEditorModel modelFour;
  MusicEditorModel modelGiven;
  ConsoleViewImpl csviewEmpty;
  ConsoleViewImpl csviewThree;
  ConsoleViewImpl csviewFour;
  ConsoleViewImpl csviewGiven;

  /**
   * Initialize variables for display.
   */
  @Before
  public void init() {
    this.modelEmpty = new MusicEditorModel();

    // Model with 3 notes
    this.modelThree = new MusicEditorModel();
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.A).setStartBeat(0).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.C).setStartBeat(0).setDuration(4).build());
    this.modelThree
        .addNote(INote.builder().setOctave(2).setKey(Key.D).setStartBeat(0).setDuration(4).build());

    // Model with 4 notes
    this.modelFour = new MusicEditorModel();
    this.modelFour
        .addNote(INote.builder().setOctave(2).setKey(Key.B).setStartBeat(2).setDuration(4).build());
    this.modelFour.addNote(
        INote.builder().setOctave(2).setKey(Key.CSHARP).setStartBeat(2).setDuration(4).build());
    this.modelFour.addNote(
        INote.builder().setOctave(2).setKey(Key.DSHARP).setStartBeat(2).setDuration(4).build());
    this.modelFour
        .addNote(INote.builder().setOctave(2).setKey(Key.E).setStartBeat(3).setDuration(4).build());

    // The given model on the assignments page
    this.modelGiven = new MusicEditorModel();

    // E3
    this.modelGiven.addNote(
        INote.builder().setPitch(new Pitch(3, Key.E)).setStartBeat(56).setDuration(8).build());

    // G3
    Pitch g3 = new Pitch(3, Key.G);
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(0).setDuration(7).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(8).setDuration(7).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(16).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(24).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(32).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(40).setDuration(8).build());
    this.modelGiven.addNote(INote.builder().setPitch(g3).setStartBeat(48).setDuration(8).build());

    // C4
    Pitch c4 = new Pitch(4, Key.C);
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(4).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(36).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(c4).setStartBeat(56).setDuration(8).build());

    // D4
    Pitch d4 = new Pitch(4, Key.D);
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(2).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(6).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(16).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(18).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(20).setDuration(4).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(34).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(38).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(48).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(50).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(d4).setStartBeat(54).setDuration(2).build());

    // E4
    Pitch e4 = new Pitch(4, Key.E);
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(0).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(8).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(10).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(12).setDuration(3).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(24).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(32).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(40).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(42).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(44).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(46).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(e4).setStartBeat(52).setDuration(2).build());

    // G4
    Pitch g4 = new Pitch(4, Key.G);
    this.modelGiven.addNote(INote.builder().setPitch(g4).setStartBeat(26).setDuration(2).build());
    this.modelGiven.addNote(INote.builder().setPitch(g4).setStartBeat(28).setDuration(4).build());

    this.out = new StringBuffer();
    csviewEmpty = new ConsoleViewImpl(this.modelEmpty, out);
    csviewThree = new ConsoleViewImpl(this.modelThree, out);
    csviewFour = new ConsoleViewImpl(this.modelFour, out);
    csviewGiven = new ConsoleViewImpl(this.modelGiven, out);
  }

  /**
   * Get string and deal with IOException.
   */
  public void viewIO(ConsoleViewImpl view) {
    try {
      view.display();
    } catch (Exception e) {
      //do nothing
    }
  }

  /**
   * Test that adding a note where there is already a note will return false and not disrupt the
   * model.
   */
  @Test
  public void addNoteWhereNoteAlreadyIs() {
    this.modelThree.addNote(
        INote.builder().setOctave(2).setKey(Key.A).setStartBeat(0).setDuration(2).build());
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n"
            + "0  X         X                                  X  \n"
            + "1  |         |                                  |  \n"
            + "2  |         |                                  |  \n"
            + "3  |         |                                  |  \n");
  }

  /**
   * This function tests that adding a note on another note in sustain will clip the remainder of
   * the underlying note and will add the note to the model.
   */
  @Test
  public void addNoteWhereNoteOnOtherNoteSustain() {
    this.modelThree.addNote(
        INote.builder().setPitch(new Pitch(2, Key.A)).setStartBeat(1).setDuration(2).build());
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n"
            + "0  X         X                                  X  \n"
            + "1  |         |                                  X  \n"
            + "2  |         |                                  |  \n"
            + "3  |         |                                  |  \n");
  }

  /**
   * This tests that adding a note which runs into another note will cover the added note and
   * return true.
   */
  @Test
  public void addNoteWhereNoteRunsIntoAnotherNote() {
    this.modelFour.addNote(
        INote.builder().setPitch(new Pitch(2, Key.B)).setStartBeat(1).setDuration(6).build());
    this.viewIO(this.csviewFour);
    assertEquals(this.out.toString(),
        "  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2 \n"
            + "0                                                       \n"
            + "1                                                    X  \n"
            + "2  X         X                                       |  \n"
            + "3  |         |    X                                  |  \n"
            + "4  |         |    |                                  |  \n"
            + "5  |         |    |                                  |  \n"
            + "6                 |                                  |  \n");
  }


  /**
   * Combine two full models.
   */
  @Test
  public void combineModelFourWithModelThreeTest() {
    this.modelFour.combine(this.modelThree);
    this.viewIO(this.csviewFour);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2 \n"
            + "0  X         X                                  X            \n"
            + "1  |         |                                  |            \n"
            + "2  |    X    |    X                             |         X  \n"
            + "3  |    |    |    |    X                        |         |  \n"
            + "4       |         |    |                                  |  \n"
            + "5       |         |    |                                  |  \n"
            + "6                      |                                     \n");
  }

  /**
   * Combine two full models.
   */
  @Test
  public void combineModelThreeWithModelFourTest() {
    this.modelThree.combine(this.modelFour);
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2 \n"
            + "0  X         X                                  X            \n"
            + "1  |         |                                  |            \n"
            + "2  |    X    |    X                             |         X  \n"
            + "3  |    |    |    |    X                        |         |  \n"
            + "4       |         |    |                                  |  \n"
            + "5       |         |    |                                  |  \n"
            + "6                      |                                     \n");
  }


  /**
   * Test combining two full models sequentially.
   */
  @Test
  public void combineSequentiallyModelFourWithModelThreeTest() {
    this.modelFour.combineSequentially(this.modelThree);
    this.viewIO(this.csviewFour);
    assertEquals(this.out.toString(),
        "    C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2 \n"
            + " 0                                                            \n"
            + " 1                                                            \n"
            + " 2       X         X                                       X  \n"
            + " 3       |         |    X                                  |  \n"
            + " 4       |         |    |                                  |  \n"
            + " 5       |         |    |                                  |  \n"
            + " 6                      |                                     \n"
            + " 7  X         X                                  X            \n"
            + " 8  |         |                                  |            \n"
            + " 9  |         |                                  |            \n"
            + "10  |         |                                  |            \n");
  }

  /**
   * Test combining two full models sequentially.
   */
  @Test
  public void combineSequentiallyModelThreeWithModelFourTest() {
    this.modelThree.combineSequentially(this.modelFour);
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "    C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2 \n"
            + " 0  X         X                                  X            \n"
            + " 1  |         |                                  |            \n"
            + " 2  |         |                                  |            \n"
            + " 3  |         |                                  |            \n"
            + " 4                                                            \n"
            + " 5                                                            \n"
            + " 6       X         X                                       X  \n"
            + " 7       |         |    X                                  |  \n"
            + " 8       |         |    |                                  |  \n"
            + " 9       |         |    |                                  |  \n"
            + "10                      |                                     \n");
  }

  /**
   * Tests that adding a note beyond the size of the editor will grow the editor.
   */
  @Test
  public void addNoteBeyondSizeOfEditor() {
    this.modelThree.addNote(
        INote.builder().setPitch(new Pitch(2, Key.G)).setStartBeat(5).setDuration(2).build());
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n"
            + "0  X         X                                  X  \n"
            + "1  |         |                                  |  \n"
            + "2  |         |                                  |  \n"
            + "3  |         |                                  |  \n"
            + "4                                                  \n"
            + "5                                     X            \n"
            + "6                                     |            \n");
  }

  /**
   * Test rendering of the given model.
   */
  @Test
  public void renderGivenVisualTest() {
    this.viewIO(this.csviewGiven);
    assertEquals(this.out.toString(),
        "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 \n"
            + " 0                 X                                            X                 \n"
            + " 1                 |                                            |                 \n"
            + " 2                 |                                  X                           \n"
            + " 3                 |                                  |                           \n"
            + " 4                 |                        X                                     \n"
            + " 5                 |                        |                                     \n"
            + " 6                 |                                  X                           \n"
            + " 7                                                    |                           \n"
            + " 8                 X                                            X                 \n"
            + " 9                 |                                            |                 \n"
            + "10                 |                                            X                 \n"
            + "11                 |                                            |                 \n"
            + "12                 |                                            X                 \n"
            + "13                 |                                            |                 \n"
            + "14                 |                                            |                 \n"
            + "15                                                                                \n"
            + "16                 X                                  X                           \n"
            + "17                 |                                  |                           \n"
            + "18                 |                                  X                           \n"
            + "19                 |                                  |                           \n"
            + "20                 |                                  X                           \n"
            + "21                 |                                  |                           \n"
            + "22                 |                                  |                           \n"
            + "23                 |                                  |                           \n"
            + "24                 X                                            X                 \n"
            + "25                 |                                            |                 \n"
            + "26                                                                             X  \n"
            + "27                                                                             |  \n"
            + "28                                                                             X  \n"
            + "29                                                                             |  \n"
            + "30                                                                             |  \n"
            + "31                                                                             |  \n"
            + "32                 X                                            X                 \n"
            + "33                 |                                            |                 \n"
            + "34                 |                                  X                           \n"
            + "35                 |                                  |                           \n"
            + "36                 |                        X                                     \n"
            + "37                 |                        |                                     \n"
            + "38                 |                                  X                           \n"
            + "39                 |                                  |                           \n"
            + "40                 X                                            X                 \n"
            + "41                 |                                            |                 \n"
            + "42                 |                                            X                 \n"
            + "43                 |                                            |                 \n"
            + "44                 |                                            X                 \n"
            + "45                 |                                            |                 \n"
            + "46                 |                                            X                 \n"
            + "47                 |                                            |                 \n"
            + "48                 X                                  X                           \n"
            + "49                 |                                  |                           \n"
            + "50                 |                                  X                           \n"
            + "51                 |                                  |                           \n"
            + "52                 |                                            X                 \n"
            + "53                 |                                            |                 \n"
            + "54                 |                                  X                           \n"
            + "55                 |                                  |                           \n"
            + "56  X                                       X                                     \n"
            + "57  |                                       |                                     \n"
            + "58  |                                       |                                     \n"
            + "59  |                                       |                                     \n"
            + "60  |                                       |                                     \n"
            + "61  |                                       |                                     \n"
            + "62  |                                       |                                     \n"
            + "63  |                                       |                              "
            + "       \n");
  }

  /**
   * Test rendering of a 100+ beat model adjusts the index column appropriately.
   */
  @Test
  public void render100BeatModelTest() {
    this.modelGiven.addNote(INote.builder().setPchOctStrDur(Key.C, 4, 79, 25).build());
    this.viewIO(this.csviewGiven);
    assertEquals(getStringRender100BeatModelTest(),
        this.out.toString());
  }

  /**
   * Helper method returns the string of the console view associated to the {@code
   * render100BeatModelTest()}.
   *
   * @return formatted and expected {@code String}
   */
  public String getStringRender100BeatModelTest() {
    String str = "     E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4"
        + "   G4 \n"
        + "  0                 X                                            X                 \n"
        + "  1                 |                                            |                 \n"
        + "  2                 |                                  X                           \n"
        + "  3                 |                                  |                           \n"
        + "  4                 |                        X                                     \n"
        + "  5                 |                        |                                     \n"
        + "  6                 |                                  X                           \n"
        + "  7                                                    |                           \n"
        + "  8                 X                                            X                 \n"
        + "  9                 |                                            |                 \n"
        + " 10                 |                                            X                 \n"
        + " 11                 |                                            |                 \n"
        + " 12                 |                                            X                 \n"
        + " 13                 |                                            |                 \n"
        + " 14                 |                                            |                 \n"
        + " 15                                                                                \n"
        + " 16                 X                                  X                           \n"
        + " 17                 |                                  |                           \n"
        + " 18                 |                                  X                           \n"
        + " 19                 |                                  |                           \n"
        + " 20                 |                                  X                           \n"
        + " 21                 |                                  |                           \n"
        + " 22                 |                                  |                           \n"
        + " 23                 |                                  |                           \n"
        + " 24                 X                                            X                 \n"
        + " 25                 |                                            |                 \n"
        + " 26                                                                             X  \n"
        + " 27                                                                             |  \n"
        + " 28                                                                             X  \n"
        + " 29                                                                             |  \n"
        + " 30                                                                             |  \n"
        + " 31                                                                             |  \n"
        + " 32                 X                                            X                 \n"
        + " 33                 |                                            |                 \n"
        + " 34                 |                                  X                           \n"
        + " 35                 |                                  |                           \n"
        + " 36                 |                        X                                     \n"
        + " 37                 |                        |                                     \n"
        + " 38                 |                                  X                           \n"
        + " 39                 |                                  |                           \n"
        + " 40                 X                                            X                 \n"
        + " 41                 |                                            |                 \n"
        + " 42                 |                                            X                 \n"
        + " 43                 |                                            |                 \n"
        + " 44                 |                                            X                 \n"
        + " 45                 |                                            |                 \n"
        + " 46                 |                                            X                 \n"
        + " 47                 |                                            |                 \n"
        + " 48                 X                                  X                           \n"
        + " 49                 |                                  |                           \n"
        + " 50                 |                                  X                           \n"
        + " 51                 |                                  |                           \n"
        + " 52                 |                                            X                 \n"
        + " 53                 |                                            |                 \n"
        + " 54                 |                                  X                           \n"
        + " 55                 |                                  |                           \n"
        + " 56  X                                       X                                     \n"
        + " 57  |                                       |                                     \n"
        + " 58  |                                       |                                     \n"
        + " 59  |                                       |                                     \n"
        + " 60  |                                       |                                     \n"
        + " 61  |                                       |                                     \n"
        + " 62  |                                       |                                     \n"
        + " 63  |                                       |                                     \n"
        + " 64                                                                                \n"
        + " 65                                                                                \n"
        + " 66                                                                                \n"
        + " 67                                                                                \n"
        + " 68                                                                                \n"
        + " 69                                                                                \n"
        + " 70                                                                                \n"
        + " 71                                                                                \n"
        + " 72                                                                                \n"
        + " 73                                                                                \n"
        + " 74                                                                                \n"
        + " 75                                                                                \n"
        + " 76                                                                                \n"
        + " 77                                                                                \n"
        + " 78                                                                                \n"
        + " 79                                          X                                     \n"
        + " 80                                          |                                     \n"
        + " 81                                          |                                     \n"
        + " 82                                          |                                     \n"
        + " 83                                          |                                     \n"
        + " 84                                          |                                     \n"
        + " 85                                          |                                     \n"
        + " 86                                          |                                     \n"
        + " 87                                          |                                     \n"
        + " 88                                          |                                     \n"
        + " 89                                          |                                     \n"
        + " 90                                          |                                     \n"
        + " 91                                          |                                     \n"
        + " 92                                          |                                     \n"
        + " 93                                          |                                     \n"
        + " 94                                          |                                     \n"
        + " 95                                          |                                     \n"
        + " 96                                          |                                     \n"
        + " 97                                          |                                     \n"
        + " 98                                          |                                     \n"
        + " 99                                          |                                     \n"
        + "100                                          |                                     \n"
        + "101                                          |                                     \n"
        + "102                                          |                                     \n"
        + "103                                          |                                     \n";
    return str;
  }

  /**
   * Test rendering of a <10 beat model adjusts the index column appropriately.
   */
  @Test
  public void renderSingleIndexModelTest() {
    this.viewIO(this.csviewThree);
    assertEquals(this.out.toString(),
        "   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2 \n"
            + "0  X         X                                  X  \n"
            + "1  |         |                                  |  \n"
            + "2  |         |                                  |  \n"
            + "3  |         |                                  |  \n");
  }


  /**
   * Test rendering of the empty model.
   */
  @Test
  public void renderEmptyModelTest() {
    this.viewIO(this.csviewEmpty);
    assertEquals(this.out.toString(), "");
  }

  /**
   * Render empty after add and remove.
   */
  @Test
  public void renderEmptyModelAfterAddAndRemove() {
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.C, 10, 0, 3).build());
    this.modelEmpty.removeNote(INote.builder().setPchOctStrDur(Key.C, 10, 0, 3).build());
    this.viewIO(this.csviewEmpty);
    assertEquals(this.out.toString(), "");
  }


  /**
   * Test that rendering with large octave numbers is displaying appropriately.
   */
  @Test
  public void renderWithLargeOctaveNumber() {
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.C, 10, 0, 3).build());
    this.modelEmpty.addNote(INote.builder().setPchOctStrDur(Key.E, 10, 0, 3).build());
    this.viewIO(this.csviewEmpty);
    assertEquals(this.out.toString(),
        "  C10  C#10 D10  D#10 E10 \n"
            + "0  X                   X  \n"
            + "1  |                   |  \n"
            + "2  |                   |  \n");
  }
}