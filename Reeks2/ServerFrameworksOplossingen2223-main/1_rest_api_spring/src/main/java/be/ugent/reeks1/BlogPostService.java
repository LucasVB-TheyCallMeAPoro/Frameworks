package be.ugent.reeks1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService implements BlogPostInterface{

    BlogPostRepository database;
    public BlogPostService(BlogPostRepository db){
        database = db;
    }
    @Override
    public List<BlogPost> getAllPosts() {
        return database.findAll();
    }

    @Override
    public void addPost(BlogPost blogPost) {
        database.save(blogPost);
    }

    @Override
    public void updatePost(long id, BlogPost blogPost) {
        BlogPost bp = database.getOne(id);
        bp.setTitle(blogPost.getTitle());
        bp.setContent(blogPost.getContent());
    }

    @Override
    public Optional<BlogPost> getPost(long id) {
        return Optional.ofNullable(database.getOne(id));
    }

    @Override
    public void deletePost(long id) {
        database.deleteById(id);
    }
}
