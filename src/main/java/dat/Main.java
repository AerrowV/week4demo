package dat;

public class Main {
    public static void main(String[] args) {
        String key = System.getenv("API_KEY");
        DataAPIReader reader = new DataAPIReader();
        String response = reader.getDataFromURL("https://api.themoviedb.org/3/find/tt0076759?external_source=imdb_id&api_key=" + key);
        System.out.println(response);
    }
}