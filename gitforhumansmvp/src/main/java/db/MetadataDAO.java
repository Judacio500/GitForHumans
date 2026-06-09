package db;
import java.sql.*;
import java.util.*;
import java.time.OffsetDateTime;
import model.MetadataBean;
import db.DAOInterface.IMetadataDAO;

public class MetadataDAO implements IMetadataDAO
{
    private static final String SQL_INSERT = "INSERT INTO archivos_metadata(id_repositorio, id_usuario_subio, nombre_archivo, ruta_almacenamiento, tamanio_bytes, tipo_cambio, version_codigo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, id_repositorio, id_usuario_subio, nombre_archivo, ruta_almacenamiento, tamanio_bytes, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, id_repositorio, id_usuario_subio, nombre_archivo, ruta_almacenamiento, tamanio_bytes, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata";
    private static final String SQL_SELECT_BY_REPO = "SELECT id, id_repositorio, id_usuario_subio, nombre_archivo, ruta_almacenamiento, tamanio_bytes, tipo_cambio, version_codigo, fecha_subida FROM archivos_metadata WHERE id_repositorio = ?";
    private static final String SQL_UPDATE = "UPDATE archivos_metadata SET nombre_archivo = ?, ruta_almacenamiento = ?, tipo_cambio = ?, version_codigo = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM archivos_metadata WHERE id = ?";

    public boolean insert(MetadataBean obj) throws SQLException 
    {
        try(Connection conn = Conn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT))
        {
            ps.setObject(1, obj.getIdRepository());
            ps.setObject(2, obj.getIdUserUpload());
            ps.setString(3, obj.getFileName());
            ps.setString(4, obj.getFilePath());
            ps.setLong(5, obj.getByteSize());
            ps.setString(6, obj.getChangeType());
            ps.setInt(7, obj.getCodeVersion());

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
                    UUID idUserUpload = rs.getObject("id_usuario_subio", UUID.class);
                    String fileName = rs.getString("nombre_archivo");
                    String filePath = rs.getString("ruta_almacenamiento");
                    String changeType = rs.getString("tipo_cambio");
                    int version = rs.getInt("version_codigo");
                    long byteSize = rs.getLong("tamanio_bytes");
                    OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                    file = new MetadataBean(fileId, idRepo, fileName, filePath, changeType, version, uploadDate, idUserUpload, byteSize);
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
                UUID idUserUpload = rs.getObject("id_usuario_subio", UUID.class);
                String fileName = rs.getString("nombre_archivo");
                String filePath = rs.getString("ruta_almacenamiento");
                String changeType = rs.getString("tipo_cambio");
                int version = rs.getInt("version_codigo");
                long byteSize = rs.getLong("tamanio_bytes");
                OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                MetadataBean file = new MetadataBean(fileId, idRepo, fileName, filePath, changeType, version, uploadDate, idUserUpload, byteSize);
                files.add(file);
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
            ps.setString(2, obj.getfilePath());
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
                    UUID idUserUpload = rs.getObject("id_usuario_subio", UUID.class);
                    String fileName = rs.getString("nombre_archivo");
                    String filePath = rs.getString("ruta_almacenamiento");
                    String changeType = rs.getString("tipo_cambio");
                    int version = rs.getInt("version_codigo");
                    long byteSize = rs.getLong("tamanio_bytes");
                    OffsetDateTime uploadDate = rs.getObject("fecha_subida", OffsetDateTime.class);

                    MetadataBean file = new MetadataBean(fileId, idRepo, fileName, filePath, changeType, version, uploadDate, idUserUpload, byteSize);
                    files.add(file);
                }
            }
        }
        
        return files;
    }
}