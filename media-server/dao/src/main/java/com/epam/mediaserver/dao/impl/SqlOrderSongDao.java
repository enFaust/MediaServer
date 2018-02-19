package com.epam.mediaserver.dao.impl;

import com.epam.mediaserver.dao.AbstractModelDao;
import com.epam.mediaserver.dao.SqlFactory;
import com.epam.mediaserver.dao.impl.pool.ConnectionPool;
import com.epam.mediaserver.entity.Model;
import com.epam.mediaserver.entity.Order;
import com.epam.mediaserver.entity.OrderSong;
import com.epam.mediaserver.entity.Song;
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

public class SqlOrderSongDao extends AbstractModelDao {

    private static final Logger LOGGER = LogManager.getLogger(SqlOrderSongDao.class);

    private static final String CREATE_QUERY = " INSERT INTO t_order_song (order_id, song_id) VALUES (?, ?);";
    private static final String SELECT_QUERY = "SELECT * FROM t_order_song";
    private static final String SELECT_QUERY_WITH_ID = "SELECT * FROM t_order_song WHERE order_song_id = ?";
    private static final String SELECT_QUERY_BY_ORDER = "SELECT * FROM t_order_song WHERE order_id = ?";
    private static final String UPDATE_QUERY = "UPDATE t_order_song SET song_id = ?, order_id = ? " +
                                               "WHERE order_song_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM t_order_song WHERE order_song_id = ?;";

    private static final String ORDER_SONG_ID = "order_song_id";
    private static final String SONG_ID = "song_id";
    private static final String ORDER_ID = "order_id";

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

    protected String getSelectQueryByOrder() {
        return SELECT_QUERY_BY_ORDER;
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

        OrderSong orderSong = (OrderSong) model;
        ps.setInt(1, orderSong.getOrder().getId());
        ps.setInt(2, orderSong.getSong().getId());

        return ps.executeUpdate();
    }

    @Override
    protected int preparedStatementForUpdate(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);

        OrderSong orderSong = (OrderSong) model;
        ps.setInt(1, orderSong.getSong().getId());
        ps.setInt(2, orderSong.getOrder().getId());
        ps.setInt(3, orderSong.getId());

        return ps.executeUpdate();
    }

    @Override
    protected int preparedStatementForDelete(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        OrderSong orderSong = (OrderSong) model;

        ps.setInt(1, orderSong.getId());

        return ps.executeUpdate();
    }

    public List<OrderSong> getByOrder(int orderId) throws DAOException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<OrderSong> list = new ArrayList<>();

        try {
            con = ConnectionPool.takeConnection();

            ps = con.prepareStatement(getSelectQueryByOrder());
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderSong goods = (OrderSong) parseResult(rs);
                list.add(goods);
            }

        } catch (ConnectionPoolException e) {
            LOGGER.error(OPEN_CONNECTION_EXEPTION, e);
            throw new DAOException(OPEN_CONNECTION_EXEPTION);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXEPTION, e);
            throw new DAOException(SQL_EXEPTION);
        } finally {
            try {
                ConnectionPool.closeConnection(con, ps, rs);
            } catch (ConnectionPoolException e) {
                LOGGER.error(CLOSE_CONNECTION_EXEPTION);
                throw new DAOException(CLOSE_CONNECTION_EXEPTION);
            }
        }

        return list;
    }

    @Override
    protected Model parseResult(ResultSet rs) throws DAOException {
        OrderSong orderSong = new OrderSong();
        SqlFactory factory = SqlFactory.getInstance();
        try {
            orderSong.setId(rs.getInt(ORDER_SONG_ID));
            Song song = (Song) factory.getSongDao().getById(rs.getInt(SONG_ID));
            orderSong.setSong(song);
            Order order = (Order) factory.getOrderDao().getById(rs.getInt(ORDER_ID));
            orderSong.setOrder(order);

        } catch (SQLException e) {
            LOGGER.error(SQL_EXEPTION);
            throw new DAOException(SQL_EXEPTION);
        }

        return orderSong;
    }
}