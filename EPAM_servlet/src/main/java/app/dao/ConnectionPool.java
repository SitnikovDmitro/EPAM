package app.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Class for managing connections with database
 * Make database operations work faster
 */
public class ConnectionPool {
    private final MysqlDataSource ds;

    private ConnectionPool() throws IOException, URISyntaxException {

        JsonNode node = new ObjectMapper().readTree(getClass().getClassLoader().getResource("db.json"));

        ds = new MysqlConnectionPoolDataSource();
        ds.setPassword(node.get("password").asText());
        ds.setUser(node.get("user").asText());
        ds.setURL(node.get("url").asText());
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance(){
        try {
            if (instance==null) instance = new ConnectionPool();
        } catch (IOException | URISyntaxException e) {
            throw new Error(e);
        }

        return instance;
    }

    /**
     * @return connection from connection pool
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
