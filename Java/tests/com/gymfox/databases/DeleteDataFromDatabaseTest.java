package com.gymfox.databases;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static com.gymfox.databases.Entity.*;
import static org.junit.Assert.assertNull;

public class DeleteDataFromDatabaseTest {
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
        Comment comment = new Comment(1);
        comment.delete();
        comment.save();
    }

    private static void deletePost() throws InvalidIdException, SQLException {
        Post post = new Post(1);
        post.delete();
        post.save();
    }

    private static void deleteCategory() throws InvalidIdException, SQLException {
        Category category = new Category(1);
        category.delete();
        category.save();
    }

    private static void deleteSection() throws InvalidIdException, SQLException {
        Section section = new Section(1);
        section.delete();
        section.save();
    }

    private static void deleteTag() throws InvalidIdException, SQLException {
        Tag tag = new Tag(1);
        tag.delete();
        tag.save();
    }

    private static void deleteUser() throws SQLException, InvalidIdException {
        User user = new User(1);
        user.delete();
        user.save();
    }

    @Test
    public void deleteFromDatabaseTest() throws InvalidIdException {
        assertNull(null, new User(1).getName());
    }
}
