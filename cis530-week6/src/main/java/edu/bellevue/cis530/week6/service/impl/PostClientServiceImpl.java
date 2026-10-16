/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.service.impl;

import edu.bellevue.cis530.week6.client.JsonPlaceholderClient;
import edu.bellevue.cis530.week6.dto.PostDto;
import edu.bellevue.cis530.week6.service.PostClientService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service implementation that passes post requests from the controller to the JSONPlaceholder client.
 */
@Service
public class PostClientServiceImpl implements PostClientService {

    private final JsonPlaceholderClient gJsonPlaceholderClient;

    /**
     * Creates the service with the client that Spring injects.
     * @param jsonPlaceholderClient JsonPlaceholderClient the class that calls the external API.
     * @return a new PostClientServiceImpl instance.
     */
    public PostClientServiceImpl(JsonPlaceholderClient jsonPlaceholderClient) {
        this.gJsonPlaceholderClient = jsonPlaceholderClient;
    } // end PostClientServiceImpl

    /**
     * Retrieves all posts by calling the client.
     * @return List of PostDto holding every post.
     */
    @Override
    public List<PostDto> getAllPosts() {
        return gJsonPlaceholderClient.getAllPosts(); // Delegate to the client.
    } // end getAllPosts

    /**
     * Retrieves one post by calling the client.
     * @param id Long the unique id of the post.
     * @return PostDto the matching post.
     */
    @Override
    public PostDto getPostById(Long id) {
        return gJsonPlaceholderClient.getPostById(id); // Delegate to the client.
    } // end getPostById

    /**
     * Retrieves all posts for one user by calling the client.
     * @param userId Long the id of the user.
     * @return List of PostDto holding that user's posts.
     */
    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        return gJsonPlaceholderClient.getPostsByUserId(userId); // Delegate to the client.
    } // end getPostsByUserId
} // end PostClientServiceImpl
