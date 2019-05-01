package org.hsw.model;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class BConnection implements Closeable {
    private static Logger logger = Logger.getLogger(BConnection.class);
    private String filePath;
    private Connection conn;
    private Statement statement;
    private ResultSet resultset;

    public BConnection(String filePath) {
        this.filePath = filePath;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            this.conn = conn;
        } catch (SQLException e) {
            logger.error("Fail to connection to database!", e);
        }
    }

    public ResultSet executeSql(String sql) throws SQLException {
        closeResultSet();
        closeStatement();
        statement = conn.createStatement();
        resultset = statement.executeQuery(sql);
        return resultset;
    }

    private void closeResultSet() {
        if (this.resultset != null) {
            try {
                resultset.close();
            } catch (SQLException e) {
                logger.warn("Close database result set failed!", e);
            }
        }
    }

    private void closeStatement() {
        if (this.statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Close database result set failed!", e);
            }
        }
    }

    private void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                logger.warn("Close database connection failed!", e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        closeResultSet();
        closeStatement();
        closeConnection();
    }
}
