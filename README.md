# Assignment 7: Controlling the Music Editor 

This assignment continues off of the assignment in Homework 6 with the addition of 1 more view for 
the rendered songs in a partner setting. Now the project supports an audio "midi" view of the 
song, a gui "visual" view, a "console" view, and a "composite" view combining the midi and gui view.
Altogether this step shows a rounding out of the MVC paradigm, completing the controller portion of 
the project. In this README, every package is going to state the files that where changed, and in 
the file description there is going to be a more detailed description of the changes and why they 
were made. Additionally, if a new file was created for a package it is going to be stated and 
described there. 

# Interface/Class/Enum Descriptions

# Controller
This new package contains all the classes used to control our model. We decided to create a 
three different kinds of controllers. Ranging from basic display functionality with the console view
and the midi, key and mouse mappings with the GuiController, and additional mapping with the 
composite controller. All three would support a function display(), which starts the displaying of
the view. In the case of the composite view, this will display the gui representation but will not
start the midi. The midi and movement of the piece can be started with the space bar. For 
controllers with key and mouse mappings we decided to go with the lambda map design suggested, 
overriding keyListener and MouseListener, adding it to the view.

**UML Diagram for Controller Objects**

```

                                   __________________
                                  |   <<interface>>  |
                                  |    IController   |
                                  |——————-——————————-|
                                  |+ display(): void |
                                   —————————————————-
                                            ^
                                            |
                       _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                      |                                             |
    _____________________________________                   __________________
   |            GuiController            |                 |  IViewController |
   |————————————————————————————————————-|                 |—————————————————-|
   |- IGuiView view                      |                 |- IView view      |
   |- IMusicEditorOperations model       |                 |——————————————————|
   |- Map<Integer,Runnable> keyPresses   |                 |+ display(): void |
   |- Map<Integer,Consumer> mousePresses |                  ——————————————————
   |————————————————————————————————————-|
   |+ display(): void                    |
   |- configureKeyboardListener(): void  |
   |- configureMouseListener(): void     |
   |- normalizeBeatNum(): void           |
    ————————————————————————————————————-
                      ^
                      ^
                      ^     
                      ^
                      ^
 _____________________________________________
|        CompositeController (Final)          | 
|————————————————————————————————————————————-|
|- CompositeViewImpl view                     |
|- IMusicEditorOperations model               |
|- Timer timer                                |
|————————————————————————————————————————————-|
|+ display(): void                            |
|- configureCompositeKeyBoardListener(): void |
|- configureCompositeMouseListener(): void    |
|- setTimer(): void                           |
 —————————————————————————————————————————————

```

## IController
This is a general interface for a controller to be used with the model. It supports one function, 
display() which can be interpreted by the specific implementations to display the specific type of 
view the controller is associated with.

## IViewController
This class is a simple controller for IViews, that simply display the view provided. It has only one
function to display, and there is no notion of key or mouse mappings.

## GuiController
This class is a controller for IGuiViews that supports key presses and mouse clicks. Mouse mappings
are made protected fields so that a class that extends it can mutate the mappings to fit its needs.
The GuiController supports left and right movement through the piece with the left and right arrow 
keys, a jump to the end of a song with the end key, a jump to the beginning of the song with the 
home key, and adding a note by clicking on a key on the piano at the current beat.

## CompositeController
This class is a controller for CompositeViewImpls, that supports additional functionality for 
composite views. This class is declared final and extends GuiController due to some code redundancy
with the gui key and mouse mappings. It access and adds to the key and mouse mappings which are
fields of GuiController. It also supports pause and play with the space bar, and adding a note
with by clicking on a key on the piano at the current beat only when the song is paused. Lastly,
the position of the song is now driven by the Midi. A timer continuously checks the midi's position,
and updates the gui accordingly.

## ControllerFactory
The controller factory dynamically creates controllers based on the desired type of view for the 
song. It makes use of the view factory to create the appropriate view, and then casts to pass to
the appropriate controller. The cast is only performed on guiViews and compositeViews, and is 
necessary due to the return type of the viewFactory.

### Supported Functionality

**Left Key** - scrolls the view to the left by one beat at a time
<t> 

