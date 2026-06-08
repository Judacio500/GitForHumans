package db.DAOInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import model.LFDiffBean;

public interface LFDiffDAO extends IDAO<LFDiffBean, UUID> 
{
    // Specific method to fetch the diff history of a single file
    List<LFDiffBean> selectByFileId(UUID idFile) throws SQLException;
}