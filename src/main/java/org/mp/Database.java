package org.mp;

import java.sql.*;

public class Database {
    public Connection qnap = null;

    public void Connect(){

        String url = "jdbc:mysql://192.168.0.8:3306/MP";
        String user = "MP_DEV";
        String passwd = "MP_DEV";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            qnap = DriverManager.getConnection(url, user, passwd);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
    public Connection getQnap() {
        return qnap;
    }
}
