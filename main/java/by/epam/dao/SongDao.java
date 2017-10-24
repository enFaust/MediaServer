package by.epam.dao;

import by.epam.bean.Album;
import by.epam.bean.Song;
import by.epam.exeption.dao.DAOException;

import java.util.List;

public interface SongDao {

    /**
     * Method return a String with query by album {@link Album} title
     *
     * @return  String
     * @throws  DAOException
     *             if database error was detected
     */

    String getByAlbumQuery();

    /**
     * Method return a String with query by song {@link Song} title
     *
     * @return  String
     * @throws  DAOException
     *             if database error was detected
     */

    String getByNameQuery();

    /**
     * Method receives a String of album {@link Album} and return list song {@link Song}
     *
     * @param album
     *            for search a field in the database by title album
     * @return {@link java.util.List} instances of {@link Song}
     * @throws DAOException
     *             if database error was detected
     */

    List<Song> getByAlbum(String album) throws DAOException;

    /**
     * Method add user photo in Data Base
     *
     * @param song
     *            for search a field in the database by title song
     * @return entity of song {@link Song}
     * @throws DAOException
     *             if database error was detected
     */

    Song getByName(String song) throws DAOException;

}
