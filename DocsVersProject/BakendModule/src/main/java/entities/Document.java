package entities;

import java.sql.Clob;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
public class Document {

    private long id;
    private long authorID;
    private String name;
    private String description;
    private static long count = 0;

    public Document() {
        id = count;
        ++count;
    }

    public Document(long authorID, String name, String description) {
        this.authorID = authorID;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
