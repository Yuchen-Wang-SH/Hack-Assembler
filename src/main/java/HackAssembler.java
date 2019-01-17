import java.io.*;

public class HackAssembler {

    public static String compile(String path) {
        firstPass(path);
        return secondPass(path);
    }

    private static String secondPass(String path) {
        LineParser.resetNumLine();
        StringBuilder machineCode = new StringBuilder();
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String assemblyLine = bufferedReader.readLine();
                // EOF
                if (assemblyLine == null) {
                    break;
                }
                LineParser line = new LineParser(assemblyLine);
                // Only A and C instructions can produce machine code.
                if (line.getType() == null || line.getType() == Command.L_COMMAND) {
                    continue;
                }
                machineCode.append(line.toCode()).append("\n");
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return machineCode.toString();
    }

    private static void firstPass(String path) {
        // First pass.
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String assemblyLine = bufferedReader.readLine();
                // EOF
                if (assemblyLine == null) {
                    break;
                }
                LineParser line = new LineParser(assemblyLine);
                // Update symbol table is implicitly handled in the constructor of LineParser.
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0 || !args[0].split("\\.")[1].equals("asm")) {
            throw new IllegalArgumentException("Must specify a valid .asm file!");
        }
        String machineCode = compile(args[0]);
        String outputFilePath = args[0].split("\\.")[0] + ".hack";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            writer.write(machineCode);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
