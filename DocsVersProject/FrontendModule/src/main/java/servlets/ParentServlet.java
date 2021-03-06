package servlets;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.VersionBean;
import dao.DAOType;
import exception.MyException;
import service.FileFolderService;
import service.RequestParser;
import service.dbOperations.DBOperations;
import service.dbOperations.DBOperationsFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 05.03.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */

public class ParentServlet extends HttpServlet {
    private DBOperations service;
    private long documentName;
    private FileFolderService fileFolderService;
    private RequestParser requestParser;
    private String encoding;
    private String filePath;
    public void init() {
        requestParser = new RequestParser();
        service = DBOperationsFactory.getDBService((DAOType) getServletContext().getAttribute("type"));
        fileFolderService = new FileFolderService();
        // Get the file location where it would be stored.
        encoding = (String) getServletContext().getAttribute("encoding");
        filePath = (String) getServletContext().getAttribute("filePath");
    }

    public String getFilePath() {
        return filePath;
    }

    public String getEncoding() {
        return encoding;
    }

    public long getDocumentName() {
        return documentName;
    }

    public void setDocumentName(long documentName) {
        this.documentName = documentName;
    }

    public DBOperations getService() {
        return service;
    }

    public FileFolderService getFileFolderService() {
        return fileFolderService;
    }

    public RequestParser getRequestParser() {
        return requestParser;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        throw new ServletException("POST method used with " +
                getClass().getName() + ": GET method required.");
    }


    public void showMessage(HttpServletRequest request, HttpServletResponse response, Object message, String parameterName, String url)
            throws ServletException, IOException {
        request.setAttribute(parameterName, message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
                getRequestDispatcher(url);
        reqDispatcher.forward(request, response);
    }

    protected VersionBean parseVersionBean(HttpServletRequest request) throws MyException {
        String par = request.getParameter("version");
        long versionName = 0;
        if (!(par == null)) {
            versionName = Long.parseLong(par);
        }
        if (versionName == 0) {
            versionName = (Long) request.getSession().getAttribute("versionToView");
        } else {
            request.getSession().setAttribute("versionToView", versionName);
        }
        long documentCode = (Long) request.getSession().getAttribute("documentToView");
        AuthorBean authorBean = getRequestParser().getAuthorBean(request);
        String login = authorBean.getLogin();
        VersionBean versionBean = getService().getVersion(login, documentCode, versionName);
        return versionBean;
    }

    protected DocumentBean parseDocumentBean (HttpServletRequest request) throws MyException {
        String par = request.getParameter("document to view");
        long docName = 0;
        if (!(par == null)) {
            docName = Long.parseLong(par);
        }
        if (docName == 0) {
            docName = (Long) request.getSession().getAttribute("documentView");
        } else {
            request.getSession().setAttribute("documentView", docName);
        }
        String login = getRequestParser().getAuthorBean(request).getLogin();
        DocumentBean doc = getService().getDocumentByAuthorAndName(login, docName);
        return doc;
    }


}
