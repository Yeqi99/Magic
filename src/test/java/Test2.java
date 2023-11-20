import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static List<String> parseCode(String code) {
        List<String> outData = new ArrayList<>();

        while (code.charAt(0) == ' ') {
            code = code.substring(1);
        }

        boolean putBegin = false;
        int begin = 0;
        int length = 0;
        int bracketNumber = 0;

        for (int end = 0; end < code.length(); end++) {
            char currentChar = code.charAt(end);
            if (currentChar == '(') {
                bracketNumber++;
            } else if (currentChar == ')') {
                bracketNumber--;
            } else if (currentChar == ' ' && bracketNumber == 0) {
                if (!putBegin || (length != 0 && length != 1)) {
                    putBegin = true;
                    outData.add(code.substring(begin, begin + length));
                }
                begin += length;
                length = 0;
            }
            length++;
        }

        if (begin != code.length()) {
            outData.add(code.substring(begin));
        }

        for (int index = 0; index < outData.size(); index++) {
            String item = outData.get(index);
            if (item.charAt(0) != ' ') {
                continue;
            }
            outData.set(index, item.substring(1));
        }

        return outData;
    }

    public static void main(String[] args) {
        String code = "    A(1 b(12 3) 23)   B C(1 b(12 3) 23) D    asda      E( )";
        List<String> parsedCode = parseCode(code);

        for (String item : parsedCode) {
            System.out.println(item);
        }
    }
}
