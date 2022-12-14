package be.ugent.reeks1;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BlogController {
    private final Logger logger = LoggerFactory.getLogger(BlogController.class);
    private final BlogPostDAOMemory postDAO;

    Counter createCounter = Metrics.counter("Total_operations","operation","create");
    Counter readCounter = Metrics.counter("Total_operations","operation", "read");
    Counter updateCounter = Metrics.counter("Total_operations", "operation", "update");
    Counter deleteCounter = Metrics.counter("Total_operations","operation","delete");

    public BlogController(BlogPostDAOMemory postDAO) {


        this.postDAO = postDAO;
    }

    /**
     * Provide a list of all blogPosts.
     */
    @GetMapping("/posts")
    public List<BlogPost> getPosts() {

        readCounter.increment();
        return postDAO.getAllPosts();
    }

    /**
     * Provide the details of a blogPost with the given id. Throw PostNotFoundException if id doesn't exist.
     */
    @GetMapping("/posts/{id}")
    public BlogPost getPost(@PathVariable("id") long id) {
        readCounter.increment();
        return postDAO.getPost(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    /**
     * Creates a new BlogPost, setting its URL as the Location header on the
     * response.
     */
    @PostMapping("/posts")
    public ResponseEntity<Void> addPost(@RequestBody BlogPost post) {
        postDAO.addPost(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        createCounter.increment();
        return ResponseEntity.created(location).build();
    }

    /**
     * Removes the blogPost with the given id.
     */
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") long id) {
        deleteCounter.increment();
        postDAO.deletePost(id);
    }

    /**
     * Update the blogPost with the given id.
     * Return http status 409 (Conflict) if id's do not match between body and path.
     */
    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestBody BlogPost post, @PathVariable("id") long id) {
        if (id != post.getId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID in path does not match ID in the body");
        }
        updateCounter.increment();
        postDAO.updatePost(id, post);
    }

    /**
     * Explicit exception handler to map PostNotFoundException to a 404 Not Found HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public void handleNotFound(Exception ex) {
        logger.warn("Exception is: " + ex.getMessage());
        // return empty 404
    }
}
