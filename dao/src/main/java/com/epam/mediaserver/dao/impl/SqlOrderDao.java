package com.epam.mediaserver.dao.impl;

import com.epam.mediaserver.builder.BuilderFactory;
import com.epam.mediaserver.dao.AbstractModelDao;
import com.epam.mediaserver.dao.SqlFactory;
import com.epam.mediaserver.dao.impl.pool.ConnectionPool;
import com.epam.mediaserver.entity.Model;
import com.epam.mediaserver.entity.Order;
import com.epam.mediaserver.entity.User;
import com.epam.mediaserver.exeption.ConnectionPoolException;
import com.epam.mediaserver.exeption.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * #AbstractModelDao extends for call CRUD commands for the MySQL db
 */

public class SqlOrderDao extends AbstractModelDao {

    private static final Logger LOGGER = LogManager.getLogger(SqlOrderDao.class);

    private static final String
        CREATE_QUERY =
        "INSERT INTO t_order(user_id, order_price, order_time, order_date, order_number) VALUES (?,?,?,?,?);";
    private static final String SELECT_QUERY = "SELECT * FROM t_order";
    private static final String SELECT_QUERY_WITH_ID = "SELECT * FROM t_order WHERE order_id = ?";
    private static final String SELECT_QUERY_BY_USER = "SELECT * FROM t_order WHERE user_id = ?";
    private static final String SELECT_QUERY_BY_NUMBER = "SELECT * FROM t_order WHERE order_number = ?";
    private static final String
        UPDATE_QUERY =
        "UPDATE t_order SET user_id = ?, order_price = ?, order_time = ?, order_date = ?, order_number WHERE order_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM t_order WHERE order_id = ?;";

    private static final String USER_ID = "user_id";
    private static final String ORDER_NUMBER = "order_number";
    private static final String ORDER_PRICE = "order_price";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_TIME = "order_time";
    private static final String ORDER_DATE = "order_date";


    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getSelectQuery() {
        return SELECT_QUERY;
    }

    @Override
    protected String getSelectQueryWithID() {
        return SELECT_QUERY_WITH_ID;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected int preparedStatementForCreate(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);

        Order order = (Order) model;

        ps.setLong(1, order.getUser().getId());
        ps.setDouble(2, order.getPrice());
        ps.setTime(3, order.getTime());
        ps.setDate(4, order.getDate());
        ps.setInt(5, order.getNumber());

        return ps.executeUpdate();
    }

    @Override
    protected int preparedStatementForUpdate(Connection con, Model model, String query) throws SQLException {

        PreparedStatement ps = con.prepareStatement(query);

        Order order = (Order) model;

        ps.setLong(1, order.getUser().getId());
        ps.setDouble(2, order.getPrice());
        ps.setTime(3, order.getTime());
        ps.setDate(4, order.getDate());
        ps.setLong(5, order.getNumber());
        ps.setLong(6, order.getId());

        return ps.executeUpdate();
    }

    @Override
    protected int preparedStatementForDelete(Connection con, Model model, String query) throws SQLException {

        PreparedStatement ps = con.prepareStatement(query);

        Order order = (Order) model;

        ps.setLong(1, order.getId());

        return ps.executeUpdate();
    }

    public Order getByNumber(int number) throws DAOException {

        Order order = null;

        try (Connection con = ConnectionPool.takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_QUERY_BY_NUMBER)) {

            ps.setInt(1, number);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = (Order) parseResult(rs);
            }

        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection is not open", e);
            throw new DAOException("Connection is not open");
        } catch (SQLException e) {
            LOGGER.error("SQL Exception", e);
            throw new DAOException("SQL Exception");
        }

        return order;
    }

    public List<Order> getByUser(Long userId) throws DAOException {

        try (Connection con = ConnectionPool.takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_QUERY_BY_USER)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                Order order = (Order) parseResult(rs);
                list.add(order);
            }
            return list;

        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection is not open", e);
            throw new DAOException("Connection is not open");
        } catch (SQLException e) {
            LOGGER.error("SQL Exception", e);
            throw new DAOException("SQL Exception");
        }


    }


    @Override
    protected Model parseResult(ResultSet rs) throws DAOException {

        try {
            return BuilderFactory.getOrderBuilder()
                .setId(rs.getLong(ORDER_ID))
                .setNumber(rs.getInt(ORDER_NUMBER))
                .setUser((User) SqlFactory.getInstance().getUserDao().getById(rs.getLong(USER_ID)))
                .setPrice(rs.getDouble(ORDER_PRICE))
                .setData(rs.getDate(ORDER_DATE))
                .setTime(rs.getTime(ORDER_TIME))
                .build();
        } catch (SQLException e) {
            LOGGER.error("SQL Exception");
            throw new DAOException("SQL Exception");
        }


    }
}
