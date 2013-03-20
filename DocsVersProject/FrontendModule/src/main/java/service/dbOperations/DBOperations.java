package service.dbOperations;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.VersionBean;
import exception.BusinessException;
import exception.SystemException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.03.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public interface DBOperations {
    public void addDocument(DocumentBean documentBean) throws BusinessException, SystemException;

    public long getLastVersionNameInfo(long docID) throws BusinessException, SystemException;

    public void addVersion(VersionBean versionBean) throws BusinessException, SystemException;

    public List<DocumentBean> getDocumentsByAuthor(String login) throws BusinessException, SystemException;

    public DocumentBean getDocumentsByAuthorAndName(String login, long docNameCode) throws BusinessException, SystemException;

    public AuthorBean getAuthorByLogin(String login) throws BusinessException, SystemException;

    public List<VersionBean> getVersionsOfDocument(String login, long docNameCode) throws BusinessException, SystemException;

    public VersionBean getVersion(String login, long docNameCode, long versName) throws BusinessException, SystemException;

    public void deleteDocument(String login, long docNameCode) throws BusinessException, SystemException;

    public void deleteVersion(long versName, long docCode, String login) throws BusinessException, SystemException;

    public long getDocumentIDByCodeNameAndLogin(String login, long docName) throws BusinessException, SystemException;

    public String getVersionType(long versionName, long documentName, String login) throws BusinessException, SystemException;
}