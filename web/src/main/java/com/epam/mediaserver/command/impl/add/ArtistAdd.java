package com.epam.mediaserver.command.impl.add;

import com.epam.mediaserver.command.Command;
import com.epam.mediaserver.constant.Message;
import com.epam.mediaserver.constant.Parameter;
import com.epam.mediaserver.entity.Artist;
import com.epam.mediaserver.exception.ServiceException;
import com.epam.mediaserver.exception.ValidateException;
import com.epam.mediaserver.service.ServiceFactory;
import com.epam.mediaserver.util.Messanger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for add new artist {@link Artist}
 */

public class ArtistAdd implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ArtistAdd.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String title = request.getParameter(Parameter.PARMETER_ARTIST_TITLE);
        String genre = request.getParameter(Parameter.PARMETER_ARTIST_GENRE);
        String description = request.getParameter(Parameter.PARMETER_ARTIST_DESCRIPTION);
        String image = request.getParameter(Parameter.PARMETER_ARTIST_IMAGE);

        try {

            ServiceFactory.getArtistService().add(title, genre, description, image);

        } catch (ValidateException e) {
            LOGGER.info(VALIDATION_ERROR);
            Messanger.sendMessage(response, Message.VALIDATION_ERROR);
        } catch (ServiceException e) {
            LOGGER.error(DB_ERROR);
            Messanger.sendMessage(response, Message.DAO_ERROR);
        }

    }
}