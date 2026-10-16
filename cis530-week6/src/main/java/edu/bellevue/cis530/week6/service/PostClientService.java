/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.service;

import edu.bellevue.cis530.week6.dto.PostDto;
import java.util.List;

/**
 * Service contract for retrieving posts from the external JSONPlaceholder API.
 */
public interface PostClientService {

    /**
     * Retrieves all posts.
     * @return List of PostDto holding every post.
     */
    List<PostDto> getAllPosts();

    /**
     * Retrieves one post by its id.
     * @param id Long the unique id of the post.
     * @return PostDto the matching post.
     */
    PostDto getPostById(Long id);

    /**
     * Retrieves all posts that belong to one user.
     * @param userId Long the id of the user.
     * @return List of PostDto holding that user's posts.
     */
    List<PostDto> getPostsByUserId(Long userId);
} // end PostClientService
