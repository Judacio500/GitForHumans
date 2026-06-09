package db;

import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.RepositoryBean;
import db.DAOInterface.IRepositoryDAO;

public class RepositoryDAO implements IRepositoryDAO
{
    private static final String SQL_INSERT = "INSERT INTO repositorios(nombre, descripcion, ruta_local_git, id_creador) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, descripcion, ruta_local_git, id_creador, fecha_creacion FROM repositorios WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, nombre, descripcion, ruta_local_git, id_creador, fecha_creacion FROM repositorios";
    private static final String SQL_SELECT_BY_CREATOR = "SELECT id, nombre, descripcion, ruta_local_git, id_creador, fecha_creacion FROM repositorios WHERE id_creador = ?";
    private static final String SQL_UPDATE = "UPDATE repositorios SET nombre = ?, descripcion = ?, id_creador = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM repositorios WHERE id = ?";

    @Override
    public boolean insert(RepositoryBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setString(3, obj.getGit_path());
            ps.setObject(4, obj.getIdCreator());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public RepositoryBean selectById(UUID id) throws SQLException 
    {
        RepositoryBean repo = null;

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID))
        {
            ps.setObject(1, id); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                if(rs.next()) 
                {
                    UUID repoId = rs.getObject("id", UUID.class);
                    String name = rs.getString("nombre");
                    String description = rs.getString("descripcion");
                    String git_path = rs.getString("ruta_local_git");
                    UUID idCreator = rs.getObject("id_creador", UUID.class);
                    OffsetDateTime creationDate = rs.getObject("fecha_creacion", OffsetDateTime.class);

                    repo = new RepositoryBean(repoId, name, description, idCreator, creationDate, git_path);
                }
            }
        }
        
        return repo;
    }

    @Override
    public List<RepositoryBean> selectAll() throws SQLException
    {
        List<RepositoryBean> repos = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                UUID repoId = rs.getObject("id", UUID.class);
                String name = rs.getString("nombre");
                String description = rs.getString("descripcion");
                String git_path = rs.getString("ruta_local_git");
                UUID idCreator = rs.getObject("id_creador", UUID.class);
                OffsetDateTime creationDate = rs.getObject("fecha_creacion", OffsetDateTime.class);

                RepositoryBean repo = new RepositoryBean(repoId, name, description, idCreator, creationDate, git_path);
                repos.add(repo);
            }
        }
        
        return repos;
    }

    @Override
    public boolean update(RepositoryBean obj) throws SQLException 
    {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE))
        {
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setObject(3, obj.getIdCreator());
            ps.setObject(4, obj.getIdRepository());

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
    public List<RepositoryBean> selectByCreatorId(UUID idCreator) throws SQLException 
    {
        List<RepositoryBean> repos = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CREATOR))
        {
            ps.setObject(1, idCreator); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next()) 
                {
                    UUID repoId = rs.getObject("id", UUID.class);
                    String name = rs.getString("nombre");
                    String description = rs.getString("descripcion");
                    String git_path = rs.getString("ruta_local_git");
                    UUID retrievedCreatorId = rs.getObject("id_creador", UUID.class);
                    OffsetDateTime creationDate = rs.getObject("fecha_creacion", OffsetDateTime.class);

                    RepositoryBean repo = new RepositoryBean(repoId, name, description, retrievedCreatorId, creationDate, git_path);
                    repos.add(repo);
                }
            }
        }
        
        return repos;
    }
}