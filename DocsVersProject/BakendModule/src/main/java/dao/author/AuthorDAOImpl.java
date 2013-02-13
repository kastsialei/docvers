package dao.author;

import entities.Author;
import exception.NullConnectionException;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:09
 * To change this template use File | Settings | File Templates.
 */
public class AuthorDAOImpl implements AuthorDAO {

    private Connection conn;

    public AuthorDAOImpl(Connection conn) throws NullConnectionException, SQLException {
        if(conn == null) throw new NullConnectionException(AuthorDAOImpl.class);
        this.conn = conn;
        this.conn.setAutoCommit(false);
    }

    @Override
    public Author getAuthorByID(long id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_AUTHOR_WHERE_ID);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            conn.commit();
            Author author = null;
            if(rs.next()){
                author = new Author(id, rs.getString("LOGIN"), rs.getString("PASSWORD"));
            }
            return author;
        } finally {
            if(ps!=null) ps.close();
            if(rs!=null) rs.close();
        }
    }

    @Override
    public Author getAuthorByDocumentID(long id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(Queries.SELECT_AUTHOR_ID_FROM_DOCUMENT_WHERE_ID);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            conn.commit();
            Author author = null;
            if(rs.next()) {
                author = getAuthorByID(rs.getLong("ID"));
            }
            return author;
        } finally {
            if(ps!=null) ps.close();
            if(rs!=null) rs.close();
        }
    }

}