**Right Key** - scrolls the view to the right by one beat at a time
<t>

**Home Key** - jumps to the start of the piece.
<t>

**End Key** - jumps to the end of the piece.
<t>

**Space Bar** - control play/pause for the composite view
<t>

**Mouse** - if click within range of piano, add according pitch to piece at marker's beat.

## Listeners

### KeyListenerImpl
This class implements the KeyListener interface and is used to create custom mappings for key
presses which can be passed to the view. The only overridden function is keyPressed, other functions
like keyTyped and keyReleased are stubs.

### MouseListenerImpl
This class implements the KeyListener interface and is used to create custom mappings for mouse
clicks which can be passed to the view. The only overridden function is mouseClicked, other 
functions like MouseReleased are stubs.

# Model/BackEnd
Classes Changed: MusicEditorModel
<p>Classes Added: ReadOnlyModel

## IMusicEditorOperations
This interface determines the basic functionality for creating a piece of music. The model depends 
on a note to create a piece of music. It was decided it would be a generic interface to increase 
flexibility of design.

## MusicEditorModel
This concrete implementation of the interface IMusicEditorOperations that stores the notes in an 
efficient mostly constant time access data structure. Our model is based on an Arraylist of beats 
that store all notes that are present in a beat at a given time. We also keep track of the global
highest and lowest Pitches as any given time which will prevent repeated O(N) searches throughout
the piece. This class defines itself by the INote class and relies on the concrete implementation of
a note. 
<p>Changes: small bug identified and fixed, edge case for empty piece in length

## Note Priority Paradigm
The paradigm of Note priority needed to be established in order to create a uniform understanding
of how notes will interact with each other when added or removed to a piece. The implementation that
we chose behaves in the following way:

#### Adding Notes
The notes of a piece are always preserved until removed. Overlaid notes and notes at the same beat
will coexist (as long as they have a duration larger than 0). 

#### Displaying Notes (GUI and Console)
The notes are displayed in chronological order. This intuitive interpretation will render only the
newest notes at a given time. If a new note is added before another and carries into it, it will 
be displayed as the newer note. If a new note is placed during the duration of another note it will
be displayed for its duration. It is important to note that the underlying notes are still preserved
and will be displayed in the piano version of the GUI view.
For example: if the following are added together on the same note:
```
0  X   +           =      X
1  |       X              X
2  |       |              |
3  |                      |
```
```
0      +   X       =      X
1  X       |              |
2  |       |              |
3          |              |
```

## IBeat
This interface declares an IBeat that stores the Notes in a given beat of a song.

## Beat
Implementation of the IBeat class. The Beat also has a focus on performance. It uses a TreeMap to 
preserve ordering of the notes so that getting the highest or lowest note during any search 
will be fast. It enforces the Head Priority paradigm described above by storing dequeues of Notes at
any pitch.

## INote
This interface sets a basis for the note used in MusicEditor, as well as the attributes required. 
It was decided to provide a static builder method because a note, has a lot of attributes to it so 
it seemed convenient to provide various ways to construct the note. It stores enough information to
be rendered in any of the three views.

## Note
Concrete implementation of INote interface used to define the MusicEditor object. Note that this 
class is an immutable object. This means that there is no need to pass around copies of the object 
during note addings or song combinings. Once the object is created it cannot be updated, and 
therefore there is no harm in providing this to the user directly.

## IPitch
Defines an interface for a pitch. This class is very important for use as a hash key in the treemaps
for each Beat. It extends Iterator and Comparable.

## Pitch
Implements IPitch. This class is also immutable and there is no harm in passing it around or 
providing it to the user.

## Key
This enum provides all the pitches in the twelve-tone equal tempered Western style scale. It is
a pitch going from C to B. This implements Iterator which is very useful when rendering the model.

## NoteStatus
This enum provides, and limits, the status of a note. This came in handy to map the notes of the 
piece to the actual status of the notes. It also serves as a way to render the piece as text more 
easily, and delegate the formatting to the enum rather than the MusicEditor object.

## ReadOnlyModel
This new file wraps MusicEditorModel for it to be exclusively read-only. The purpose is for the 
views to only have access to a read-only model in order to prevent the model from being
mutated or modified by objects that do not have such permission.

