package db;

import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.CollaboratorBean;

public class CollaboratorDAO 
{
    private static final String SQL_INSERT = "INSERT INTO colaboradores(id_usuario, id_repositorio, nivel_permiso) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id_usuario, id_repositorio, nivel_permiso, fecha_asignacion FROM colaboradores";
    private static final String SQL_UPDATE = "UPDATE colaboradores SET nivel_permiso = ? WHERE id_usuario = ? AND id_repositorio = ?";
    private static final String SQL_DELETE = "DELETE FROM colaboradores WHERE id_usuario = ? AND id_repositorio = ?";
    private static final String SQL_SELECT_BY_USER = "SELECT id_usuario, id_repositorio, nivel_permiso, fecha_asignacion FROM colaboradores WHERE id_usuario = ?";
    private static final String SQL_SELECT_BY_REPO = "SELECT id_usuario, id_repositorio, nivel_permiso, fecha_asignacion FROM colaboradores WHERE id_repositorio = ?";

    public boolean insert(CollaboratorBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setObject(1, obj.getIdUser());
            ps.setObject(2, obj.getIdRepository());
            ps.setString(3, obj.getPermissionLevel());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<CollaboratorBean> selectAll() throws SQLException
    {
        List<CollaboratorBean> collabs = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                UUID userId = rs.getObject("id_usuario", UUID.class);
                UUID repoId = rs.getObject("id_repositorio", UUID.class);
                String permission = rs.getString("nivel_permiso");
                OffsetDateTime assignmentDate = rs.getObject("fecha_asignacion", OffsetDateTime.class);

                collabs.add(new CollaboratorBean(userId, repoId, permission, assignmentDate));
            }
        }
        
        return collabs;
    }

    public boolean update(CollaboratorBean obj) throws SQLException 
    {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE))
        {
            ps.setString(1, obj.getPermissionLevel());
            ps.setObject(2, obj.getIdUser());
            ps.setObject(3, obj.getIdRepository());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean delete(UUID idUser, UUID idRepository) throws SQLException 
    {
        try(Connection conn = Conn.getConnection();
            PreparedStatement ps = conn.prepareStatement(SQL_DELETE))
        {
            ps.setObject(1, idUser);
            ps.setObject(2, idRepository);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<CollaboratorBean> selectByUserId(UUID idUser) throws SQLException 
    {
        List<CollaboratorBean> collabs = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_USER))
        {
            ps.setObject(1, idUser); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next()) 
                {
                    UUID retrievedUserId = rs.getObject("id_usuario", UUID.class);
                    UUID repoId = rs.getObject("id_repositorio", UUID.class);
                    String permission = rs.getString("nivel_permiso");
                    OffsetDateTime assignmentDate = rs.getObject("fecha_asignacion", OffsetDateTime.class);

                    collabs.add(new CollaboratorBean(retrievedUserId, repoId, permission, assignmentDate));
                }
            }
        }
        
        return collabs;
    }

    public List<CollaboratorBean> selectByRepositoryId(UUID idRepository) throws SQLException 
    {
        List<CollaboratorBean> collabs = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_REPO))
        {
            ps.setObject(1, idRepository); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next()) 
                {
                    UUID userId = rs.getObject("id_usuario", UUID.class);
                    UUID retrievedRepoId = rs.getObject("id_repositorio", UUID.class);
                    String permission = rs.getString("nivel_permiso");
                    OffsetDateTime assignmentDate = rs.getObject("fecha_asignacion", OffsetDateTime.class);

                    collabs.add(new CollaboratorBean(userId, retrievedRepoId, permission, assignmentDate));
                }
            }
        }
        
        return collabs;
    }
}