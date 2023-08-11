package com.media_collection.frontend.data.service;

import com.media_collection.frontend.data.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BackendService {
    List<User> users = new ArrayList<>(List.of(new User(1,"Test user")));
    public List<User> findAllUsers(String value) {
        return users;
    }
    public void saveUser(User user)
    {
        users.add(user);
    }
    public void deleteUser(User user)
    {
        users.remove(user);
    }
}
