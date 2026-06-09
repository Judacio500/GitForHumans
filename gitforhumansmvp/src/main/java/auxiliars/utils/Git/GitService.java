package auxiliars.utils.Git;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitService 
{
    private GitService() 
    {
        throw new IllegalStateException("Cannot Instantiate this Class");
    }    

    public static void initRepository(String gitPath) throws GitAPIException
    {
        File sourceCodeFolder = new File(gitPath);

        try (Git git = Git.init().setDirectory(sourceCodeFolder).call()) 
        {
            System.out.println("Repository Initilized at: " + git.getRepository().getDirectory());
        } 
    }
}
