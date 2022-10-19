package ugent.be.reeks1;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogpostDAO {

    public ArrayList<Blogpost> posts = new ArrayList<>();

    public  BlogpostDAO() {
        posts.add(new Blogpost("Hello World!","Test content no hate!"));
    }

    public void AddPost(Blogpost bp)
    {
        posts.add(bp);
    }

    public void DeletePost(int id)
    {
        posts.remove(id);
    }

    public void Update(int id, Blogpost pb)
    {
        posts.get(id).setTitle(pb.getTitle()); ;
        posts.get(id).setContent(pb.getContent()); ;
    }

    public  Blogpost GetById(int id)
    {
        return  posts.get(id);
    }

}
