package auxiliars.utils;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil 
{
    // Literally makes the hash take longer in a logarithmic scale
    private static final int WORKLOAD = 12;

    // Used during Registration
    public static String hashPassword(String plainPassword) 
    {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainPassword, salt);
    }

    // Used during Login
    public static boolean verifyPassword(String plainPassword, String hashedPassword) 
    {
        // If the database hash is null or empty, fail immediately to prevent errors
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) 
        {
            return false;
        }

        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}