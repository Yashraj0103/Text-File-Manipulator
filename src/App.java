import java.util.*;
import java.io.*;

class App {
    static Scanner sc = new Scanner(System.in);
    static int SizeOfFile;
    static File files;
    static LinkedList ContantOfList;
    static String Lines[];
    static int indexno = 0;
    static Stack ClipBoard;
    static boolean Answer = false;

    public static void main(String[] args) throws Exception {
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(
                "                                           T E X T   F I L E   M A N I P U L A T O R                                                    ");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Enter File Name in which you want changes : ");
        String Name = sc.next();
        String Path = Name + ".txt";
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------------------");

        files = new File(Path);
        checkname(Name); //checks if file is exists or not
        if (Answer) {
            System.exit(0);
        }
        RandomAccessFile Randomfile = new RandomAccessFile(files, "rw");
        int Index = Randomfile.read();
        while (Index != -1) {
            System.out.print((char) Index);
            Index = Randomfile.read();
        }
        System.out.println();
        System.out.println("Length of File : " + Randomfile.length());

        SizeOfFile = (int) Randomfile.length();

        Randomfile.close();

        Lines = new String[SizeOfFile]; 
        ContantOfList = new LinkedList();
        ClipBoard = new Stack(SizeOfFile);
        int x;
        do {
            try {

                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(" PRESS :\r \n" +
                        "(1). Eliminate repeated Lines from the file \r\n" +
                        "(2). Reverse the content of file \r\n" +
                        "(3). Insert new Line \r\n" +
                        "(4). Copy text \r\n" +
                        "(5). Paste text Line\r\n" +
                        "(6). Cut the Line \r\n" +
                        "(7). Sort the content of the file\r\n" +
                        "(8). Write one file content to another file \r\n" +
                        "(9). Exit");

                x = sc.nextInt();
                System.out.println("Selected option: " + x);
                switch (x) {
                    case 1: {
                        eliminateRepeatedLine(Path);
                    }
                        break;

                    case 2: {
                        reverseContent(Path);
                    }
                        break;

                    case 3: {
                        addNewLine(Path);
                    }
                        break;

                    case 4: {
                        copytext(Path);
                    }
                        break;

                    case 5: {
                        pastetext(Path);
                    }
                        break;

                    case 6: {
                        cuttext(Path);
                    }
                        break;

                    case 7: {
                        sortcontant(Path);
                    }
                        break;

                    case 8: {
                        mergeContent(Path);
                    }
                        break;

                    case 9: {
                        System.out.println(
                                "---------------------------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.println("Thank You !!..");
                    }
                        break;

                    default: {
                        System.out.println("Invalid input");
                    }
                        break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Exiting due to Ctrl+C");
                break;
            }

        } while (x != 9);

    }

    private static void checkname(String Name) // Checks is file exists or not
    {
        String Path = Name + ".txt";
        File file = new File(Path);
        if (!file.exists()) {
            System.out.println("File does not exist : " + Path);
            System.out.println("do you want to re-enter name : yes/no");
            String Choice = sc.next();
            if (Choice.equalsIgnoreCase("NO")) {
                Answer = true;
                return;
            } else if (Choice.equalsIgnoreCase("YES")) {
                System.out.println("Enter name :");
                String name = sc.next();
                checkname(name);
            } else {
                System.out.println("Invalid Input :");
                Answer = true;
            }
        }
    }

    // eliminates repeated Lines

    private static void eliminateRepeatedLine(String Path) throws Exception {

        File OriginalFile = new File(Path);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist : " + Path);
            return;
        }
        BufferedReader Reader = new BufferedReader(new FileReader(Path));
        String Line;
        while ((Line = Reader.readLine()) != null) {
            if (!ContantOfList.contains(Line)) {
                ContantOfList.addLast(Line);
            }
        }

        Reader.close();

        BufferedWriter Writer = new BufferedWriter(new FileWriter(Path));
        Writer.close();

        File Size = new File(Path);
        System.out.println("length of file :" + Size.length());

        BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(Path));

        while (!ContantOfList.isEmpty()) {
            ContantWriter.write(ContantOfList.deletefirstString());
            ContantWriter.newLine();
        }

        ContantWriter.flush();
        ContantWriter.close();
        System.out.println("File Modified Successfully.");

    }

