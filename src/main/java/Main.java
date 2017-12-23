import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class  Main {
    public static void main(String[] args) {

        String radiourl = "http://www.maximum.ru/currenttrack.aspx?station=maximum";
        URL url = createUrl(radiourl);
        String resultSting = parseUrl(url);

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(resultSting).getAsJsonObject();
        JsonArray questions = mainObject.getAsJsonArray("History");
        //JsonObject currObj = mainObject.getAsJsonObject("Current");

        ArrayList<String> artistList = new ArrayList<>();
        ArrayList<String> songList = new ArrayList<>();
        ArrayList<String> starttimeList = new ArrayList<>();


        System.out.println("CURRENT \n");
        //System.out.println(currObj.get("Artist").getAsString());
        //System.out.println(currObj.get("Song").getAsString());
        //System.out.println(currObj.get("StartTime").getAsString());
        System.out.println("HISTORY \n");

        DbConnect pg = new DbConnect();

        ArrayList<String> starttimeListDb = pg.sqlSelectQuerty();

        for (JsonElement user : questions) {
            JsonObject userObject = user.getAsJsonObject();

            String asr = userObject.get("Artist").getAsString();
            String asrreg = asr.replace("\'", "\''");
            String ssr = userObject.get("Song").getAsString();
            String ssrreg = ssr.replace("\'", "\''");
            String strreg = userObject.get("StartTime").getAsString();

            //System.out.println(userObject.get("Artist").getAsString());
            //System.out.println(userObject.get("Song").getAsString());
            //System.out.println(userObject.get("StartTime").getAsString());

            artistList.add(asrreg);
            songList.add(ssrreg);
            starttimeList.add(strreg);


            //String sqlExecute = "INSERT INTO maximum VALUES (nextval('auto_rows_id'), '" + asrreg + "','" + ssrreg + "','" + strreg + "')";

            //System.out.println(sqlExecute);

            //pg.sqlInsertQuerty(sqlExecute);

        }



        ArrayList<String> reversArtistList = revers(artistList);
        ArrayList<String> reversSongList = revers(songList);
        ArrayList<String> reverStartTimetList = revers(starttimeList);
        ArrayList<String> reverStartTimetListDb = starttimeListDb;
        //ArrayList<String> reverStartTimetListDb = revers(starttimeListDb);
        ArrayList<String> reverStartTimetListNz = new ArrayList<>();

        for (int i = 0 ; i < reverStartTimetList.size(); i++){
            reverStartTimetListNz.add(timeNormalize(reverStartTimetList.get(i)));
        }
        reverStartTimetList = reverStartTimetListNz;


/*
        for (int i = 0; i < reversArtistList.size(); i++){

            String sqlExecute = "INSERT INTO maximum VALUES (nextval('auto_rows_id'), '" + reversArtistList.get(i) +
                    "','" + reversSongList.get(i) + "','" + reverStartTimetList.get(i) + "')";
             pg.sqlInsertQuerty(sqlExecute);
        }
*/

        for (int i = 0; i < reverStartTimetList.size(); i++) {
            boolean istina = true;
            String s = reverStartTimetList.get(i);
            for (int j = 0; j < reverStartTimetList.size(); j++) {

                if (s.equals(reverStartTimetListDb.get(j))) {
                    System.out.println(s + " == " + reverStartTimetListDb.get(j));
                    istina = false;
                }
                else {
                    System.out.println( s +  "!==" + reverStartTimetListDb.get(j));
                }



            }
            if (istina) {
                pg.sqlInsertQuerty("INSERT INTO maximum VALUES (nextval('auto_rows_id'), '" + reversArtistList.get(i) +
                        "','" + reversSongList.get(i) + "','" + reverStartTimetList.get(i) + "')");
            }
        }
    }


// private function

    private static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static ArrayList<String> revers(ArrayList<String> list) {

        ArrayList<String> result = new ArrayList<>();
        int b = list.size() - 1;
        for (int i = 0; i < list.size(); i++) {

            result.add(list.get(b - i));
        }
        return result;

    }

    private static String timeNormalize(String r) {
        String rr = r.replace("T", " ");
        String rrr = rr.substring(0, 19);
        return rrr;
    }

 }