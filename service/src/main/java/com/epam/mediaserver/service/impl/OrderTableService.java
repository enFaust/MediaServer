package com.epam.mediaserver.service.impl;

import com.epam.mediaserver.constant.Error;
import com.epam.mediaserver.dao.SqlFactory;
import com.epam.mediaserver.entity.Order;
import com.epam.mediaserver.exception.ServiceException;
import com.epam.mediaserver.exception.ValidateException;
import com.epam.mediaserver.exeption.DAOException;
import com.epam.mediaserver.util.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class OrderTableService {

    private static final Logger LOGGER = LogManager.getLogger(OrderTableService.class);

    public void add(double price, String user) throws ValidateException, ServiceException {

        try {
            if (Validation.orderCheck(price, user)) {

                Order order = new Order();

                order.setPrice(price);
                order.setUser(SqlFactory.getUserDao().authorisation(user));

                Date date = new Date(System.currentTimeMillis());
                order.setDate(date);

                Time time = new Time(System.currentTimeMillis());
                order.setTime(time);

                SqlFactory.getOrderDao().add(order);

            } else {
                LOGGER.info(Error.VALIDATION);
                throw new ValidateException(Error.VALIDATION);
            }
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }

    }

    public void edit(int id, double price, String user, int number) throws ServiceException, ValidateException {

        try {
            Order order = (Order) SqlFactory.getOrderDao().getById(id);

            if (Validation.orderCheck(price, user, number)) {

                order.setNumber(number);
                order.setPrice(price);
                order.setUser(SqlFactory.getUserDao().authorisation(user));

                SqlFactory.getOrderDao().update(order);

            } else {
                LOGGER.info(Error.VALIDATION);
                throw new ValidateException(Error.VALIDATION);
            }
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }
    }


    public void delete(int id) throws ServiceException {

        try {
            Order order = (Order) SqlFactory.getOrderDao().getById(id);
            SqlFactory.getOrderDao().delete(order);
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }
    }

    public Order getById(int id) throws ServiceException {

        Order order = null;

        try {
            order = (Order) SqlFactory.getOrderDao().getById(id);
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }
        return order;

    }

    public List<Order> getByUser(int id) throws ServiceException {

        List<Order> orders = null;

        try {
            orders = SqlFactory.getOrderDao().getByUser(id);
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }
        return orders;

    }

    public List<Order> getAll() throws ServiceException {

        List<Order> orders = null;

        try {
            orders = SqlFactory.getOrderDao().getAll();
        } catch (DAOException e) {
            LOGGER.error(Error.DAO_EXCEPTION);
            throw new ServiceException(Error.DAO_EXCEPTION);
        }

        return orders;
    }
}