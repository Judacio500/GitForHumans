package db.DAOInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.RepositoryBean;

public interface IRepositoryDAO extends IDAO<RepositoryBean, UUID> 
{
    // Specific method to fetch all repositories from a specific creator
    List<RepositoryBean> selectByCreatorId(UUID idCreator) throws SQLException;
}