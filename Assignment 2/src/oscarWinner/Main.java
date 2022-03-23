package oscarWinner;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static oscarWinner.WinnersOpsDB.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] path = {"oscar_age_female.csv", "oscar_age_male.csv"};
        Stream<Winner> inputStream = loadData(path);
        multiAwardedFilm(inputStream).forEach(System.out::println);
        inputStream = loadData(path);
        extremeWinners(inputStream).forEach(e -> System.out.println(e.getWinnerName() + "   " + e.getWinnerAge()));
        inputStream = loadData(path);
        Arrays.stream(comparison(inputStream)).forEach(e -> System.out.println(e));

    }
}
