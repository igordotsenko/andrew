package com.gymfox.databases;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static com.gymfox.databases.Entity.*;
import static org.junit.Assert.assertNull;

public class DeleteDataFromDatabaseTest {
    static final Integer FIRST_INDEX = 1;
    private static User user;
    private static Tag tag;
    private static Section section;
    private static Category category;
    private static Post post;
    private static Comment comment;

    @BeforeClass
    public static void deleteFromTable() throws SQLException, InvalidIdException {
        deleteComment();
        deletePost();
        deleteCategory();
        deleteSection();
        deleteTag();
        deleteUser();
    }

    private static void deleteComment() throws InvalidIdException, SQLException {
        comment = new Comment(FIRST_INDEX);
        comment.delete();
        comment.save();
    }

    private static void deletePost() throws InvalidIdException, SQLException {
        post = new Post(FIRST_INDEX);
        post.delete();
        post.save();
    }

    private static void deleteCategory() throws InvalidIdException, SQLException {
        category = new Category(FIRST_INDEX);
        category.delete();
        category.save();
    }

    private static void deleteSection() throws InvalidIdException, SQLException {
        section = new Section(FIRST_INDEX);
        section.delete();
        section.save();
    }

    private static void deleteTag() throws InvalidIdException, SQLException {
        tag = new Tag(FIRST_INDEX);
        tag.delete();
        tag.save();
    }

    private static void deleteUser() throws SQLException, InvalidIdException {
        user = new User(FIRST_INDEX);
        user.delete();
        user.save();
    }

    @Test
    public void deleteFromDatabaseTest() {
        assertNull(null, comment.getText());
        assertNull(null, post.getContent());
        assertNull(null, post.getTitle());
        assertNull(null, category.getTitle());
        assertNull(null, tag.getName());
        assertNull(null, user.getName());
        assertNull(null, user.getEmail());
    }
}
