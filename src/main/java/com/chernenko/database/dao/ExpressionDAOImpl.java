package com.chernenko.database.dao;

import com.chernenko.database.DBConnector;
import com.chernenko.database.DatabaseException;
import com.chernenko.database.domain.Expression;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ExpressionDAOImpl implements ExpressionDAO {

    private static final String INSERT_QUERY = "INSERT INTO expressions(id, expression, result) VALUES (?,?,?);";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM expressions WHERE expressions.id = ?;";
    private static final String GET_ALL_QUERY = "SELECT * FROM expressions ";
    private static final String DELETE_QUERY = "DELETE FROM courses WHERE expressions.id = ?;";
    private static final String UPDATE_QUERY = "UPDATE expressions SET expression = ?, result = ? WHERE id = ?;";

    private static final String SEARCH_QUERY = "SELECT * FROM expressions WHERE expressions.result ";
    private final DBConnector connector;

    public ExpressionDAOImpl(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public void save(Expression expression) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setLong(1, new Random().nextLong());
            preparedStatement.setString(2, expression.getExpression());
            preparedStatement.setDouble(3, expression.getResult());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Insertion is failed", e);
        }
    }

    @Override
    public Expression findById(Long id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY)) {
            preparedStatement.setString(2, String.valueOf(id));

            return mapResultSetToEntity(preparedStatement.executeQuery());

        } catch (SQLException e) {
            throw new DatabaseException(" Getting by id is failed", e);
        }
    }

    @Override
    public List<Expression> findByParameter(String parameter)  {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_QUERY + parameter)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Expression> expressions = new ArrayList<>();
                while (resultSet.next()) {
                    expressions.add(mapResultSetToEntity(resultSet));
                }
                return expressions;
            }
        } catch (SQLException e) {
            throw new DatabaseException(" Getting by id is failed", e);
        }
    }

    @Override
    public List<Expression> findAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERY)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Expression> expressions = new ArrayList<>();
                while (resultSet.next()) {
                    expressions.add(mapResultSetToEntity(resultSet));
                }
                return expressions;
            }
        } catch (SQLException e) {
            throw new DatabaseException(" Getting all is failed", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setLong(1, id);
            int deleted = statement.executeUpdate();

            if (deleted == 0) {
                throw new DAOException("Incorrect id");
            }

        } catch (SQLException e) {
            throw new DAOException("Delete from database failed", e);
        }
    }

    @Override
    public void update(Expression expression) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, expression.getExpression());
            preparedStatement.setString(2, String.valueOf(expression.getResult()));
            preparedStatement.setString(3, String.valueOf(expression.getId()));


            int updated = preparedStatement.executeUpdate();

            if (updated == 0) {
                throw new DAOException("Incorrect input element");
            }

        } catch (SQLException e) {
            throw new DAOException("Update failed", e);
        }
    }

    @Override
    public Expression mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Expression(resultSet.getLong("id"),
                resultSet.getString("expression"),
                resultSet.getInt("result"));
    }

}
