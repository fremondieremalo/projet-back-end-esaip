package org.sebsy.compta.infra;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class Db {
    private static final HikariDataSource DATA_SOURCE;
    static {
        DATA_SOURCE = new HikariDataSource();
        DATA_SOURCE.setJdbcUrl(Env.required("DB_URL"));
        DATA_SOURCE.setUsername(Env.required("DB_USER"));
        DATA_SOURCE.setPassword(Env.required("DB_PASSWORD"));
        DATA_SOURCE.setMaximumPoolSize(13);
        DATA_SOURCE.setConnectionTimeout(2000);
        DATA_SOURCE.setLeakDetectionThreshold(20000); // 20 secondes
    }
    private Db() {}
    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }
}