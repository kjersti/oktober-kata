import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringCalculator {

    public int add(String numbers) {
        if (numbers.length() == 0) return 0;

        List<Integer> digits = new StringParser(numbers).digits();
        digits = removeGreaterThanThousand(digits);

        verifyNoNegatives(digits);

        return sum(digits);
    }

    private int sum(List<Integer> digits) {
        if (digits.isEmpty()) return 0;
        return digits.get(0) + sum(digits.subList(1, digits.size()));
    }

    private List<Integer> removeGreaterThanThousand(List<Integer> digits) {
        List<Integer> smallerThanThousand = new ArrayList<Integer>();
        for (Integer d : digits) {
            if (d <= 1000) smallerThanThousand.add(d);
        }
        return smallerThanThousand;
    }

    private void verifyNoNegatives(List<Integer> digits) {
        List<Integer> negatives = negatives(digits);
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed. " + join(negatives));
        }
    }

    private List<Integer> negatives(List<Integer> digits) {
        List<Integer> negatives = new ArrayList<Integer>();
        for (Integer d : digits) {
            if (d < 0) negatives.add(d);
        }
        return negatives;
    }

    private String join(List<Integer> negatives) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < negatives.size(); i++) {
            sb.append(negatives.get(i));
            if (i < negatives.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static class StringParser {

        private static final String DELIMITER_DEFINE = "//";
        private static final String NEWLINE = "\n";
        private final String numbers;
        private final String delimiter;

        public StringParser(String numbers) {
            String delimiter = ",|\n";
            if (numbers.startsWith(DELIMITER_DEFINE)) {
                delimiter = extractDelimiter(numbers);
                numbers = stripDelimiterDefinition(numbers);
            }
            this.numbers = numbers;
            this.delimiter = delimiter;
        }

        public List<Integer> digits() {
            List<String> strings = Arrays.asList(numbers.split(delimiter));
            List<Integer> digits = new ArrayList<Integer>();
            for (String s : strings) {
                digits.add(Integer.valueOf(s));
            }
            return digits;
        }

        private String stripDelimiterDefinition(String numbers) {
            return numbers.substring(numbers.indexOf(NEWLINE) + 1);
        }

        private String extractDelimiter(String numbers) {
            String delimiterString = numbers.substring(DELIMITER_DEFINE.length(), numbers.indexOf(NEWLINE));
            if (!delimiterString.startsWith("[")) return delimiterString;
            else return delimiterString.replaceFirst("\\[", "").replace("][", "|").replace("]", "");
        }
    }

}
