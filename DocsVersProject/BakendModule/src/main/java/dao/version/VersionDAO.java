package dao.version;

import dao.DAO;
import entities.Version;
import exception.DAOException;
import exception.NotEnoughRightsException;
import exception.SystemException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:05
 * To change this template use File | Settings | File Templates.
 */
public interface VersionDAO extends DAO {
    public List<Version> getVersionsOfDocument(long id) throws DAOException, SystemException;

    public void addVersion(Version version) throws DAOException, SystemException;

    long getLastVersionNameInfo(long docID) throws DAOException, SystemException;

    void deleteVersion(long id, long docCode, String login) throws DAOException, SystemException;

    String getVersionType(long versionName, long documentName, String login) throws DAOException, SystemException;
}