# Util

## CompositionBuilder
A builder of compositions implemented by our MusicEditorModel to construct a new composition based 
on the given notes that have been added and the desired tempo. 

## MusicReader
Helper class that provides functionality to conveniently parse a file with specified commands and 
construct a composition based on it. In cohesion with the CompositionBuilder interface implemented 
by our MusicEditorModel, it takes the role of a factory for producing new music compositions, given
a source for commands and a CompositionBuilder. 

#V iews
Files Changed: BeatRange, DrawNotes, DrawPiano, GuiViewImpl, MidiViewImpl, ViewFactory
<p>Files Added: CompositeViewImpl, IGuiView

## IView
Interface all views for the model must adhere to. This interface is simple in order to enforce 
"Interface segregation", for a view it all boils down to displaying the actual view. Thus, we 
decided to have a single method for displaying the view. As mentioned in the interface, any view 
that implements this interface should provide a visual/audible representation of the model with a 
minimum of displaying all notes, their pitch, and their position. This could be better described as
a facade to what is going behind the views we can present. We decided to have this simple interface
in order to display our different views, hiding the complexity/objects in the back. 

## ConsoleViewImpl
This class implements the IView interface and provides the functionality for printing a 
representation of the model to the console. It does this by using the getTopNotesAtBeat function
supported by the model, which is specifically designed to return just the top notes. This prevents
the overhead of having to iterate through all of the notes, and instead just displays the top
at each beat, thus enforcing the "Note Priority Paradigm".

## IGuiView
This new interface was used to specialize more functionality expected from GUI views, that our other 
views should not intend to offer. This allowed us to add event handlers, and provide a better
controller-view pairing depending on what we wanted to display, and the functionality offered by 
that view to further interact with the model. 

## GuiViewImpl
This class is an "aggregator", meaning that it instantiates various Swing components and executes 
sequential steps to construct the frame to be presented to the user. This class can be considered 
to be the top class for our GUI view, since it instantiates all the components that work together 
to graphically display the contents of the model. It takes care of setting the correct dimensions, 
and connecting all the components together.
<p>Changes:
Implements IGuiView, and extends JFrame. This was done to have a more specialized interface for the 
expected behavior of this view. An important thing to note is that the new interface required more 
methods in order for it's controller to be able to provide desired functionality. We wanted to point 
out the following method.

```
IPitch clickNote(MouseEvent e); - since we had a component that needed to draw the piano and set 
                                  the dimensions for such piano, the pitch is inherently related to 
                                  the position of its according piano key, thus taking advantage of
                                  that components information to get concise information from a
                                  MouseEvent.
```

## MidiViewImpl
For our MIDI we decided to go with the Sequencer setup for two major reasons. First, this allowed 
us to set the tempo of the track and add a MetaEventListener that closes the sequencer when an 
"End of track message" is received. This seemed like a more viable option, rather than sleeping 
the program. This obviously depended strongly on how we created the sequence of tracks. We decided 
to place every instrument or voice in their own track to better emulate a MIDI performance. 
Additionally, this class provides various public methods to control the sequence, where this setup 
favors additional functionality with easier methods to do so. This design decision of arranging
tracks was done to organize our playback better.
<p>Changes:
Added a method in order to synch our visual and audible views by allowing external classes to access 
the instantaneous position of the sequencer. JavaDoc provided for getter to further illustrate
functionality.

```
/**
 * This method returns the instantaneous position of the {@code player} in ticks, regardless of it
 * playing or not.
 *
 * @return position of {@code Sequencer} in MIDI ticks
 */
int getBeatPosition(); 
``` 

## CompositeViewImpl
This new file, and thus this new class, provides a handy way to control our audible and visual 
views in order to present them to the user as a unified view. This allows for a single point of 
control in order to assure both views are on the correct model, as well as focusing on the correct
beat number. This class could be described as using the delegate pattern to perform its promised
functionality. This class is particularly handy for it's controller since it will be presented
as one view, which in reality delegates the desired action to the appropriate view.

