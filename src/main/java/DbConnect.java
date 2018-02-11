import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConnect {

    public static void main(String[] args) {

        System.out.println("SQL execute");

       /* FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/java/db.properties");
            property.load(fis);

            String host = property.getProperty("dbhost");
            String login = property.getProperty("dblogin");
            String passwd = property.getProperty("dbpass");

            System.out.println("HOST: " + host  + ", LOGIN: " + login + ", PASSWORD: " + passwd);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
*/
    }

    public void sqlInsertQuerty(String resultSqlString) {

        try {
            FileInputStream fis;
            Properties property = new Properties();

            fis = new FileInputStream("src/main/resources/db.properties");

            property.load(fis);

            String dbhost = property.getProperty("dbhost");
            String dblogin = property.getProperty("dblogin");
            String dbpasswd = property.getProperty("dbpass");

            try {

                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://" + dbhost;
                String login = dblogin;
                String password = dbpasswd;
                Connection con = DriverManager.getConnection(url, login, password);
                try {
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(resultSqlString);

                    stmt.close();
                } finally {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

    }
    public ArrayList sqlSelectQuerty() {
        ArrayList<String> timeInBase = new ArrayList<>();

        try {
            FileInputStream fis;
            Properties property = new Properties();

            fis = new FileInputStream("src/main/resources/db.properties");
            //fis = new FileInputStream("db.properties");

            property.load(fis);

            String dbhost = property.getProperty("dbhost");
            String dblogin = property.getProperty("dblogin");
            String dbpasswd = property.getProperty("dbpass");

            try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://" + dbhost;
                String login = dblogin;
                String password = dbpasswd;
                Connection c = DriverManager.getConnection(url, login, password);
                try {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM maximum ORDER BY id DESC LIMIT 10;");
                    while (rs.next()) {
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
        }
        catch (IOException e) {
            System.err.println("ERROR: file not found!");
        }

         return timeInBase;

        }

    }