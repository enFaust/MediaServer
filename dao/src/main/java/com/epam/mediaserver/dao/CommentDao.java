package com.epam.mediaserver.dao;

import com.epam.mediaserver.entity.Comment;
import com.epam.mediaserver.exeption.DAOException;

import java.util.List;


public interface CommentDao extends CrudDao<Comment,Long> {


    /**
     * Method receives title of song and find equal fields in table t_comment
     *
     * @return entity of song
     * @throws DAOException if database error was detected
     */

    List<Comment> getBySong(Long song) throws DAOException;
}
