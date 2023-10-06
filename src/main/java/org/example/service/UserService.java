package org.example.service;


import org.example.dto.CredentialsDto;
import org.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<User> getAllUsers();

  public User getUserByUserNameAndPassword(String name, String password);

  User getUserById(Long id);

  User createUser(CredentialsDto credentialsDto);

  public User getUserByUserName(String name);

  User updateUser(Long id, User user);

  void deleteUser(Long id);

  Optional<User> loginUser(String username, String password);
}
