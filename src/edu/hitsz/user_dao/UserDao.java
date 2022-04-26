package edu.hitsz.user_dao;

import java.io.IOException;
import java.util.List;

public interface UserDao {
    List<User>getAllUsers();
    void doAdd(User user) throws IOException;
    void doDelete(int rank) throws IOException;
}
