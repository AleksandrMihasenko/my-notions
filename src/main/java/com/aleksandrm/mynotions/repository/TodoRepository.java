package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Todo> getTodos() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Todo(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getBoolean("completed")
                )
        );
    }

    public void addTodo(Todo todo) {
        String sql = "INSERT INTO todos (task, completed) VALUES (?, ?)";
        jdbcTemplate.update(sql, todo.getTask(), todo.isCompleted());
    }

    public void updateTodo(Long id, boolean completed) {
        String sql = "UPDATE todos SET completed = ? WHERE id = ?";

        jdbcTemplate.update(sql, completed, id);
    }

    public void deleteTodo(Long id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
