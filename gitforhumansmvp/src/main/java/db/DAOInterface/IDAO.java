package db.DAOInterface;

import java.sql.SQLException;
import java.util.List;

// SIMPLE CRUD INTERFACE
public interface IDAO<T, K> 
{
    // C - Create
    boolean insert(T obj) throws SQLException;
    
    // R - Read
    T selectById(K id) throws SQLException;
    List<T> selectAll() throws SQLException;
    
    // U - Update
    boolean update(T obj) throws SQLException;
    
    // D - Delete
    boolean delete(K id) throws SQLException;
}