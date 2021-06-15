package com.example.test;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressLint("NewApi")
public class Databaseonlinecontroller {
        private static String ip = "remotemysqpl.com";
        private static String port = "3306";
        private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
        private static String database = "11pvv4O6sJ";
        private static String username = "11pvv4O6sJ";
        private static String password = "NWnV8A7TuL";
        private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
        public Connection getConnectionOf() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            try {
                Class.forName(Classes);
                connection = DriverManager.getConnection(url, username,password);
            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return connection;
        }


}
