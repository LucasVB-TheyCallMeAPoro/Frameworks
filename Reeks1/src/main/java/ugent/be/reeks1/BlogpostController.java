package ugent.be.reeks1;

import com.fasterxml.jackson.core.TreeNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class BlogpostController {
    private BlogpostDAO dao;

    public  BlogpostController(BlogpostDAO d){dao = d;}

    @GetMapping
    public List<Blogpost> GetAll()
    {
        return dao.posts;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> GetPostById(@PathVariable("id") int id)
    {
        if(id >= dao.posts.size())
            return new ResponseEntity<>(new BlogPostNotFoundException("OHOH NOT FOUND RUN!"),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(dao.GetById(id),HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> VoegToe(@RequestBody Blogpost post)
    {

        dao.AddPost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> UpdatePostWithId(@PathVariable("id") int id, @RequestBody Blogpost bp)
    {
        if(id >= dao.posts.size())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        dao.Update(id,bp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteById(@PathVariable("id") int id)
    {
        if(id >= dao.posts.size())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        dao.DeletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
