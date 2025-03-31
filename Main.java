import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("src\\tst");
    }
}