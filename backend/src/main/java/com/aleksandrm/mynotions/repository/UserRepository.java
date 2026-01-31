package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setFullName(rs.getString("full_name"));
                u.setRole(rs.getString("role"));
                u.setActive(rs.getBoolean("is_active"));

                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    u.setCreatedAt(createdAt.toLocalDateTime());
                }

                Timestamp updatedAt = rs.getTimestamp("updated_at");
                if (updatedAt != null) {
                    u.setUpdatedAt(updatedAt.toLocalDateTime());
                }

                return u;
            }, email);

            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public User save(User user) {
        String sql = "INSERT INTO users (email, password_hash, full_name, role, is_active) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING id, email, password_hash, full_name, role, is_active, created_at, updated_at";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    User u = new User();
                    u.setId(rs.getLong("id"));
                    u.setEmail(rs.getString("email"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setFullName(rs.getString("full_name"));
                    u.setRole(rs.getString("role"));
                    u.setActive(rs.getBoolean("is_active"));

                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        u.setCreatedAt(createdAt.toLocalDateTime());
                    }

                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    if (updatedAt != null) {
                        u.setUpdatedAt(updatedAt.toLocalDateTime());
                    }

                    return u;
                },
                user.getEmail(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getRole(),
                user.isActive()
        );
    }
}
