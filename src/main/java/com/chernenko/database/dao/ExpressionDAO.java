package com.chernenko.database.dao;

import com.chernenko.database.domain.Expression;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExpressionDAO {
    void save(Expression expression);

    Expression findById(Long id);

    List<Expression> findByParameter(String parameter);

    List<Expression> findAll();

    void update(Expression expression);

    void deleteById(Long id);

    Expression mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
