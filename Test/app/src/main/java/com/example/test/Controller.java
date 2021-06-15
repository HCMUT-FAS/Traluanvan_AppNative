package com.example.test;

import java.sql.Connection;

public class Controller {
        Databaseonlinecontroller Database = new Databaseonlinecontroller();

        public Connection ConnnectionData() {
            return Database.getConnectionOf();
        }

}
