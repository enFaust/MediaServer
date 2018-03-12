/*package com.epam.mediaserver.command.impl.search;

import com.epam.mediaserver.command.Command;
import com.epam.mediaserver.constant.Error;
import com.epam.mediaserver.constant.Message;
import com.epam.mediaserver.constant.Parameter;
import com.epam.mediaserver.entity.User;
import com.epam.mediaserver.exception.ServiceException;
import com.epam.mediaserver.service.ServiceFactory;
import com.epam.mediaserver.util.Messanger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchUser implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SearchUser.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String value = request.getParameter(Parameter.PARMETER_USER_SEARCH);
        String page = request.getParameter(Parameter.PARMETER_CURRENT_USER_PAGE);

        List<User> users;
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Current page: " + page);

        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        try {
            if (Objects.isNull(page)){
                users = ServiceFactory.getUserService().search(value, 1);
            }
           else {
                users = ServiceFactory.getUserService().search(value, Integer.valueOf(page));
            }
            out.print("{\"users\": " + mapper.writeValueAsString(users) + "}");

            request.getSession().setAttribute(Parameter.PARMETER_USER_PAGE, ServiceFactory.getUserService().getSearchPage(value));

        } catch (ServiceException e) {
            LOGGER.error(Error.USERS_ALL);
            Messanger.sendMessage(response, Message.DAO_ERROR);
        }
    }
}*/
