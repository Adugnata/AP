package oscarWinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class WinnersOpsDB {
    private static Function<String, Winner> mapToWinner = (line) -> {
        String[] p = line.split(",");
        return new OscarWinner(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), p[3].replace("\"", ""), p[4].replace("\"", ""));
    };

    public static Stream<Winner> loadData(String[] dbs) throws FileNotFoundException, IOException {
        List<Stream<Winner>> l = new ArrayList<>();
        for (String db : dbs) {
            Stream<Winner> inputStream = Files.lines(Paths.get(db)).skip(1).map(mapToWinner); //an auxiliary function is used to properly map the lines to the Winner objects
            l.add(inputStream);
        }
        Stream<Winner>[] o = (Stream<Winner>[]) new Stream<?>[l.size()];
        return Stream.of(l.toArray(o)).flatMap(x -> x); //flatMap is used to merge the different source files into a single stream
    }

    public static Stream<Winner> youngWinners(Stream<Winner> str) {
        return str.filter(e -> e.getWinnerAge() < 35).sorted(Comparator.comparing(Winner::getWinnerName));
    }

    public static Stream<Winner> extremeWinners(Stream<Winner> str) {
        List<Winner> sortedStream = str.sorted(Comparator.comparing(Winner::getWinnerAge)).collect(Collectors.toList());
        return Stream.of(sortedStream.get(sortedStream.size() - 1), sortedStream.get(0));
    }

    public static Stream<String> multiAwardedPerson(Stream<Winner> str) {
        List<String> people = str.map(item -> item.getWinnerName()).collect(Collectors.toList());
        return people.stream().filter(i -> Collections.frequency(people, i) > 1).distinct().sorted(Comparator.comparing(String::toString));
    }

    public static Stream<String> multiAwardedFilm(Stream<Winner> str) {
        List<String> movies = str.sorted(Comparator.comparing(Winner::getYear)).map(item -> item.getFilmTitle()).collect(Collectors.toList());
        return movies.stream().filter(i -> Collections.frequency(movies, i) > 1).distinct();
    }

    public static Stream<Winner> youngWinnersParallel(Stream<Winner> str) {
        return str.parallel().filter(e -> e.getWinnerAge() < 35).sorted(Comparator.comparing(Winner::getWinnerName));
    }

    public static Stream<Winner> extremeWinnersParallel(Stream<Winner> str) {
        List<Winner> sortedStream = str.parallel().sorted(Comparator.comparing(Winner::getWinnerAge)).collect(Collectors.toList());
        return Stream.of(sortedStream.get(sortedStream.size() - 1), sortedStream.get(0));
    }

    public static Stream<String> multiAwardedPersonParallel(Stream<Winner> str) {
        List<String> people = str.parallel().map(item -> item.getWinnerName()).collect(Collectors.toList());
        return people.parallelStream().filter(i -> Collections.frequency(people, i) > 1).distinct().sorted(Comparator.comparing(String::toString));
    }

    public static Stream<String> multiAwardedFilmParallel(Stream<Winner> str) {
        List<String> movies = str.parallel().sorted(Comparator.comparing(Winner::getYear)).map(item -> item.getFilmTitle()).collect(Collectors.toList());
        return movies.parallelStream().filter(i -> Collections.frequency(movies, i) > 1).distinct();
    }

    public static <T, U> long measure(Function<Stream<T>, Stream<U>> f, Stream<T> s1) {
        Stream<U> s2 = f.apply(s1);
        long startTime = System.nanoTime();
        s2.collect(Collectors.toList());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static <T, U> LongStream runJobs(Stream<Function<Stream<T>, Stream<U>>> stream, Stream<T> s) {
        List<T> lst = s.collect(Collectors.toList()); //the stream must be consumed multiple times
        LongStream ls = stream.mapToLong(fun -> measure(fun, lst.stream()));
        return ls;
    }

    public static long[] comparison(Stream<Winner> s) {

        List<Function<Stream<Winner>, Stream<Winner>>> jobsListW = new ArrayList(); //functions from Stream<Winner> to Stream<Winner>
        List<Function<Stream<Winner>, Stream<String>>> jobsListS = new ArrayList(); //functions from Stream<Winner> to Stream<String>
        jobsListW.addAll(Arrays.asList((e) -> (youngWinners(e)), (e) -> (extremeWinners(e)), (e) -> (youngWinnersParallel(e)), (e) -> (extremeWinnersParallel(e))));
        jobsListS.addAll(Arrays.asList((e) -> (multiAwardedPerson(e)), (e) -> (multiAwardedFilm(e)), (e) -> (multiAwardedPersonParallel(e)), (e) -> (multiAwardedFilmParallel(e))));
        List<Winner> lst = s.collect(Collectors.toList());
        LongStream ls1 = runJobs(jobsListW.subList(0, 4).stream(), lst.stream());
        LongStream ls2 = runJobs(jobsListS.subList(0, 4).stream(), lst.stream());
        List<Long> ll1 = ls1.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<Long> ll2 = ls2.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        //the array is built as specified; unspecified elements at indices 0,1,6 are given 0 as value
        return new long[]{0, 0, (long) ll1.get(0), (long) ll1.get(1), (long) ll2.get(0), (long) ll2.get(1), 0, (long) ll1.get(2), (long) ll1.get(3), (long) ll2.get(2), (long) ll2.get(3)};
    }
}