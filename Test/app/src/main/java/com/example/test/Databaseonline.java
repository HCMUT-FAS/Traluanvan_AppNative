package com.example.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Databaseonline {
    public static final String TABLE_NAME = "luanVan";
    public static final String Col_PRIMARY_KEY = "LV_Ma";
    public static final String COL_2 = "LV_Ten";
    public static final String COL_3 = "LV_TenTiengAnh";
    public static final String COL_4 = "SV1_Ten";
    public static final String COL_5 = "MSSV1";
    public static final String COL_6 = "SV2_Ten";
    public static final String COL_7 = "MSSV2";
    public static final String COL_8 = "GV1_Ten";
    public static final String COL_9 = "GV2_Ten";

    private  Connection connection;
    public List<ViewInterface> getAllData() throws SQLException {
        List<ViewInterface> list = new ArrayList<>();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from luanVan";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new ViewInterface(
                    rs.getInt(Col_PRIMARY_KEY),
                    rs.getString(COL_2),
                    rs.getString(COL_3),
                    rs.getString(COL_4),
                    rs.getString(COL_5),
                    rs.getString(COL_6),
                    rs.getString(COL_7),
                    rs.getString(COL_8),
                    rs.getString(COL_9)
            ));// Đọc dữ liệu từ ResultSet
        }
        connection.close();// Đóng kết nối
        return list;
    }

    }
