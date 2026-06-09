package db;

import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.LFDiffBean;
import db.DAOInterface.ILFDiffDAO;

public class LFDiffDAO implements ILFDiffDAO
{
    private static final String SQL_INSERT = "INSERT INTO human_diffs(id_archivo, id_usuario, descripcion_vineta) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, id_archivo, id_usuario, descripcion_vineta, fecha_commit FROM human_diffs WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, id_archivo, id_usuario, descripcion_vineta, fecha_commit FROM human_diffs";
    private static final String SQL_SELECT_BY_FILE = "SELECT id, id_archivo, id_usuario, descripcion_vineta, fecha_commit FROM human_diffs WHERE id_archivo = ?";
    private static final String SQL_UPDATE = "UPDATE human_diffs SET descripcion_vineta = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM human_diffs WHERE id = ?";

    @Override
    public boolean insert(LFDiffBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setObject(1, obj.getIdFile());
            ps.setObject(2, obj.getIdUser());
            ps.setString(3, obj.getBulletDescription());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public LFDiffBean selectById(UUID id) throws SQLException 
    {
        LFDiffBean diff = null;

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID))
        {
            ps.setObject(1, id); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                if(rs.next()) 
                {
                    UUID diffId = rs.getObject("id", UUID.class);
                    UUID fileId = rs.getObject("id_archivo", UUID.class);
                    String description = rs.getString("descripcion_vineta");
                    OffsetDateTime commitDate = rs.getObject("fecha_commit", OffsetDateTime.class);
                    UUID idUser = rs.getObject("id_usuario", UUID.class);

                    diff = new LFDiffBean(diffId, fileId, idUser, description, commitDate);
                }
            }
        }
        
        return diff;
    }

    @Override
    public List<LFDiffBean> selectAll() throws SQLException
    {
        List<LFDiffBean> diffs = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                UUID diffId = rs.getObject("id", UUID.class);
                UUID fileId = rs.getObject("id_archivo", UUID.class);
                String description = rs.getString("descripcion_vineta");
                OffsetDateTime commitDate = rs.getObject("fecha_commit", OffsetDateTime.class);
                UUID idUser = rs.getObject("id_usuario", UUID.class);

                LFDiffBean diff = new LFDiffBean(diffId, fileId, idUser, description, commitDate);
                diffs.add(diff);
            }
        }
        
        return diffs;
    }

    @Override
    public boolean update(LFDiffBean obj) throws SQLException 
    {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE))
        {
            ps.setString(1, obj.getBulletDescription());
            ps.setObject(2, obj.getIdDiff());

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
    public List<LFDiffBean> selectByFileId(UUID idFile) throws SQLException 
    {
        List<LFDiffBean> diffs = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_FILE))
        {
            ps.setObject(1, idFile); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next()) 
                {
                    UUID diffId = rs.getObject("id", UUID.class);
                    UUID retrievedFileId = rs.getObject("id_archivo", UUID.class);
                    UUID idUser = rs.getObject("id_usuario", UUID.class);
                    String description = rs.getString("descripcion_vineta");
                    OffsetDateTime commitDate = rs.getObject("fecha_commit", OffsetDateTime.class);

                    LFDiffBean diff = new LFDiffBean(diffId, retrievedFileId, idUser, description, commitDate);
                    diffs.add(diff);
                }
            }
        }
        
        return diffs;
    }
}