## BeatRange
This class takes care of drawing a header for the scroll pane used to display the beats in the 
model, it's dimensions work together with the dimensions for the grid in order for the beat to be 
positioned at the correct place.
<p>Changes:

```
Dimension getPreferredSize(); - the preferred size was being calculated wrong, this was fixed by
                                properly using modular arithmetic to compute the dimensions based on
                                the beat number and the measure, adjusting accordingly to properly
                                position the header above the grid of notes.
```

## NoteRange
This component is in charge of creating a row header for the scroll pane. It renders all the notes 
in the current range of the model, and works in cohesion with the DrawNotes in order to place the 
correct pitch at the correct place. Taking advantage of the scroll pane to display the current 
pitches given the position of the view.

## DrawNotes
This class takes care of drawing the view used in the scroll pane. This class graphically draws the 
notes by placing the highest at the top, displaying the range of the piece, and placing them in a 
grid specified by measure. It also provides a divisor for octaves. It draws the onset black, and the
rest of the note's duration in green. It attempts to adhere to the provided view.
<p>Changes:
The event handling, that used to control the position of the red marker, was moved to the controller
package, adhering to the MVC architecture given the new requirements of the assignment.
The position of the marker is now controlled by the beat number, which is passed as a 
parameter by the controller paired to the view that uses this component. We also added a getter in
order to access the markerX instance, dictating the focus of the panel.

```
void shiftView(int shift); - this new method now calculates the x-coordinate of the marker position 
                             to be relative to the shift value passed when the method is called.
                             
int getMarkerX(); - returns the current x-coordinate of the red marker used to signal what beat we 
                    are focusing on              
```

## DrawPiano
This class takes care of graphically representing a piano with 10 octaves. It works in conjunction 
with DrawNotes to display the current notes being played, indicated by the position of the marker,
and shows that graphically by rendering them yellow. 
<p>Changes:
Moved all methods that took care of event handling to the controller packages. *Mention new methods, 
as well it is now controlled through the beatnum and shiftview*

## ViewFactory
This class is a provides a single point of control for making IView objects. It supports three 
options for views, and produces an IView based on the type provided. Current views supported include
"console", "visual", "midi", and "composite." Each view will display the model provided to the ViewFactory 
constructor. The default output for console is set to System.out.
<p>Changes: 
The only changes made to this class where adding a new case statement in order to create a view that
fits our new type, "composite."

# Test Classes
Each test class states what objects it is testing, attempting to cover all edge cases.

## Testing MIDI
We would like to comment on how we tested our MIDI and our mock. Since we decided to use the 
sequencer setup for our MIDI playback, we had to figure out a way to read the messages created by 
MidiViewImpl and added to that particular instance of the sequencer gotten from MidiSystem. We 
decided to create a MockMidiViewImpl that extends our MidiViewImpl, and added a protected method
that allows the child class to access the sequence in the parent class. We did so in order to 
delegate this transmitting of messages to the class and confirm the sequences contents in the child
class. What we decided to do was reconstruct the notes of the piece entirely from the MidiMessages 
in the tracks created by our MidiViewImpl. Although the algorithm to reconstruct them took time to
create, it allowed us to identify a major change we implemented in the end. It demonstrated that 
putting all the events/MIDI messages in the same track cut the duration of certain notes when they 
where the same pitch and placed relatively close to each other. Thus, we decided to implement a map
in our MidiViewImpl to map a single instrument to a single track which demonstrated an audible 
difference. After making this change this algorithm created the desired composition from the 
MidiMessages, meaning that it helped us test our program. With this being said, the recommended 
testing for MIDI was implemented in order to create the required files for submission but we 
decided to confirm our functionality mostly on creating the notes from the track's messages and 
comparing them to the contents of the model.

## Controller Testing
To test the controllers, several controllers were made, and the functionality supported by the 
controller was tested to confirm that they operated properly. For the console view a mock
was used to confirm that the output was as expected. For the midi view, the mock midi view created
last week was used to confirm that display started playing the midi. For the gui and composite view
the key and mouse mappings were tested, and then the difference between these two mappings 
(that you can only add a note when the song is paused) was tested as well. The gui and composite 
views were not display so as to not cause gui's to pop up during testing.
