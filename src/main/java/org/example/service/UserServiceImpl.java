package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.converter.UserConverter;
import org.example.dto.CredentialsDto;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final HashPassService hashPassService;
  private final UserConverter userConverter;
  private final PasswordEncoder passwordEncoder;


  public User getUserByUserNameAndPassword(String name, String password) {
    User user = userRepository.findByUsername(name);
    if (hashPassService.verify(password, user.getPassword())) {
      return user;
    } else {
      throw new RuntimeException("enter incorrect password or login");
    }
  }

  public User getUserByUserName(String name) {
    User user = userRepository.findByUsername(name);
    if (user == null) {
      throw new UserNotFoundException("User " + name + " not found");
    }
    return user;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public User createUser(CredentialsDto credentialsDto) {
    User user = userRepository.findByUsername(credentialsDto.getUsername());
    if (user != null) {
      throw new RuntimeException("User already exists");
    } else {
      user = userConverter.toUser(credentialsDto);
      String hash = passwordEncoder.encode(credentialsDto.getPassword());
      user.setPassword(hash);
      userRepository.save(user);
    }
    return user;
  }

  @Override
  public User updateUser(Long id, User user) {
    User existingUser = userRepository.findById(id).orElse(null);
    if (existingUser != null) {
      existingUser.setUsername(user.getUsername());
      existingUser.setPassword(user.getPassword());
      existingUser.setEmail(user.getEmail());
      existingUser.setFollowing(user.getFollowing());
      return userRepository.save(existingUser);
    }
    return null;
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public Optional<User> loginUser(String username, String password) {
    Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
    return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword()));
  }
}
