package db.DAOInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.MetadataBean;

public interface IMetadataDAO extends IDAO<MetadataBean, UUID> 
{
    // Specific method to fetch all files belonging to a repository
    List<MetadataBean> selectByRepositoryId(UUID idRepository) throws SQLException;
}