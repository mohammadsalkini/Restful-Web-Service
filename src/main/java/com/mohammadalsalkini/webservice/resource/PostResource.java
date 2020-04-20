package com.mohammadalsalkini.webservice.resource;

import com.mohammadalsalkini.webservice.domain.Post;
import com.mohammadalsalkini.webservice.domain.User;
import com.mohammadalsalkini.webservice.exception.UserNotFoundException;
import com.mohammadalsalkini.webservice.repository.PostRepository;
import com.mohammadalsalkini.webservice.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Mohammad Alsalkini
 * @project webservice
 * @created 19.04.2020 - 19:00
 */
@RestController
@RequestMapping("/api/users")
public class PostResource {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public PostResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> retrieveAllPosts(@PathVariable int id) {

        Optional<User> optionalUser = userRepository.findById(id);

        List<Post> posts = optionalUser.get().getPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post,
                                            UriComponentsBuilder ucBuilder) {

        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        post.setUser(user);

        Post savedPost = postRepository.save(post);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(savedPost, headers, HttpStatus.CREATED);


    }

}
