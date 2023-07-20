/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author NIDULA
 */
public class MySQL {

    private static Connection C;
    private static final String DATABASE = "nshop";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "nidula12#";

    private static Statement createConnection() throws Exception {
        if (C == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            C = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE , USER_NAME , PASSWORD);
        }
        Statement s = C.createStatement();
        return s;
    }

    public static ResultSet search(String query) throws Exception {
        ResultSet r = createConnection().executeQuery(query);
        return r;
    }

    public static void iud(String query) {
        try {
            createConnection().executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
