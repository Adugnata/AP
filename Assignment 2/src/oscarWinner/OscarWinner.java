package oscarWinner;

public class OscarWinner implements Winner {
    int index;
    int year;
    int age;
    String name;
    String movie;

    public OscarWinner(int i, int y, int a, String n, String m){
        index = i;
        year = y;
        age = a;
        name = n;
        movie = m;
    }

    @Override
    public int getYear(){
        return year;
    }

    @Override
    public int getWinnerAge(){
        return age;
    }

    @Override
    public String getWinnerName(){
        return name;
    }

    @Override
    public String getFilmTitle(){
        return movie;
    }
}