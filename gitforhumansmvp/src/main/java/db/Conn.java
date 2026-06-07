package db;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import auxiliars.dbAuxiliars.DBCredentialReader;

public class Conn
{
    // Utilizamos nuestra elegantisima funcion especifica
    // que manda a llamar a nuestro generalisimo lector de .properties (me siento bien Senior Full Stack Developer)
    private static final String USER = DBCredentialReader.readCredential("DB_USER");
    private static final String PASS = DBCredentialReader.readCredential("DB_PASS");
    private static final String URL  = DBCredentialReader.readCredential("DB_URL");
    private static final String DRIVER = "org.postgresql.Driver";

    private static BasicDataSource ds;

    public static DataSource getDataSource() 
    {
        if (ds == null) 
        {
            ds = new BasicDataSource();
            ds.setUrl(URL);
            ds.setUsername(USER);
            ds.setPassword(PASS);
            ds.setDriverClassName(DRIVER);
            ds.setInitialSize(5);
            ds.setMaxTotal(20);
            ds.setMaxWaitMillis(10000); 
        }
        return ds;
    }
    
    public static Connection getConnection() throws SQLException 
    {
        return getDataSource().getConnection();
    }

    public static void close(ResultSet rs) 
    {
        try 
        {
            if (rs != null) rs.close();
        } 
        catch(SQLException ex) 
        {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement ps) 
    {
        try 
        {
            if (ps != null) ps.close();
        } 
        catch(SQLException ex) 
        {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(Connection conn) 
    {
        try 
        {
            if (conn != null) conn.close();
        } 
        catch(SQLException ex) 
        {
            ex.printStackTrace(System.out);
        }
    }
}