package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("User1", "1", (byte) 10);
        userService.saveUser("User2", "2", (byte) 20);
        userService.saveUser("User3", "3", (byte) 30);
        userService.saveUser("User4", "4", (byte) 40);

//        userService.dropUsersTable();
        userService.getAllUsers();
        userService.removeUserById(2);
//        userService.saveUser("User2", "2", (byte) 20);
        System.out.println("-----");
        userService.getAllUsers();
//        userService.getAllUsers();
    }
}
