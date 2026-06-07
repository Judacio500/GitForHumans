package auxiliars.dbAuxiliars;
import auxiliars.utils.PropertyReader;

public class DBCredentialReader 
{
    private DBCredentialReader()
    {
        throw new IllegalStateException("Cannot Instantiate this Class");
    }

    public static String readCredential(String key)
    {
        String Env = System.getenv(key);

        if(Env == null)
        {
            Env = PropertyReader.getProperty("db.properties", key);
            if(Env == null || Env.trim().isEmpty())
                return null;
        }

        return Env;
    }
}
