package servlets;

import exception.BusinessException;
import exception.SystemException;
import filters.LoginFilter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ConnectionPoolFactory;
import service.SessionFactoryUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.03.13
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */
public class ServletListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(ServletListener.class);
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
//        ServletContext sc = event.getServletContext();
//        String whatType = sc.getInitParameter("typeSelected");
//        Furniture f = new Furniture(whatType);
//        sc.setAttribute("furniture", f);
        logger.trace("Initializing context...");
//        String path = event.getServletContext().getInitParameter("hibernateConfigFilePath");
//        SessionFactoryUtil.init(path);

    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {

         // todo: logger
        try {
            ConnectionPoolFactory.getInstance().getConnectionPool().closeAllConnections();
            SessionFactory sf = SessionFactoryUtil.getInstance().getSessionFactory();
            if (!sf.isClosed()) sf.close();
            logger.trace("Destroying context...");
        } catch (SystemException e) {
            logger.error("System Exception while destroying Exceptional Filter.");
        } catch (BusinessException e) {
            logger.error("Business Exception while destroying Exceptional Filter.");
        }
    }
}
