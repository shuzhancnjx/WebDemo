package com.gradle.MySql;

import lombok.Getter;

public class Query {
    static @Getter String TableName = "KeyStrokeLog";
    static @Getter String dbName = "Log";

    static @Getter String sqlOfCreateTable = "CREATE TABLE " + TableName
            + " ("
            + "ID INT(64) NOT NULL AUTO_INCREMENT,"
            + "records TEXT,"
            + "PRIMARY KEY (ID)"
            + ")";
    
    static @Getter String sqlOfSelectResult;
    static @Getter String sqlOfInsertResult;


}
