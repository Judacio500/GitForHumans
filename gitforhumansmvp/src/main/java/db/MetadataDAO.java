package db;

import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.MetadataBean;
import db.DAOInterface.IMetadataDAO;

public class MetadataDAO implements IMetadataDAO
{
    private static final String SQL_INSERT = "INSERT INTO archivos_metadata(id_repositorio, nombre_archivo, azure_url, tipo_cambio, version_codigo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, id_repositorio, nombre_archivo, azure_url, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, id_repositorio, nombre_archivo, azure_url, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata";
    private static final String SQL_UPDATE = "UPDATE archivos_metadata SET nombre_archivo = ?, azure_url = ?, tipo_cambio = ?, version_codigo = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM archivos_metadata WHERE id = ?";
    private static final String SQL_SELECT_BY_REPO = "SELECT id, id_repositorio, nombre_archivo, azure_url, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata WHERE id_repositorio = ?";

    @Override
    public boolean insert(MetadataBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setObject(1, obj.getIdRepository());
            ps.setString(2, obj.getFileName());
            ps.setString(3, obj.getAzureUrl());
            ps.setString(4, obj.getChangeType());
            ps.setInt(5, obj.getCodeVersion());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public MetadataBean selectById(UUID id) throws SQLException 
    {
        MetadataBean file = null;

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID))
        {
            ps.setObject(1, id); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                if(rs.next()) 
                {
                    UUID fileId = rs.getObject("id", UUID.class);
                    UUID idRepo = rs.getObject("id_repositorio", UUID.class);
                    String fileName = rs.getString("nombre_archivo");
                    String azureUrl = rs.getString("azure_url");
                    String changeType = rs.getString("tipo_cambio");
                    int version = rs.getInt("version_codigo");
                    OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                    file = new MetadataBean(fileId, idRepo, fileName, azureUrl, changeType, version, uploadDate);
                }
            }
        }
        
        return file;
    }

    @Override
    public List<MetadataBean> selectAll() throws SQLException
    {
        List<MetadataBean> files = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                UUID fileId = rs.getObject("id", UUID.class);
                UUID idRepo = rs.getObject("id_repositorio", UUID.class);
                String fileName = rs.getString("nombre_archivo");
                String azureUrl = rs.getString("azure_url");
                String changeType = rs.getString("tipo_cambio");
                int version = rs.getInt("version_codigo");
                OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                files.add(new MetadataBean(fileId, idRepo, fileName, azureUrl, changeType, version, uploadDate));
            }
        }
        
        return files;
    }

    @Override
    public boolean update(MetadataBean obj) throws SQLException 
    {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE))
        {
            ps.setString(1, obj.getFileName());
            ps.setString(2, obj.getAzureUrl());
            ps.setString(3, obj.getChangeType());
            ps.setInt(4, obj.getCodeVersion());
            ps.setObject(5, obj.getIdFile());

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
    public List<MetadataBean> selectByRepositoryId(UUID idRepository) throws SQLException 
    {
        List<MetadataBean> files = new ArrayList<>();

        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_REPO))
        {
            ps.setObject(1, idRepository); 

            try(ResultSet rs = ps.executeQuery()) 
            {
                while(rs.next()) 
                {
                    UUID fileId = rs.getObject("id", UUID.class);
                    UUID idRepo = rs.getObject("id_repositorio", UUID.class);
                    String fileName = rs.getString("nombre_archivo");
                    String azureUrl = rs.getString("azure_url");
                    String changeType = rs.getString("tipo_cambio");
                    int version = rs.getInt("version_codigo");
                    OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                    files.add(new MetadataBean(fileId, idRepo, fileName, azureUrl, changeType, version, uploadDate));
                }
            }
        }
        
        return files;
    }
}