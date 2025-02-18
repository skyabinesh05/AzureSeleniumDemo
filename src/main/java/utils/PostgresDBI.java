package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDBI {
    private static final String URL = "jdbc:postgresql://tsstawrdscdata01.tibco-st.test.sky.aws:2525/tibco";
    //    tsstawrdscdata01.tibco-st.test.sky.aws
    private static final String USER = "om_qa";
    private static final String PASSWORD = "f4Odilipk63VIt3eb9";

    private static final String URLIT = "jdbc:postgresql://tsitawrdscdata01.tibco-it.test.sky.aws:2525/tibco";

    //    tsstawrdscdata01.tibco-st.test.sky.aws
    private static final String USERIT = "om_qa";
    private static final String PASSWORDIT = "96TM2ooKx6QJhDKFgR";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getConnectionIT() throws SQLException {
        return DriverManager.getConnection(URLIT, USERIT, PASSWORDIT);
    }

}
