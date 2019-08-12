/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.model.Car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author tabuchikenta
 */
@Named(value = "carsBean")
@RequestScoped
public class CarsBean {

    ArrayList<Car> cars;
    Connection connection;

    /**
     * Creates a new instance of CarsBean
     */
    public CarsBean() {
    }

    public ArrayList<Car> getCars() {
        cars = new ArrayList();
        open();
        search(cars);
        close();
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    private void open() {
        final String PASS = "jdbc:derby://localhost:1527/cars";
        final String USER = "tabuchikenta";
        final String PASSWORD = "a17891789";

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = (Connection) DriverManager.getConnection(PASS, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("データベースオープンエラーです。"));
        }
    }

    private void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("データーベースクローズエラーです。"));
        }
    }

    private ArrayList<Car> search(ArrayList<Car> data) {
        try {
//テーブルから全てのアイテムを取得する
            try (Statement statement = (Statement) connection.createStatement()) {
//テーブルから全てのアイテムを取得する
                String sql = "SELECT * FROM tabuchikenta.IMPORTCARS";
                try (ResultSet rs = statement.executeQuery(sql)) {
                    while (rs.next()) {
                        String brand = rs.getString("BRAND");
                        String model = rs.getString("MODEL");
                        String color = rs.getString("COLOR");
                        int price = rs.getInt("PRICE");
//商品名、価格でPcItemオブジェクト作成、dataへ追加
                        data.add(new Car(brand, model, color, price));
                    }
                }
            }
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("データベース検索エラーです。"));
}
return data;
    }

}
