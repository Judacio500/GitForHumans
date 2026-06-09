package auxiliars.utils.Git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

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

    public static void autoCommit(String gitPath, String commitMessage, String authorName, String authorEmail) throws GitAPIException, IOException
    {
        File sourceCodeFolder = new File(gitPath);

        try (Git git = Git.open(sourceCodeFolder)) 
        {
            git.add().addFilepattern(".").call();

            git.add().setUpdate(true).addFilepattern(".").call();

            git.commit().setMessage(commitMessage).setAuthor(authorName, authorEmail).call();
               
            System.out.println("Autocommit: " + commitMessage);
        }
    }

    public static List<Map<String, String>> getHistory(String gitPath) throws GitAPIException, IOException
    {
        List<Map<String, String>> history = new ArrayList<>();
        File sourceCodeFolder = new File(gitPath);

        if (!new File(sourceCodeFolder, ".git").exists()) 
        {
            return history;
        }

        try (Git git = Git.open(sourceCodeFolder)) 
        {
            Iterable<RevCommit> commits = git.log().call();
            
            for (RevCommit commit : commits) 
            {
                Map<String, String> commitData = new HashMap<>();
                
                commitData.put("hash", commit.getName().substring(0, 7)); 
                commitData.put("message", commit.getShortMessage());
                commitData.put("author", commit.getAuthorIdent().getName());
                commitData.put("date", commit.getAuthorIdent().getWhen().toString());
                
                history.add(commitData);
            }
        }
        catch (GitAPIException e)
        {
            System.out.println("The repository doesn't have any commits: " + e.getMessage());
        }
        
        return history;
    }
}
