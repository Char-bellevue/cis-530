/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
Method getAllPosts() supplied in the CIS 530 Assignment 6.2 instructions. Bellevue University.
*/
package edu.bellevue.cis530.week6.client;

import edu.bellevue.cis530.week6.dto.PostDto;
import edu.bellevue.cis530.week6.exception.ApiClientException;
import edu.bellevue.cis530.week6.exception.ResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Client class that directly calls the public JSONPlaceholder API for the /posts resource.
 */
@Component
public class JsonPlaceholderClient {

    private final WebClient gWebClient;

    /**
     * Creates the client with the WebClient bean that Spring injects.
     * @param webClient WebClient the configured client pointing at the JSONPlaceholder base URL.
     * @return a new JsonPlaceholderClient instance.
     */
    public JsonPlaceholderClient(WebClient webClient) {
        this.gWebClient = webClient;
    } // end JsonPlaceholderClient

    /**
     * Retrieves every post from GET /posts.
     * @return List of PostDto holding all posts, or an empty list when nothing is returned.
     */
    public List<PostDto> getAllPosts() {
        try {
            List<PostDto> lPosts = gWebClient.get()
                    .uri("/posts") // Call /posts on the base URL.
                    .retrieve()
                    .bodyToFlux(PostDto.class) // Convert the JSON array into PostDto objects.
                    .collectList()
                    .block(); // Wait for the result so it returns like a normal method.

            return lPosts != null ? lPosts : Collections.emptyList();
        } catch (WebClientException lException) {
            throw new ApiClientException("Unable to retrieve posts from JSONPlaceholder.", lException);
        }
    } // end getAllPosts

    /**
     * Retrieves one post from GET /posts/{id}.
     * @param id Long the unique id of the post to retrieve.
     * @return PostDto the matching post.
     */
    public PostDto getPostById(Long id) {
        try {
            PostDto lPost = gWebClient.get()
                    .uri("/posts/{id}", id) // Use the id as a path variable.
                    .retrieve()
                    .bodyToMono(PostDto.class) // Convert the JSON object into one PostDto.
                    .block();

            if (lPost == null) {
                throw new ResourceNotFoundException("Post not found with id: " + id);
            }
            return lPost;
        } catch (WebClientResponseException.NotFound lException) {
            throw new ResourceNotFoundException("Post not found with id: " + id); // The API returned 404.
        } catch (WebClientException lException) {
            throw new ApiClientException("Unable to retrieve post " + id + " from JSONPlaceholder.", lException);
        }
    } // end getPostById

    /**
     * Retrieves all posts for one user from GET /posts?userId=value.
     * @param userId Long the id of the user whose posts are requested.
     * @return List of PostDto holding that user's posts, or an empty list when nothing is returned.
     */
    public List<PostDto> getPostsByUserId(Long userId) {
        try {
            List<PostDto> lPosts = gWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/posts")
                            .queryParam("userId", userId) // Send userId as a query parameter.
                            .build())
                    .retrieve()
                    .bodyToFlux(PostDto.class)
                    .collectList()
                    .block();

            return lPosts != null ? lPosts : Collections.emptyList();
        } catch (WebClientException lException) {
            throw new ApiClientException("Unable to retrieve posts for user " + userId + " from JSONPlaceholder.", lException);
        }
    } // end getPostsByUserId
} // end JsonPlaceholderClient
