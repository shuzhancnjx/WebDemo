package com.gradle.MySql;

import java.sql.*;

public class CreateTable {

    private String jdbcDriver = "com.mysql.jdbc.Driver";
    private String dbAddress = "jdbc:mysql://localhost:3306/";
    private String userPass ="?user=root&password=";
    private String userName="root";
    private String passWard ="";
    private String dbName;
    private String sqlOfCreateTable;

    private Statement statement;
    private Connection connection;

    public CreateTable(String dbName, String sqlOfCreateTable){
        this.dbName = dbName;
        this.sqlOfCreateTable = sqlOfCreateTable;

        try{
            Class.forName(jdbcDriver);
            DriverManager.getConnection(dbAddress+dbName, userName, passWard);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            createDatabase();
            createTable();
        }
    }

    private void createDatabase(){
        try{
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress+userPass);
            statement = connection.createStatement();
            int result = statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    private void createTable(){
        try{
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress+dbName, userName, passWard);
            statement = connection.createStatement();
            statement.executeUpdate(sqlOfCreateTable);
            System.out.println("Table created");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }

}