    // reverse the contant

    private static void reverseContent(String filePath) throws Exception {
        File originalFile = new File(filePath);
        if (!originalFile.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }
    
        // Read all lines from the file into a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
    
        // Reverse the list of lines
        Collections.reverse(lines);
    
        // Write the reversed lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    
        System.out.println("File content reversed successfully.");
    }
    

    // add new Line in file

    private static void addNewLine(String Path) throws Exception {

        File OriginalFile = new File(Path);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist: " + Path);
            return;
        }
        long Size = OriginalFile.length();
        Stack OriginalLines = new Stack((int) Size);
        Stack ModifiedLines = new Stack((int) Size);

        System.out.println("Enter the target word where you want to add Lines:");
        sc.nextLine();
        String TargetWord = sc.nextLine();

        System.out.println("Enter the new Line to replace with:");
        String NewLineToReplace = sc.nextLine();

        BufferedReader Reader = new BufferedReader(new FileReader(OriginalFile));
        String Line;

        while ((Line = Reader.readLine()) != null) {
            OriginalLines.push(Line);
        }

        Reader.close();

        while (!OriginalLines.isEmpty()) {
            String CurrentLine = OriginalLines.pop();
            if (CurrentLine.contains(TargetWord)) {
                ModifiedLines.push(NewLineToReplace);
            }
            ModifiedLines.push(CurrentLine);
        }

        BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(OriginalFile));

        while (!ModifiedLines.isEmpty()) {
            ContantWriter.write(ModifiedLines.pop());
            ContantWriter.newLine();
        }

        ContantWriter.flush();
        ContantWriter.close();

        System.out.println("File modified successfully.");
    }

    // copy text

    private static void copytext(String Path) throws Exception {

        File OriginalFile = new File(Path);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist: " + Path);
            return;
        }
        long Size = OriginalFile.length();
        Stack OriginalLines = new Stack((int) Size);

        ClipBoard.clearstack();
        ClipBoard.display();

        String SourceFile = Path;

        // Open the source file for reading
        BufferedReader SourceReader = new BufferedReader(new FileReader(SourceFile));

        String Line;

        // Read Lines from the source file and store them in a linked List
        while ((Line = SourceReader.readLine()) != null) {
            OriginalLines.push(Line);
        }

        // Close the source file
        SourceReader.close();

        System.out.println();
        System.out.println("enter Line number you want to copy :");
        int CopyLineNumber = sc.nextInt();
        ClipBoard.push(OriginalLines.peekindex(OriginalLines.stacksize() - (CopyLineNumber - 1))); // -1 to adjust for
                                                                                                   // 0-based index

        System.out.println("Stack :");
        ClipBoard.display();

        System.out.println();
        System.out.println("Copied Successfully!!");
    }

    // paste text

    private static void pastetext(String Path) throws Exception {

        File OriginalFile = new File(Path);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist: " + Path);
            return;
        }

        System.out.println("Enter Line number where do you want to paste Text :");
        int PasteLineNo = sc.nextInt();
        if (PasteLineNo <= 0) {
            System.out.println("Invalid Number");
            return;
        }

        LinkedList CopyFile = new LinkedList();
        BufferedReader SourceReader = new BufferedReader(new FileReader(Path));

        String Line;
        int Count = 1;
        // Read Lines from the source file and store them in a linked List
        while ((Line = SourceReader.readLine()) != null) {
            if (Count == PasteLineNo) {
                String NewLine = ClipBoard.peek();
                CopyFile.addLast(NewLine);
            }
            CopyFile.addLast(Line);
            Count++;
        }

        SourceReader.close();

        BufferedWriter DemoWriter = new BufferedWriter(new FileWriter(Path));
        DemoWriter.close();

        System.out.println("linked List Before:");
        CopyFile.display();

        BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(Path));
        String AddLines = CopyFile.deletefirstString();
        while (AddLines != null) {
            ContantWriter.write(AddLines);
            ContantWriter.newLine();
            AddLines = CopyFile.deletefirstString();
        }
        ContantWriter.close();
        System.out.println();
        System.out.println("linked List after:");
        CopyFile.display();
        System.out.println("Pasted Successfully!!");
    }

    // cut text
    private static void cuttext(String Path) throws Exception {

        File OriginalFile = new File(Path);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist: " + Path);
            return;
        }
        System.out.println("Line no:");
        int CutLineNo = sc.nextInt();
        if (CutLineNo <= 0) {
            System.out.println("Invalid no");
            return;
        }
        ClipBoard.clearstack();
        ClipBoard.display();

        LinkedList List = new LinkedList();

        BufferedReader Reader = new BufferedReader(new FileReader(Path));
        String Line;
        int Count = 1;

        // Read Lines from the source file and store them in a linked List
        while ((Line = Reader.readLine()) != null) {
            if (Count != CutLineNo) {
                List.addLast(Line);
            } else {
                ClipBoard.push(Line);
            }
            Count++;
        }
        Reader.close();

        BufferedWriter DemoWriter = new BufferedWriter(new FileWriter(Path));
        DemoWriter.close();

        System.out.println("linked List Before:");
        List.display();

        BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(Path));
        String AddLines = List.deletefirstString();
        while (AddLines != null) {
            ContantWriter.write(AddLines);
            ContantWriter.newLine();
            AddLines = List.deletefirstString();
        }
        ContantWriter.close();

        System.out.println();
        System.out.println("Linked List After:");
        List.display();

        System.out.println();
        System.out.println("Line is Cutted Successfully!!");

    }

    // sort the file contant
    private static void sortcontant(String filePath) throws Exception {
        File originalFile = new File(filePath);
        if (!originalFile.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }
        // Read lines from the source file into a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader sourceReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = sourceReader.readLine()) != null) {
                lines.add(line);
            }
        }
        // Display original content
        System.out.println("Old content:");
        lines.forEach(System.out::println);
        // Sort the lines numerically
        lines.sort((a, b) -> {
            try {
                return Integer.compare(Integer.parseInt(a.trim()), Integer.parseInt(b.trim()));
            } catch (NumberFormatException e) {
                return a.compareTo(b); // Fallback to lexicographical sorting if parsing fails
            }
        });
    
        // Display sorted content
        System.out.println("\nSorted content:");
        lines.forEach(System.out::println);
    
        // Write sorted lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    
        System.out.println("\nFile modified successfully!");
    }
    

    // Write one file content to another file
    private static void mergeContent(String Path) throws Exception {
        sc.nextLine();
        System.out.println("Enter source file name (which file data you want to add in file) : ");
        String Path1 = sc.nextLine();
        String Path2 = Path1 + ".txt";
        File OriginalFile = new File(Path2);
        if (!OriginalFile.exists()) {
            System.out.println("File does not exist: " + Path2);
            return;
        }
        LinkedList List = new LinkedList();
        BufferedReader Reader = new BufferedReader(new FileReader(Path));
        String OriginalLine;
        while ((OriginalLine = Reader.readLine()) != null) {
            List.addLast(OriginalLine);
        }

        Reader.close();

        BufferedReader SourceReader = new BufferedReader(new FileReader(Path2));
        String Line;
        // Read Lines from the source file and store them in a linked List
        while ((Line = SourceReader.readLine()) != null) {
            List.addLast(Line);
        }
        // Close the source file
        SourceReader.close();

        BufferedWriter Demowriter = new BufferedWriter(new FileWriter(Path));
        Demowriter.close();

        System.out.println("linked List Before:");
        List.display();

        BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(Path));
        String AddLines = List.deletefirstString();
        while (AddLines != null) {
            ContantWriter.write(AddLines);
            ContantWriter.newLine();
            AddLines = List.deletefirstString();
        }
        ContantWriter.close();

        System.out.println();
        System.out.println("Linked List After:");
        List.display();

        System.out.println();

    }
}

