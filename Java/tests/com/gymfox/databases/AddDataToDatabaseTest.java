package com.gymfox.databases;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static com.gymfox.databases.Entity.InvalidIdException;
import static org.junit.Assert.assertEquals;

public class AddDataToDatabaseTest {
    private static final int FIRST_INDEX = 1;
    @BeforeClass
    public static void addDataToDatabase() throws SQLException, InvalidIdException {
        insertUser();
        insertTag();
        insertSection();
        insertCategory();
        insertPost();
        insertComment();
    }

    public static void insertUser() throws SQLException {
        User user = new User();
        user.setName("Peter Griffin");
        user.setEmail("peter.griffin@mail.net");
        user.save();
    }

    public static void insertTag() throws SQLException {
        Tag tag = new Tag();
        tag.setName("horrors");
        tag.save();
    }

    public static void insertSection() throws SQLException {
        Section section = new Section();
        section.setTitle("Films");
        section.save();
    }

    public static void insertCategory() throws SQLException {
        Category category = new Category();
        category.setTitle("Melodrama");
        category.setSection(FIRST_INDEX);
        category.save();
    }

    public static void insertPost() throws SQLException {
        Post post = new Post();
        post.setTitle("Titanic");
        post.setContent("The film has a lot of water");
        post.setCategory(FIRST_INDEX);
        post.save();
    }

    public static void insertComment() throws SQLException, InvalidIdException {
        Comment comment = new Comment();
        comment.setText("wtf?...");
        comment.setUser(FIRST_INDEX);
        comment.setUser(new User(FIRST_INDEX));
        comment.setPost(FIRST_INDEX);
        comment.save();
    }

    @Test
    public void getAllDataByIdTest() throws InvalidIdException {
        User user = new User(FIRST_INDEX);
        Tag tag = new Tag(FIRST_INDEX);
        Section section = new Section(FIRST_INDEX);
        Category category = new Category(FIRST_INDEX);
        Post post = new Post(FIRST_INDEX);
        Comment comment = new Comment(FIRST_INDEX);

        assertEquals("Peter Griffin", user.getName());
        assertEquals("peter.griffin@mail.net", user.getEmail());
        assertEquals("horrors", tag.getName());
        assertEquals("Films", section.getTitle());
        assertEquals("Melodrama", category.getTitle());
        assertEquals(FIRST_INDEX, category.getSection().getId());
        assertEquals("Titanic", post.getTitle());
        assertEquals("The film has a lot of water", post.getContent());
        assertEquals(FIRST_INDEX, post.getCategory().getId());
        assertEquals("wtf?...", comment.getText());
        assertEquals(FIRST_INDEX, comment.getUser().getId());
        assertEquals("Peter Griffin", comment.getUser().getName());
        assertEquals("Titanic", comment.getPost().getTitle());
    }
}
