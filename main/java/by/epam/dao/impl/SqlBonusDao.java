package by.epam.dao.impl;

import by.epam.bean.Bonus;
import by.epam.bean.Model;
import by.epam.dao.AbstractModelDao;
import by.epam.dao.BonusDao;
import by.epam.dao.impl.pool.ConnectionPool;
import by.epam.exeption.dao.ConnectionPoolException;
import by.epam.exeption.dao.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * #BonusDao interface implementation for the MySQL db {@link Bonus}
 * #AbstractModelDao extends for call CRUD commands for the MySQL db
 */

public class SqlBonusDao extends AbstractModelDao implements BonusDao{

    private static final Logger LOGGER = Logger.getLogger(SqlBonusDao.class);

    private static final String CREATE_QUERY = "INSERT INTO t_bonus (bonus_title, bonus_description, bonus_code , bonus_discount) VALUES ( ?, ?, ?, ?)";
    private static final String SELECT_QUERY = "Select * FROM t_bonus";
    private static final String SELECT_QUERY_WITH_ID = "SELECT * From t_bonus WHERE bonus_id = ?";
    private static final String SELECT_QUERY_BY_CODE = "SELECT * From t_bonus WHERE bonus_code = ?";
    private static final String UPDATE_QUERY = "UPDATE t_bonus SET bonus_description = ?, bonus_discount = ?, bonus_code = ? WHERE bonus_title= ?;";
    private static final String DELETE_QUERY = "DELETE FROM t_bonus WHERE bonus_title=?;";


    private static final String BONUS_ID = "bonus_id";
    private static final String BONUS_TITLE = "bonus_title";
    private static final String BONUS_DESCRIPTION = "bonus_description";
    private static final String BONUS_DISCOUNT = "bonus_discount";
    private static final String BONUS_CODE = "bonus_code";

    @Override
    public String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    public String getSelectQuery() {
        return SELECT_QUERY;
    }

    @Override
    public String getSelectQueryWithID() {
        return SELECT_QUERY_WITH_ID;
    }

    @Override
    public String getSelectQueryByCode() {
        return SELECT_QUERY_BY_CODE;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    public int preparedStatementForCreate(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        Bonus bonus = (Bonus) model;
        ps.setString(1, bonus.getTitle());
        ps.setString(2, bonus.getDescription());
        ps.setString(3, bonus.getCode());
        ps.setInt(4, bonus.getDiscount());

        return ps.executeUpdate();
    }

    @Override
    public int preparedStatementForUpdate(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        Bonus bonus = (Bonus) model;
        ps.setString(1, bonus.getDescription());
        ps.setInt(2, bonus.getDiscount());
        ps.setString(3, bonus.getCode());
        ps.setString(4, bonus.getTitle());

        return ps.executeUpdate();
    }

    @Override
    public int preparedStatementForDelete(Connection con, Model model, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        Bonus bonus = (Bonus) model;

        ps.setString(1, bonus.getTitle());

        return ps.executeUpdate();
    }

    @Override
    public Model parseResult(ResultSet rs) throws SQLException {
       Bonus bonus = new Bonus();

       bonus.setId(rs.getInt(BONUS_ID));
       bonus.setTitle(rs.getString(BONUS_TITLE));
       bonus.setDescription(rs.getString(BONUS_DESCRIPTION));
       bonus.setDiscount(rs.getInt(BONUS_DISCOUNT));
       bonus.setCode(rs.getString(BONUS_CODE));

       return bonus;
    }

    @Override
    public Bonus getByCode(String code) throws DAOException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Bonus bonus = null;

        try {
            con = ConnectionPool.takeConnection();
            ps = con.prepareStatement(getSelectQueryByCode());
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                bonus = (Bonus) parseResult(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXEPTION, e);
            throw new DAOException(SQL_EXEPTION);
        } catch (ConnectionPoolException e) {
            LOGGER.error(OPEN_CONNECTION_EXEPTION, e);
            throw new DAOException(OPEN_CONNECTION_EXEPTION);
        } finally {
            try {
                ConnectionPool.closeConnection(con, ps, rs);
            } catch (ConnectionPoolException e) {
                LOGGER.error(CLOSE_CONNECTION_EXEPTION);
                throw new DAOException(CLOSE_CONNECTION_EXEPTION);
            }
        }
        return bonus;
    }


}