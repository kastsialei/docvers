package dao.version;

import dao.ExceptionsThrower;
import entities.Version;
import exception.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;
import service.QueriesHQL;

import javax.swing.*;
import java.lang.IllegalArgumentException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOImplHHQL implements VersionDAO {
    private Session session;

    public VersionDAOImplHHQL(Session session) {
        this.session = session;

    }

    @Override
    public List<Version> getVersionsOfDocument(long id) throws MyException {
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            query.setLong("id", id);
            List<Version> versions = query.list();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void addVersion(Version version) throws MyException{
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            //long name = getLastVersionNameInfo(version.getDocumentID());
            Query query = session.createQuery(QueriesHQL.UPDATE_VERSION_SET_IS_RELEASED);
            query.setBoolean("isReleased", true);
            query.setLong("id", version.getDocumentId().getId());
            query.executeUpdate();
            session.cancelQuery();
            session.clear();
            //version.setVersionName(name + 1);
            version.setReleased(false);
            session.save(version);


        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws MyException{
        try {
            Query query = session.createQuery(QueriesHQL.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
            query.setString("login", login);
            query.setLong("codeDocumentName", docCode);
            query.setLong("versionName", versName);
            int i = query.executeUpdate();
            if (i == 0) throw new NoSuchObjectInDB("Nothing to delete");
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws MyException{
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_TYPE_FROM_VERSION);
            query.setString("login", login);
            query.setLong("codeDocumentName", documentName);
            query.setLong("versionName", versionName);
            String type = (String) query.uniqueResult();
            if (type.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return type;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public Version getVersion(long id, long versName) throws MyException{
        Version version = null;
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
            query.setLong("id", id);
            query.setLong("versionName", versName);
            version = (Version) query.uniqueResult();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void updateVersionDescription(String login, long codeDocName, long versionName, String description) throws MyException {
        try {
            Query query = session.createQuery(QueriesHQL.UPDATE_VERSION_DESCRIPTION);
            query.setString("login", login);
            query.setString("description", description);
            query.setLong("codeDocumentName", codeDocName);
            query.setLong("versionName", versionName);
            query.executeUpdate();
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws MyException{
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_NAME_FROM_VERSION);
            query.setLong("id", docID);
            Long l = (Long) query.uniqueResult();
            long versionName = (l == null ? 0 : l);
            return versionName;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }
}
