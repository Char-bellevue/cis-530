/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.controller;

import edu.bellevue.cis530.week6.dto.PostDto;
import edu.bellevue.cis530.week6.service.PostClientService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the client endpoints under /client/posts.
 */
@RestController
@RequestMapping("/client/posts")
public class PostClientController {

    private final PostClientService gPostClientService;

    /**
     * Creates the controller with the service that Spring injects.
     * @param postClientService PostClientService the service that retrieves posts.
     * @return a new PostClientController instance.
     */
    public PostClientController(PostClientService postClientService) {
        this.gPostClientService = postClientService;
    } // end PostClientController

    /**
     * Handles GET /client/posts and returns every post from the public API.
     * @return ResponseEntity of List of PostDto with HTTP status 200.
     */
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(gPostClientService.getAllPosts());
    } // end getAllPosts

    /**
     * Handles GET /client/posts/{id} and returns one post.
     * @param id Long the post id taken from the URL path.
     * @return ResponseEntity of PostDto with HTTP status 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(gPostClientService.getPostById(id));
    } // end getPostById

    /**
     * Handles GET /client/posts/user/{userId} and returns all posts for that user.
     * @param userId Long the user id taken from the URL path.
     * @return ResponseEntity of List of PostDto with HTTP status 200.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(gPostClientService.getPostsByUserId(userId));
    } // end getPostsByUserId
} // end PostClientController
