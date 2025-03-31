import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

public class Interpreter {
    private String path;

    private final String directFileData = "";

    private final int[] stack = new int[500];
    private final int[] heap = new int[500];
    int indx = 0;

    int returnSpace = 0;

    boolean onHeap = false;

    public void execute(String path) throws IOException {
        this.path = path + ".bl";

        run();
    }

    public boolean writeTxt() throws IOException {
        FileWriter fileWriter = new FileWriter("");
        fileWriter.write("");
        fileWriter.close();
        return true;
    }

    public void run() throws IOException {
        boolean repeatOverride = false;

        String fileData = Files.readString(Path.of(path)).strip();
        for (int i = 0; i < fileData.length(); i++)
            switch (fileData.charAt(i)){
                case '\\' -> onHeap = true;
                case '/' -> onHeap = false;
                case '=' -> {
                    try {
                        if (onHeap) System.out.println(heap[indx]);
                        else System.out.println(stack[indx]);
                    } catch (ArrayIndexOutOfBoundsException boundsException) {
                        repeatOverride = true;
                        if (onHeap) System.out.println(heap[heap.length - 1]);
                        else System.out.println(stack[stack.length - 1]);
                    }
                }
                case '@' -> {
                    if (!repeatOverride)
                        i = returnSpace;
                }
                case '*' -> returnSpace = i;
                case '-' -> {
                    try {
                        if (onHeap) heap[indx]--;
                        else stack[indx]--;
                    } catch (ArrayIndexOutOfBoundsException boundsException) {repeatOverride = true;}
                }
                case '+' -> {
                    try {
                        if (onHeap) heap[indx]++;
                        else stack[indx]++;
                    } catch (ArrayIndexOutOfBoundsException boundsException) {repeatOverride = true;}
                }
                case '>' -> indx++;
                case '<' -> indx--;
                case '#' -> {
                    i++;

                    boolean reverse = false;
                    String statementType = "";

                    if (fileData.charAt(i) == '!') {
                        reverse = true;
                        i++;
                    }

                    if (fileData.charAt(i) == '>') {
                        statementType = ">";
                        i++;
                    }

                    else if (fileData.charAt(i) == '<') {
                        statementType = "<";
                        i++;
                    }

                    StringBuilder num = new StringBuilder();

                    char c;
                    while ((c = fileData.charAt(i)) != '#'){
                        num.append(c);
                        i++;
                    }

                    if (reverse) {
                        if (statementType.equals(">")){
                            if (indx <= Integer.parseInt(num.toString()))
                                repeatOverride = true;
                        }
                        else if (statementType.equals("<")){
                            if (indx >= Integer.parseInt(num.toString()))
                                repeatOverride = true;
                        }
                        else if (indx != Integer.parseInt(num.toString()))
                            repeatOverride = true;
                    }
                    if (statementType.equals(">")){
                        if (indx > Integer.parseInt(num.toString()))
                            repeatOverride = true;
                    }
                    else if (statementType.equals("<")){
                        if (indx < Integer.parseInt(num.toString()))
                            repeatOverride = true;
                    }
                    else if (indx == Integer.parseInt(num.toString()))
                        repeatOverride = true;
                }
            }

        System.out.println(Arrays.toString(stack));
    }
}
