package ru.pin120.demoSpring;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBHelper {
//    final static String DB_URL =
//            "jdbc:mysql://localhost:3306/policlinicdb"; /*База данных*/
//    final static String LOGIN = "root";
//    final static String PASSWORD = "root";
//    static private Connection connection;
//
//
//
//    public static void connect(){
//        try{
//            connection = DriverManager.getConnection(DB_URL,LOGIN, PASSWORD);
//        }
//        catch (SQLException ex){
//            System.out.println(ex.getMessage());
//            System.out.println("Connection error");
//        }
//    }
//    public static void disconnect() throws SQLException {
//        connection.close();
//    }
//    public static Connection getConnection(){
//        return connection;
//    }
//
////    @SneakyThrows
////    @Override
////    public void close() throws IOException {
////        connection.close();
////    }
}
