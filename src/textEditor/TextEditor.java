package textEditor;

import java.util.Stack;

class Selection {
    public int start, end;
    public String text;

    public Selection(int start, int end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }
}

class State {
    String text;
    int cursor;

    public State(String text, int cursor) {
        this.text = text;
        this.cursor = cursor;
    }
}

class EditorStateManager {
    private Stack<State> states = new Stack<>();
    private Stack<State> undoneStates = new Stack<>();

    public void recordState(String text, int cursor) {
        if (states.isEmpty()) {
            states.push(new State(text, cursor));
        } else {
            State peek = states.peek();
            if (!peek.text.equals(text) || peek.cursor != cursor) {
                states.push(new State(text, cursor));
            }
        }
    }

    public void recordUndoneState(String text, int cursor) {
        if (undoneStates.isEmpty()) {
            undoneStates.push(new State(text, cursor));
        } else {
            State peek = undoneStates.peek();
            if (!peek.text.equals(text) || peek.cursor != cursor) {
                undoneStates.push(new State(text, cursor));
            }
        }
    }

    public State pop() {
        if (states.isEmpty()) return null;
        return states.pop();
    }

    public State popUndoneState() {
        if (undoneStates.isEmpty()) return null;
        return undoneStates.pop();
    }

}

public class TextEditor {
    private String text = "";
    private int cursor = 0;
    private String clipBoard = "";
    private Selection selection = null;
    private EditorStateManager manager = new EditorStateManager();

    public void append(String str) {
        manager.recordState(text, cursor);
        this.delete(); // delete selection first if any
        if (cursor == 0) {
            text = str + text;
        } else if (cursor == str.length()) {
            text += str;
        } else {
            text = text.substring(0, cursor) + str + text.substring(cursor);
        }
        cursor += str.length();
    }

    public void move(int n) {
        manager.recordState(text, cursor);
        this.unselect();
        if (n <= 0) {
            cursor = 0;
        } else if (n >= text.length()) {
            cursor = text.length();
        } else {
            cursor = n;
        }
    }

    public void backspace() {
        manager.recordState(text, cursor);
        if (selection != null) {
            this.delete();
            return;
        }
        if (cursor == 0) return;
        if (cursor == text.length()) {
            text = text.substring(0, cursor - 1);
        } else {
            text = text.substring(0, cursor - 1) + text.substring(cursor);
        }
        cursor -= 1;
    }

    public Selection select(int start, int end) {
        manager.recordState(text, cursor);
        selection = new Selection(start, end, text.substring(start, end));
        return selection;
    }

    public void unselect() {
        this.selection = null;
    }

    public void delete() {
        manager.recordState(text, cursor);
        if (this.selection != null) {
            int start = selection.start;
            int end = selection.end;
            String suffix = "";
            String prefix = "";
            if (start > 0) prefix = text.substring(0, start);
            if (end < text.length() - 1) suffix = text.substring(end + 1);
            text = prefix + suffix;
            cursor = start;
            this.unselect();
        }
    }

    public String copy() {
        manager.recordState(text, cursor);
        if (this.selection != null) {
            this.clipBoard = selection.text;
            this.unselect();
            return clipBoard;
        } else {
            System.out.println("no text selected");
            return "";
        }
    }

    public void undo() {
        State prevState = manager.pop();
        manager.recordUndoneState(text, cursor);
        if (prevState != null) {
            text = prevState.text;
            cursor = prevState.cursor;
        }
    }

    public void redo() {
        State prevState = manager.popUndoneState();
        manager.recordUndoneState(text, cursor);
        if (prevState != null) {
            text = prevState.text;
            cursor = prevState.cursor;
        }
    }

    public void paste() {
        manager.recordState(text, cursor);
        append(clipBoard);
    }

    public String getText() {
        return text;
    }

    public int getCursor() {
        return cursor;
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "{" +
                "text='" + text + '\'' +
                ", text-length=" + text.length() +
                ", cursor=" + cursor +
                ", clip-board=" + clipBoard +
                '}';
    }
}
