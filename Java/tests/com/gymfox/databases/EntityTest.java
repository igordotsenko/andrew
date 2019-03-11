package com.gymfox.databases;

import com.gymfox.databases.Entity.InvalidIdException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.gymfox.databases.Entity.getListQuery;
import static com.gymfox.databases.Entity.setDatabase;

public class EntityTest {
    private static final int FIRST_INDEX_PARAMETER = 1;
    private static Connection connection = null;

    @BeforeClass
    public static void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        connection = DriverManager.getConnection (
                "jdbc:postgresql://localhost:5432/orm", "postgres",
                "13121993");
        setDatabase(connection);
    }


    private static String getPreparedStatementByQuery(String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        return preparedStatement.toString();
    }

    private static String getPreparedStatementById(String query, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(FIRST_INDEX_PARAMETER, id);

        return preparedStatement.toString();
    }

    @Test
    public void getInsertQueryTest() throws SQLException, InvalidIdException {
        Category category = new Category(1);
        Comment comment = new Comment();

        category.setTitle("test");
        category.setSection(5);

        comment.setText("some text");
        comment.setUser(13);
        comment.setPost(13);

        Assert.assertEquals("INSERT INTO \"category\" (category_title, section_id) VALUES ('test', 5) RETURNING " +
                "category_id", getPreparedStatementByQuery(category.getInsertQuery()));
        Assert.assertEquals("INSERT INTO \"comment\" (comment_text, post_id, user_id) VALUES ('some text', 13, 13) " +
                "RETURNING comment_id", getPreparedStatementByQuery(comment.getInsertQuery()));
    }

    @Test
    public void getUpdateQuery() throws SQLException, InvalidIdException {
        User user = new User(5);

        user.setName("test1");
        user.setName("test2");
        user.setName("test3");
        user.setName("test4");
        user.setName("test5");
        user.setName("test6");
        user.setName("test7");
        user.setName("test8");
        user.setName("test9");
        user.setEmail("test@mail.com");

        Assert.assertEquals("UPDATE \"user\" SET user_email = 'test@mail.com', user_name = 'test9' WHERE user_id=5",
                getPreparedStatementById(user.getUpdateQuery(), user.getId()));
    }

    @Test
    public void deleteQueryTest() throws SQLException, InvalidIdException {
        Post post0 = new Post();
        Post post1 = new Post(1);
        Post post2 = new Post(2);
        Post post3 = new Post(3);
        Post post4 = new Post(4);
        Post post5 = new Post(5);

        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=0",
                getPreparedStatementById(post0.getDeleteQuery(), post0.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=1",
                getPreparedStatementById(post1.getDeleteQuery(), post1.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=2",
                getPreparedStatementById(post2.getDeleteQuery(), post2.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=3",
                getPreparedStatementById(post3.getDeleteQuery(), post3.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=4",
                getPreparedStatementById(post4.getDeleteQuery(), post4.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=5",
                getPreparedStatementById(post5.getDeleteQuery(), post5.getId()));
    }

    @Test
    public void selectQueryTest() throws SQLException {
        Assert.assertEquals("SELECT * FROM \"tag\"", getPreparedStatementByQuery(getListQuery(Tag.class)));
        Assert.assertEquals("SELECT * FROM \"post\"", getPreparedStatementByQuery(getListQuery(Post.class)));
    }

    @Test
    public void siblingsQueryTest() throws SQLException, InvalidIdException {
        Post post0 = new Post();
        Post post1 = new Post(1);
        Post post2 = new Post(2);
        Post post3 = new Post(3);
        Post post4 = new Post(4);
        Post post5 = new Post(5);

        User user = new User();

        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=0",
                getPreparedStatementById(post0.getSiblingsQuery(Tag.class), post0.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=1",
                getPreparedStatementById(post1.getSiblingsQuery(Tag.class), post1.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=2",
                getPreparedStatementById(post2.getSiblingsQuery(Tag.class), post2.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=3",
                getPreparedStatementById(post3.getSiblingsQuery(Tag.class), post3.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=4",
                getPreparedStatementById(post4.getSiblingsQuery(Tag.class), post4.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=5",
                getPreparedStatementById(post5.getSiblingsQuery(Tag.class), post5.getId()));
        Assert.assertEquals("SELECT * FROM \"post__user\" NATURAL JOIN \"post\" WHERE user_id=0",
                getPreparedStatementById(user.getSiblingsQuery(Post.class), user.getId()));
    }

    @Test (expected = InvalidIdException.class)
    public void zeroIdExceptionTest() throws InvalidIdException {
        new Category(0);
    }

    @Test (expected = InvalidIdException.class)
    public void negativeIdExceptionTest() throws InvalidIdException {
        new Category(-65535);
    }
}