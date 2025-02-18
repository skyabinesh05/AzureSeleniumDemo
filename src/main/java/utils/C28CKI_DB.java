package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class C28CKI_DB {
    private static final String URL = "jdbc:oracle:thin:@colkfx28db1:4521:C28CKI1";
    private static final String USER = "cki_user ";
    private static final String PASSWORD = "cki_userfx2016";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
