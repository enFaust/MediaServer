package com.epam.mediaserver.command.impl.show;

import com.epam.mediaserver.command.Command;
import com.epam.mediaserver.constant.Error;
import com.epam.mediaserver.constant.Message;
import com.epam.mediaserver.entity.Bonus;
import com.epam.mediaserver.exception.ServiceException;
import com.epam.mediaserver.service.ServiceFactory;
import com.epam.mediaserver.util.Messanger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBonuses implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowBonuses.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Bonus> bonuses = null;

        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();

        try {
            bonuses = ServiceFactory.getBonusService().getAll();

            out.print("{\"bonuses\": " + mapper.writeValueAsString(bonuses) + "}");

        } catch (ServiceException e) {
            LOGGER.error(Error.BONUSES_ALL);
            Messanger.sendMessage(response, Message.DAO_ERROR);
        }
    }
}