class LinkedList {
    static Scanner sc = new Scanner(System.in);

    class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    Node Head = null;

    void addFirst(String data) {
        Node n = new Node(data);
        if (Head == null) {
            Head = n;
        } else {
            n.next = Head;
            Head = n;
        }
    }

    void addLast(String data) {
        Node n = new Node(data);
        if (Head == null) {
            Head = n;
        } else {
            Node Temp = Head;
            while (Temp.next != null) {
                Temp = Temp.next;
            }
            Temp.next = n;
        }
    }

    void delFirst() {
        if (Head == null) {
            System.out.println("List is empty");
        } else {
            Node del = Head;
            Head = Head.next;
            del.next = null;
        }
    }

    String deletefirstString() {
        if (Head == null) {
            return null;
        } else {
            Node del = Head;
            Head = Head.next;
            del.next = null;
            return del.data;
        }

    }

    void display() {
        if (Head == null) {
            System.out.println("List is empty");
        } else {
            Node Temp = Head;
            while (Temp != null) {
                System.out.println(Temp.data);
                Temp = Temp.next;
            }
        }
    }

    Boolean isEmpty() {
        if (Head == null)
            return true;
        else
            return false;
    }

    int Size() {
        if (Head == null) {
            return 0;
        } else {
            Node Temp = Head;
            int Count = 0;
            while (Temp != null) {
                Count++;
                Temp = Temp.next;
            }
            return Count;
        }
    }

    boolean contains(String data) {
        Node Temp = Head;
        while (Temp != null) {
            if (Temp.data.equals(data)) {
                return true;
            }
            Temp = Temp.next;
        }
        return false;
    }

    void reverseAndWriteToFile(String inputPath) {
        try {
            Stack stack = new Stack(TextFileManipulator.SizeOfFile); // Create an instance of the Stack class
            BufferedReader Reader = new BufferedReader(new FileReader(inputPath));

            String Line;
            while ((Line = Reader.readLine()) != null) {
                stack.push(Line); // Push each Line onto the stack
            }

            Reader.close();
            BufferedWriter Writer = new BufferedWriter(new FileWriter(inputPath));
            Writer.close();

            BufferedWriter ContantWriter = new BufferedWriter(new FileWriter(inputPath));
            while (!stack.isEmpty()) {
                String reversedLine = stack.pop(); // Pop Lines from the stack
                ContantWriter.write(reversedLine);
                ContantWriter.newLine();
            }

            ContantWriter.close();
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Stack {
    String[] a;
    int top;

    Stack(int Size) {
        this.a = new String[Size];
        top = -1;
    }

    void push(String x) {
        if (top >= a.length - 1)
            System.out.println("overflow");
        else
            a[++top] = x;
    }

    String pop() {
        if (top == -1) {
            System.out.println("underflow");
            return null;
        } else

            return a[top--];
    }

    void display() {
        if (top == -1) {
            System.out.println("stack is empty!!");
        } else {
            for (int Index = top; Index >= 0; Index--) {
                System.out.println(a[Index] + " ");
            }
        }
    }

    int stacksize() {
        if (top == -1) {
            return 0;
        } else {
            int Count = 0;
            for (int Index = top; Index >= 0; Index--) {
                Count++;
            }
            return Count;
        }
    }

    boolean isEmpty() {
        if (top == -1)
            return true;
        else
            return false;
    }

    String peek() {
        if (top == -1)
            return null;
        else
            return (a[top]);
    }

    String peekindex(int Index) {
        if (top - Index + 1 <= -1) {
            return null;
        } else {
            return (a[top - Index + 1]);
        }
    }

    boolean clearstack() {
        if (top == -1) {
            return true;
        } else {
            while (top != -1) {
                pop();
            }
            return true;
        }
    }
}
