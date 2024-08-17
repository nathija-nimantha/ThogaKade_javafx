package org.example.db;

import org.example.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private DBConnection(){
        connection = new ArrayList<>();
    }

    private static DBConnection instance;
    private List<Customer> connection;

    public List<Customer> getConnection(){
        return connection;
    }

    public static DBConnection getInstance(){
        return null==instance?instance=new DBConnection():instance;
    }
}
