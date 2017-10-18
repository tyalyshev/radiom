import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

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

}









