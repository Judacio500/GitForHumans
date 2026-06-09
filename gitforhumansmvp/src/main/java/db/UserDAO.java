package db;

import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.UserBean;
import db.DAOInterface.IUserDAO;

public class UserDAO implements IUserDAO
{
    private static final String SQL_INSERT = "INSERT INTO Usuarios(nombre, email, password_hash) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, email, password_hash, avatar_url, is_active, fecha_registro FROM usuarios WHERE id = ?";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT id, nombre, email, password_hash, avatar_url, is_active, fecha_registro FROM usuarios WHERE email = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, nombre, email, password_hash, avatar_url, is_active, fecha_registro FROM usuarios";
    private static final String SQL_UPDATE = "UPDATE usuarios SET nombre = ?, email = ?, password_hash = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM usuarios WHERE id = ?";

    @Override
    public boolean insert(UserBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());

            // if the query works the afected rows must be 1
            int rowsAffected = ps.executeUpdate();
        
            // Considering the last line worked as expected this 
            // method will return TRUE 
            // if anything went wrong it will return FALSE
            // further more if the database exploded
            // the SQLException stops the code and sends the error to the server
            return rowsAffected > 0;
        }
    }

    @Override
    public UserBean selectById(UUID id) throws SQLException 
    {
        UserBean user = null;

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID))
        {
            ps.setObject(1,id); // Inserting the id we are looking for in the query

            try(ResultSet rs = ps.executeQuery()) // execute the complete query in a new try
            {
                if(rs.next()) // si encontramos filas
                {
                    UUID userId = rs.getObject("id", UUID.class);
                    String name = rs.getString("nombre");
                    String email = rs.getString("email");
                    String password = rs.getString("password_hash");
                    String avatarUrl = rs.getString("avatar_url");
                    Boolean is_active = rs.getBoolean("is_active");
                    OffsetDateTime registerDate = rs.getObject("fecha_registro", OffsetDateTime.class);

                    user = new UserBean(userId, name, email, password, registerDate, avatarUrl, is_active);
                }
            }
        }
        return user;
    }

    @Override
    public List<UserBean> selectAll() throws SQLException
    {
        List<UserBean> users = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                UUID id = rs.getObject("id", UUID.class);
                String name = rs.getString("nombre");
                String email = rs.getString("email");
                String password = rs.getString("password_hash");
                String avatarUrl = rs.getNString("avatar_url");
                Boolean is_active = rs.getBoolean("is_active");
                OffsetDateTime registerDate = rs.getObject("fecha_registro", OffsetDateTime.class);

                UserBean user = new UserBean(id, name, email, password, registerDate, avatarUrl, is_active);
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public boolean update(UserBean obj) throws SQLException 
    {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE))
        {

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword()); 
            ps.setObject(4, obj.getIdUser());
            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean delete(UUID id) throws SQLException 
    {
        try(Connection conn = Conn.getConnection();
            PreparedStatement ps = conn.prepareStatement(SQL_DELETE))
        {
            ps.setObject(1, id);

            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected > 0;
        }
    }

    @Override
    public UserBean selectByEmail(String email) throws SQLException 
    {
        UserBean user = null;

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_EMAIL))
        {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    UUID userId = rs.getObject("id", UUID.class);
                    String name = rs.getString("nombre");
                    String userEmail = rs.getString("email");
                    String password = rs.getString("password_hash");
                    String avatarUrl = rs.getNString("avatar_url");
                    Boolean is_active = rs.getBoolean("is_active");
                    OffsetDateTime registerDate = rs.getObject("fecha_registro", OffsetDateTime.class);

                    user = new UserBean(userId, name, userEmail, password, registerDate, avatarUrl, is_active);
                }
            }
        }
        
        return user;
    }

}