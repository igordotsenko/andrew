package com.gymfox.databases;

import com.gymfox.databases.Entity.InvalidIdException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static com.gymfox.databases.DBManagerImplTest.dbManager;
import static com.gymfox.databases.Entity.getListQuery;

public class EntityTest {

    @Test
    public void getInsertQueryTest() throws InvalidIdException {
        Category category = new Category(1);
        Comment comment = new Comment();

        category.setTitle("test");
        category.setSection(5);

        comment.setText("some text");
        comment.setUser(13);
        comment.setPost(13);

        Assert.assertEquals("INSERT INTO \"category\" (category_title, section_id) VALUES ('test', 5) RETURNING " +
                "category_id", category.getInsertQuery());
        Assert.assertEquals("INSERT INTO \"comment\" (comment_text, post_id, user_id) VALUES ('some text', 13, 13) " +
                "RETURNING comment_id", comment.getInsertQuery());
    }

    @Test
    public void getUpdateQuery() throws SQLException, InvalidIdException {
        User user = new User(5);

        user.setName("test1");
        user.setName("test2");
        user.setEmail("test@mail.com");

        Assert.assertEquals("UPDATE \"user\" SET user_email = 'test@mail.com', user_name = 'test2' WHERE user_id=5",
                dbManager.getPreparedStatement(user.getUpdateQuery(), user.getId()));
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
                dbManager.getPreparedStatement(post0.getDeleteQuery(), post0.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=1",
                dbManager.getPreparedStatement(post1.getDeleteQuery(), post1.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=2",
                dbManager.getPreparedStatement(post2.getDeleteQuery(), post2.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=3",
                dbManager.getPreparedStatement(post3.getDeleteQuery(), post3.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=4",
                dbManager.getPreparedStatement(post4.getDeleteQuery(), post4.getId()));
        Assert.assertEquals("DELETE FROM \"post\" WHERE post_id=5",
                dbManager.getPreparedStatement(post5.getDeleteQuery(), post5.getId()));
    }

    @Test
    public void selectQueryTest() {
        Assert.assertEquals("SELECT * FROM \"tag\"", getListQuery(Tag.class));
        Assert.assertEquals("SELECT * FROM \"post\"", getListQuery(Post.class));
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
                dbManager.getPreparedStatement(post0.getSiblingsQuery(Tag.class), post0.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=1",
                dbManager.getPreparedStatement(post1.getSiblingsQuery(Tag.class), post1.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=2",
                dbManager.getPreparedStatement(post2.getSiblingsQuery(Tag.class), post2.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=3",
                dbManager.getPreparedStatement(post3.getSiblingsQuery(Tag.class), post3.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=4",
                dbManager.getPreparedStatement(post4.getSiblingsQuery(Tag.class), post4.getId()));
        Assert.assertEquals("SELECT * FROM \"post__tag\" NATURAL JOIN \"tag\" WHERE post_id=5",
                dbManager.getPreparedStatement(post5.getSiblingsQuery(Tag.class), post5.getId()));
        Assert.assertEquals("SELECT * FROM \"post__user\" NATURAL JOIN \"post\" WHERE user_id=0",
                dbManager.getPreparedStatement(user.getSiblingsQuery(Post.class), user.getId()));
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
