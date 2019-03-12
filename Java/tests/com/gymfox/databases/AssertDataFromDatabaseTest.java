package com.gymfox.databases;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static com.gymfox.databases.DBManagerImplTest.FIRST_INDEX;
import static com.gymfox.databases.Entity.InvalidIdException;
import static org.junit.Assert.assertEquals;

public class AssertDataFromDatabaseTest {
    private static User user;
    private static Tag tag;
    private static Section section;
    private static Category category;
    private static Post post;
    private static Comment comment;

    @BeforeClass
    public static void changeAllData() throws SQLException, InvalidIdException {
        updateDataUser();
        updateDataTag();
        updateDataSection();
        updateDataCategory();
        updateDataPost();
        updateDataComment();
    }

    public static void updateDataUser() throws SQLException, InvalidIdException {
        user = new User(FIRST_INDEX);
        user.setName("Stewie Griffin");
        user.setEmail("stewie.griffin@mail.net");
        user.save();
    }

    public static void updateDataTag() throws SQLException, InvalidIdException {
        tag = new Tag(FIRST_INDEX);
        tag.setName("Metal");
        tag.save();
    }

    public static void updateDataSection() throws SQLException, InvalidIdException {
        section = new Section(FIRST_INDEX);
        section.setTitle("Music");
        section.save();
    }

    public static void updateDataCategory() throws SQLException, InvalidIdException {
        category = new Category(FIRST_INDEX);
        category.setTitle("Heavy metal");
        category.setSection(FIRST_INDEX);
        category.save();
    }

    public static void updateDataPost() throws SQLException, InvalidIdException {
        post = new Post(FIRST_INDEX);
        post.setTitle("Manowar");
        post.setContent("if you are not into metal");
        post.setCategory(FIRST_INDEX);
        post.save();
    }

    public static void updateDataComment() throws SQLException, InvalidIdException {
        comment = new Comment(FIRST_INDEX);
        comment.setText("you are not my friend");
        comment.setUser(FIRST_INDEX);
        comment.setUser(new User(FIRST_INDEX));
        comment.setPost(FIRST_INDEX);
        comment.save();
    }

    @Test
    public void setDataByIdTest() {
        assertEquals("Stewie Griffin", user.getName());
        assertEquals("stewie.griffin@mail.net", user.getEmail());
        assertEquals("Metal", tag.getName());
        assertEquals("Music", section.getTitle());
        assertEquals("Heavy metal", category.getTitle());
        assertEquals("Manowar", post.getTitle());
        assertEquals("if you are not into metal", post.getContent());
        assertEquals("you are not my friend", comment.getText());
        assertEquals("Stewie Griffin", comment.getUser().getName());
        assertEquals("Manowar", comment.getPost().getTitle());
    }
}
