package db.DAOInterface;

import java.sql.SQLException;
import java.util.UUID;
import model.UserBean;

// Interfaz de DAO para la tabla usuario
// si cambia el tipo de llave primaria solo cambiamos el parametro k
// si cambio mi bd el patron es el mismo solo reimplemento esta interfaz
public interface IUserDAO extends IDAO<UserBean,UUID>
{
    UserBean selectByEmail(String email) throws SQLException;
}