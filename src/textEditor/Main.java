package textEditor;

// In an editor's context you should be clear about the cursor and text
// For text "HELLO, WORLD", cursor positions (marked in braces) will look like:
// "{0}H{1}E{2}L{3}L{4}O{5},{6}(space){7}W{8}O{9}R{10}L{11}D{12}"

public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.append("HELLO, WORLD");
        editor.move(5); // move cursor to 5th position
        editor.backspace();
        editor.backspace();
        editor.backspace();
        editor.backspace();
        editor.backspace();
        editor.append("NOW");
        editor.print();
    }
}
