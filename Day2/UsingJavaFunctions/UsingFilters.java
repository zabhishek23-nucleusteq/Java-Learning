package Day2.UsingJavaFunctions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UsingFilters implements WordAnalyzer {
    private final WordNormalizer normalizer;
    private final Predicate<String> filter;

    public UsingFilters(WordNormalizer normalizer, Predicate<String> filter) {
        this.normalizer = normalizer;
        this.filter = filter;
    }
    @Override
    public List<Map.Entry<String, Long>> analyze(String filePath, int topN) throws IOException {
        List<String> words = Files.lines(Paths.get(filePath))
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(normalizer::normalize)
                .filter(filter)
                .collect(Collectors.toList());

        Map<String, Long> wordCounts = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
}
