package DocsVersProject.daoTests.AuthorDAOTest;

import DocsVersProject.daoTests.EntitiesFactory;
import dao.author.AuthorDAO;
import dao.author.AuthorDAOImpl;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import entities.Author;
import org.junit.Test;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:39
 * To change this template use File | Settings | File Templates.
 */
public class getAuthorByIIDTest {
    @Test
    public void getAuthorByIdtSuccessful() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        Author author = EntitiesFactory.createNewAuthor();
        long id = author.getId();
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // there is no documents in database
        AuthorDAO dao = new AuthorDAOImpl(conn);
        // when
        dao.getAuthorByID(id);
        // then
        verify(conn).prepareStatement(Queries.SELECT_FROM_AUTHOR_WHERE_ID);
        verify(ps).setLong(1, id);
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();

    }
}
