import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class  Main {
    public static void main(String[] args) {

        String radiourl = "http://www.maximum.ru/currenttrack.aspx?station=maximum";
        URL url = createUrl(radiourl);
        String resultSting = parseUrl(url);

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(resultSting).getAsJsonObject();
        JsonArray questions = mainObject.getAsJsonArray("History");
        JsonObject currObj = mainObject.getAsJsonObject("Current");

        System.out.println("CURRENT \n");
        System.out.println(currObj.get("Artist").getAsString());
        System.out.println(currObj.get("Song").getAsString());
        System.out.println(currObj.get("StartTime").getAsString());
        System.out.println("HISTORY \n");

        DbConnect pg = new DbConnect();

        for (JsonElement user : questions) {
            JsonObject userObject = user.getAsJsonObject();

            String asr = userObject.get("Artist").getAsString();
            String asrreg = asr.replace("\'","\''");
            String ssr = userObject.get("Song").getAsString();
            String ssrreg = ssr.replace("\'","\''");
            String strreg = userObject.get("StartTime").getAsString();

            System.out.println(userObject.get("Artist").getAsString());
            System.out.println(userObject.get("Song").getAsString());
            System.out.println(userObject.get("StartTime").getAsString());


            String sqlExecute = "INSERT INTO maximum VALUES (nextval('auto_rows_id'), '" + asrreg + "','" + ssrreg + "','" + strreg + "')";

            //System.out.println(sqlExecute);

            pg.sqlInsertQuerty(sqlExecute);

        }



    }

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

}