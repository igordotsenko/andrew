package com.gymfox.databases;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {
    private static final String DELETE_QUERY   = "DELETE FROM \"%1$s\" WHERE %1$s_id=?";
    private static final String INSERT_QUERY   = "INSERT INTO \"%1$s\" (%2$s) VALUES (%3$s) RETURNING %1$s_id";
    private static final String LIST_QUERY     = "SELECT * FROM \"%s\"";
    private static final String SELECT_QUERY   = "SELECT * FROM \"%1$s\" WHERE %1$s_id=?";
    private static final String CHILDREN_QUERY = "SELECT * FROM \"%1$s\" WHERE %2$s_id=?";
    private static final String SIBLINGS_QUERY = "SELECT * FROM \"%1$s\" NATURAL JOIN \"%2$s\" WHERE %3$s_id=?";
    private static final String UPDATE_QUERY   = "UPDATE \"%1$s\" SET %2$s WHERE %1$s_id=?";

    private static final String ID = "_id";
    private static final String CREATED = "_created";
    private static final String UPDATED = "_updated";
    private static final int ZERO_ID = 0;
    private static final int FIRST_INDEX_PARAMETER = 1;

    private static Connection db = null;

    protected boolean isLoaded = false;
    protected boolean isModified = false;
    private String table;
    private int id = 0;

    protected Map<String, Object> fields = new HashMap<>();

    static class InvalidIdException extends Exception {
        public InvalidIdException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Entity() {
        this.table = getLowerCaseTableName(getClass());
    }

    public Entity(Integer id) {
        try {
            validateId(id);
            this.id = id;
            this.table = getLowerCaseTableName(getClass());
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    private void validateId(Integer id) throws InvalidIdException {
        if ( id <= ZERO_ID ) {
            throw new InvalidIdException(String.format("Invalid id. Should be more then %d", ZERO_ID));
        }
    }

    public static final void setDatabase(Connection connection) {
        validateConnectionIsNotNull(connection);

        db = connection;
    }

    private static void validateConnectionIsNotNull(Connection connection) {
        if ( connection == null ) {
            throw new NullPointerException();
        }
    }

    public final int getId() {
        return id;
    }

    void setId(int newId) {
        id = newId;
    }

    public final java.util.Date getCreated() {
        load();
        return getDate(CREATED);
    }

    public final java.util.Date getUpdated() {
        load();
        return getDate(UPDATED);
    }

    public final Object getColumn(String name) {
        load();
        return fields.get(table + "_" + name);
    }

    public final <T extends Entity> T getParent(Class<T> cls) {
        load();
        try {
            return cls.getConstructor(Integer.class).newInstance(getParentId(cls));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T extends Entity> Integer getParentId(Class<T> cls) {
        return (Integer) fields.get(getLowerCaseTableName(cls) + ID);
    }

    public final <T extends Entity> List<T> getChildren(Class<T> cls) {
        return getTableByConditions(cls, getChildrenQuery(cls));
    }

    final <T extends Entity> String getChildrenQuery(Class<T> cls) {
        return String.format(CHILDREN_QUERY, getLowerCaseTableName(cls), table);
    }

    public final <T extends Entity> List<T> getSiblings(Class<T> cls) {
        return getTableByConditions(cls, getSiblingsQuery(cls));
    }

    final <T extends Entity> String getSiblingsQuery(Class<T> cls) {
        String siblingTable = getJoinTableName(getLowerCaseTableName(cls), table);

        return String.format(SIBLINGS_QUERY, siblingTable, getLowerCaseTableName(cls), table);
    }

    private <T extends Entity> List<T> getTableByConditions(Class<T> cls, String query) {
        try {
            return rowsToEntities(cls, getResultSetByQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResultSet getResultSetByQuery(String query) throws SQLException {
        return getPreparedStatement(query, id).executeQuery();
    }

    private static <T extends Entity> String getLowerCaseTableName(Class<T> cls) {
        return cls.getSimpleName().toLowerCase();
    }

    public final void setColumn(String name, Object value) {
        fields.put(table + "_" + name, value);
    }

    public final void setParent(String name, Integer id) {
        fields.put(name + ID, id);
    }

    private PreparedStatement getPreparedStatement(String query, int id) throws SQLException {
        PreparedStatement preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(FIRST_INDEX_PARAMETER, id);

        return preparedStatement;
    }

    final String getSelectQuery() {
        return String.format(SELECT_QUERY, table);
    }

    private void load() {
        if ( !isLoaded ) {
            try {
                ResultSet resultSet = getPreparedStatement(getSelectQuery(), id).executeQuery();

                if ( resultSet.next() ) {
                    fields = resultSetToMap(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            isLoaded = true;
         }
    }

    private static Map<String, Object> resultSetToMap(ResultSet resultSet) throws SQLException {
        Map<String, Object> newFields = new HashMap<>();
        int columnCount = resultSet.getMetaData().getColumnCount();

        for ( int i = 1; i <= columnCount; i++ ) {
            String fieldName = resultSet.getMetaData().getColumnName(i);
            Object fieldValue = resultSet.getObject(i);

            newFields.put(fieldName, fieldValue);
        }

        return newFields;
    }

    private void insert() throws SQLException {
        if ( !fields.isEmpty()  ) {
            Statement statement = db.createStatement();
            statement.execute(getInsertQuery());

            ResultSet generatedKey = statement.getGeneratedKeys();

            while (generatedKey.next()) {
                id = generatedKey.getInt(FIRST_INDEX_PARAMETER);
            }
        }
    }

    final String getInsertQuery() {
        return String.format(INSERT_QUERY, table, join(fields.keySet()), join(getValuesForInsert()));
    }

    private Collection<String> getValuesForInsert() {
        List<String>valuesList = new ArrayList<>();

        for ( Object value : fields.values() ) {
            valuesList.add(getValuesStringRepresentation(value));
        }

        return  valuesList;
    }

    private void update() throws SQLException {
        if ( !fields.isEmpty() && !isModified ) {
            getUpdateStatement().executeUpdate();

            isModified = true;
        }
    }

    private PreparedStatement getUpdateStatement() throws SQLException {
        return getPreparedStatement(getUpdateQuery(), id);
    }

    final String getUpdateQuery() {
        return String.format(UPDATE_QUERY, table, getPairForUpdateQuery());
    }

    private String getPairForUpdateQuery() {
        List<String> setPairString = new ArrayList<>();

        for ( Map.Entry entity : fields.entrySet() ) {
            setPairString.add(getPairStringRepresentation(entity));
        }

        return String.join(", ", setPairString);
    }

    private String getValuesStringRepresentation(Object value) {
        return value instanceof String ? String.format("'%s'", value) : String.format("%s", value);
    }

    private String getPairStringRepresentation(Map.Entry entity) {
        return String.format("%s = %s", entity.getKey(), getValuesStringRepresentation(entity.getValue()));
    }

    public final void delete() throws SQLException {
        getDeleteStatement().execute();
    }

    private PreparedStatement getDeleteStatement() throws SQLException {
        return getPreparedStatement(getDeleteQuery(), id);
    }

    final String getDeleteQuery() {
        return String.format(DELETE_QUERY, table);
    }

    public final void save() throws SQLException {
        if ( id == ZERO_ID ) {
            insert();

            return;
        }

        update();
    }

    protected static <T extends Entity> List<T> all(Class<T> cls) {
        try {
            return rowsToEntities(cls, generateResultSet(cls));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Entity> ResultSet generateResultSet(Class<T> cls) throws SQLException {

        return db.createStatement().executeQuery(getListQuery(cls));
    }

    final static <T extends Entity> String getListQuery(Class<T> cls) {
        return String.format(LIST_QUERY, getLowerCaseTableName(cls));
    }

    private static Collection<String> genPlaceholders(int size) {
        validateSize(size);

        return genPlaceholders(size, "?");
    }

    private static Collection<String> genPlaceholders(int size, String placeholder) {
        List<String> placeholders = new ArrayList<>();

        for ( int i = 1; i <= size; i++ ) {
            placeholders.add(placeholder);
        }

        return placeholders;
    }

    private static String getJoinTableName(String leftTable, String rightTable) {
        return leftTable.compareTo(rightTable) < 0 ? leftTable + "__" + rightTable : rightTable + "__" + leftTable;
    }

    private java.util.Date getDate(String column) {
        load();

        return new Date(((Integer) fields.get(table + column)).longValue());
    }

    private static String join(Collection<String> sequence) {
        validateSize(sequence.size());

        return join(sequence, ", ");
    }

    private static String join(Collection<String> sequence, String glue) {
        return String.join(glue, sequence);
    }

    private static <T extends Entity> List<T> rowsToEntities(Class<T> cls, ResultSet rows) {
        List<T> entitiesList = new ArrayList<>();

        try {
            while (rows.next()) {
                entitiesList.add((T) rowToEntity(cls, rows));
            }
        } catch (IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
        }

        return entitiesList;
    }

    private static void validateSize(int size) {
        if ( size <= 0 ) {
            throw new IllegalArgumentException();
        }
    }

    private static <T extends Entity> Entity rowToEntity(Class<T> cls, ResultSet resultSet)
            throws IllegalAccessException, InstantiationException {
        try {
            Entity entity = cls.getConstructor().newInstance();
            entity.fields = resultSetToMap(resultSet);
            entity.id = (Integer) entity.fields.get(getLowerCaseTableName(cls) + ID);
            entity.table = getLowerCaseTableName(cls);
            entity.isLoaded = true;

            return entity;
        } catch (InvocationTargetException | NoSuchMethodException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
