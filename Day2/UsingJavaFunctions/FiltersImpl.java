package Day2.UsingJavaFunctions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FiltersImpl {
    public static void main(String[] args) throws IOException {
        WordNormalizer normalizer = word -> word.toLowerCase().replaceAll("[^a-z]", "");
        Predicate<String> nonEmpty = word -> !word.isEmpty();

        WordAnalyzer analyzer = new UsingFilters(normalizer, nonEmpty);
        List<Map.Entry<String, Long>> result = analyzer.analyze("input.txt", 5);

        result.forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
    }
}
