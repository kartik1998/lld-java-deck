package textEditor;

class Selection {
    public int start, end;
    public String text;

    public Selection(int start, int end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }
}

public class TextEditor {
    private String text = "";
    private int cursor = 0;
    private String clipBoard = "";
    private Selection selection = null;

    public void append(String str) {
//        if (selection != null) throw new Error("cannot append if there is a selection");
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
        if (selection != null) throw new Error("cannot backspace if there is a selection");
        if (cursor == 0) return;
        if (cursor == text.length()) {
            text = text.substring(0, cursor - 1);
        } else {
            text = text.substring(0, cursor - 1) + text.substring(cursor);
        }
        cursor -= 1;
    }

    public Selection select(int start, int end) {
        selection = new Selection(start, end, text.substring(start, end));
        return selection;
    }

    public void unselect() {
        this.selection = null;
    }

    public String copy() {
        if (this.selection != null) {
            this.clipBoard = selection.text;
            return clipBoard;
        } else {
            System.out.println("no text selected");
            return "";
        }
    }

    public void paste() {
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
                ", textLength=" + text.length() +
                ", cursor=" + cursor +
                '}';
    }
}
