package com.mohammadalsalkini.webservice.resource;

import com.mohammadalsalkini.webservice.domain.User;
import com.mohammadalsalkini.webservice.exception.UserNotFoundException;
import com.mohammadalsalkini.webservice.repository.UserRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Mohammad Alsalkini
 * @project webservice
 * @created 18.04.2020 - 18:30
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(path = {"","/"})
    public ResponseEntity<List<Resource<User>>> retrieveAllUsers() {

        Optional<List<User>> listOptional = Optional.of(userRepository.findAll());

        if (!listOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        return new ResponseEntity<>(
                listOptional.get().stream()
                        .map(user -> {
                            Resource<User> resource = new Resource<>(user);
                            ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
                            resource.add(linkTo.withRel("all-users"));
                            return resource;
                        }).collect(Collectors.toList()), HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Resource<User>> retrieveUser(@PathVariable int id) {

        return userRepository.findById(id)
                .map(user ->
                {
                    Resource<User> resource = new Resource<>(user);
                    ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
                    resource.add(linkTo.withRel("all-users"));
                    return new ResponseEntity<>(resource, HttpStatus.OK);
                })
                .orElseThrow(() -> new UserNotFoundException("id: " + id));
    }


    @PostMapping(path = {"", "/"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(
            @Valid @RequestBody User user, UriComponentsBuilder ucBuilder) throws Exception {

        //todo return Exception

//        if (service.findOne(user.getId()).isPresent()) {
//            throw new Exception(String.valueOf(user.getId()));
//        }

        User savedUser = userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(savedUser, headers, HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseThrow(() -> new UserNotFoundException("id: " + id));
    }


}





