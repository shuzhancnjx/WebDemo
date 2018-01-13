package com.gradle.MySql;

import java.sql.*;

public class SqlOperations {

    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String dbAddress = "jdbc:mysql://localhost:3306/";
//    private String userPass ="?user=root&password=";
    private static String userPass = "?useSSL=false";

    private static String userName="root";
    private static String passWord ="";

    final static String tableName1 = "KeyStrokeLog";
    final static String tableName2 = "Essay";
    final String dbName = "Log";

    private Statement statement;
    private Connection connection;

    public SqlOperations(){

        try{
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress+dbName, userName, passWord);
            connection.close();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            createDatabase();
            createTable(tableName1);
            createTable(tableName2);
        }
    }

    private Connection connectToDB(String dbName) throws ClassNotFoundException,SQLException {
        Class.forName(jdbcDriver);
        Connection connection = DriverManager.getConnection(dbAddress+dbName, userName, passWord);
        return  connection;
    }

    private void createDatabase(){
        try{
            Connection connection = connectToDB("");
            statement = connection.createStatement();
            int result = statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database created");
            connection.close();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    private void createTable(String tableName){
        try{
            Connection connection = connectToDB(dbName);
            statement = connection.createStatement();

            String sqlOfCreateTable = "CREATE TABLE " + tableName
                    + " ("
                    + "ID BIGINT NOT NULL AUTO_INCREMENT,"
                    + "uuid VARCHAR(20) NOT NULL,"
                    + "records TEXT,"
                    + "PRIMARY KEY (ID)"
                    + ")";

            statement.executeUpdate(sqlOfCreateTable);
            System.out.println("Table created");
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }

    public void writeToLogTable(String uuid, String jsonString, String tableName){

        try{
            Connection connection = connectToDB(dbName);

            String query = " INSERT INTO "
                    + tableName
                    + " (uuid, records)"
                    + " values (?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, jsonString);

            int res = preparedStatement.executeUpdate();

            System.out.println(uuid + " "+ jsonString + " "+ "Inserted");

            connection.close();

        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void selectFromLogTable(String uuid, String tableName){

        try{
            Connection connection = connectToDB(dbName);
            statement = connection.createStatement();

            String query = "SELECT uuid, records FROM "
                    + tableName
                    + " WHERE uuid = '"
                    + uuid + "'";

            ResultSet resultSet = statement.executeQuery(query);
            System.out.println(resultSet.getMetaData() );
            connection.close();

        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
