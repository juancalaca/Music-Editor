package cs3500.music.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This is a concrete implementation of {@code IBeat} that uses a {@code TreeMap<IPitch,
 * Deque<INote>>} to preserve ordering. When a new {@code INote} is added it is placed at the front
 * of the Deque based on the TreeMap. This {@code Beat} provides very quick access to all of the
 * {@code INote}s, the most recently added {@code INote}s, and the highest/lowest {@code IPitch}
 * based on the ordering of the {@code TreeMap}.
 *
 * @see INote
 * @see IPitch
 */
public class Beat implements IBeat {

  /**
   * The Map storing the {@code INote}s in the piece.
   */
  private final TreeMap<IPitch, Deque<INote>> notes;

  /**
   * Constructor initializes {@code TreeMap}.
   */
  public Beat() {
    this.notes = new TreeMap<>();
  }

  @Override
  public void add(INote note) {
    Objects.requireNonNull(note);
    IPitch pitch = note.getPitch();
    if (this.notes.containsKey(pitch)) {
      Deque<INote> deque = this.notes.get(note.getPitch());
      deque.addFirst(note);
      return;
    }
    Deque newDeque = new ArrayDeque();
    newDeque.addFirst(note);
    this.notes.put(pitch, newDeque);
  }

  @Override
  public Boolean remove(INote note) {
    Objects.requireNonNull(note);
    IPitch pitch = note.getPitch();
    if (!this.notes.containsKey(pitch)) {
      return false;
    }
    Deque<INote> deque = this.notes.get(note.getPitch());
    if (!deque.contains(note)) {
      return false;
    }
    deque.remove(note);
    if (this.notes.get(note.getPitch()).size() == 0) {
      this.notes.remove(note.getPitch());
    }
    return true;
  }

  @Override
  public List<INote> getAllNotes() {
    List notesList = new ArrayList();
    for (IPitch p : this.notes.keySet()) {
      notesList.addAll(this.notes.get(p));
    }
    return notesList;
  }

  @Override
  public List<INote> getTopNotes() {
    List notesList = new ArrayList();
    for (IPitch p : this.notes.keySet()) {
      notesList.add(this.notes.get(p).getFirst());
    }
    return notesList;
  }

  @Override
  public IPitch minPitch() {
    return this.notes.firstKey();
  }

  @Override
  public IPitch maxPitch() {
    return this.notes.lastKey();
  }
}
