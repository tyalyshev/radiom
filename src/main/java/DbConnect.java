import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbConnect {

    public static void main(String[] args) {

        System.out.println("SQL execute");

    }
    public void sqlInsertQuerty(String resultSqlString) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://192.168.31.149:5432/radiom";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(resultSqlString);

                stmt.close();
            }   finally {
                con.close();
            }

            }   catch (Exception e) {
                e.printStackTrace();
            }
    }
    public ArrayList sqlSelectQuerty() {
        ArrayList<String> timeInBase = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://192.168.31.149:5432/radiom";
            String login = "postgres";
            String password = "postgres";
            Connection c = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM maximum ORDER BY id DESC LIMIT 10;" );
                while (rs.next()){
                    timeInBase.add(rs.getString("dtime"));
                }
                rs.close();
                stmt.close();

            } finally {
                c.close();

            }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

         return timeInBase;

        }

    }











