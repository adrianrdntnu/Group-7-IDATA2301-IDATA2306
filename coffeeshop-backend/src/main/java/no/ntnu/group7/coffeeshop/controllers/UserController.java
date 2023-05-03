package no.ntnu.group7.coffeeshop.controllers;

import no.ntnu.group7.coffeeshop.dto.UserProfileDto;
import no.ntnu.group7.coffeeshop.model.User;
import no.ntnu.group7.coffeeshop.services.AccessUserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API controller serving endpoints for users.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private AccessUserService userService;

  /**
   * Return user profile information
   *
   * @param username Username for which the profile is requested
   * @return The profile information or error code when not authorized
   */
  @GetMapping("/{username}")
  public ResponseEntity<?> getProfile(@PathVariable String username) throws InterruptedException {
    User sessionUser = userService.getSessionUser();

    if (sessionUser != null && sessionUser.getUsername().equals(username)) {
      UserProfileDto profile = new UserProfileDto(sessionUser.getId(), sessionUser.getFirstName(),
          sessionUser.getLastName(),
          sessionUser.getEmail(), sessionUser.getAddress());
      return new ResponseEntity<>(profile, HttpStatus.OK);
    } else if (sessionUser == null) {
      return new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
    }
  }

  /**
   * Update user profile information
   *
   * @param username Username for which the profile is updated
   * @return HTTP 200 OK or error code with error message
   */
  @PutMapping("/{username}")
  public ResponseEntity<String> updateProfile(@PathVariable String username, @RequestBody UserProfileDto profileData)
      throws InterruptedException {
    User sessionUser = userService.getSessionUser();
    ResponseEntity<String> response;
    if (sessionUser != null && sessionUser.getUsername().equals(username)) {
      if (profileData != null) {
        if (userService.updateProfile(sessionUser, profileData)) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else {
          response = new ResponseEntity<>("Could not update Profile data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        response = new ResponseEntity<>("Profile data not supplied", HttpStatus.BAD_REQUEST);
      }
    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Profile data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Profile data for other users not accessible!", HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Returns a list of UserProfileDto's
   *
   * @return A list of all users or an error code when not authorized
   */
  @GetMapping("")
  public ResponseEntity<Object> getAllUsers() throws InterruptedException {
    User sessionUser = userService.getSessionUser();

    if (sessionUser != null && sessionUser.isAdmin()) {
      List<User> allUsers = userService.getAllUsers();
      List<UserProfileDto> allUserProfileDtos = allUsers.stream()
          .map(user -> new UserProfileDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
              user.getAddress()))
          .collect(Collectors.toList());
      return new ResponseEntity<>(allUserProfileDtos, HttpStatus.OK);
    } else if (sessionUser == null) {
      return new ResponseEntity<>("User data accessible only to authenticated users", HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity<>("User data accessible only to admin users", HttpStatus.FORBIDDEN);
    }
  }
}
