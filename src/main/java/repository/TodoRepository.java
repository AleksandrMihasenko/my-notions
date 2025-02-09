package repository;

import model.Todo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Todo todo = new Todo(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getBoolean("completed")
                );
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }
}
