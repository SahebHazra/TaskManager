package com.env;

public class Env {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taskdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "saheb";

    public static String getDB_URL() {
        return DB_URL;
    }

    public static String getDB_USER() {
        return DB_USER;
    }

    public static String getDB_KEY() {
        return DB_PASSWORD;
    }
}