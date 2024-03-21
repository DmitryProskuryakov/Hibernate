package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Dmitry", "Ivanov", (byte) 25);
        System.out.println("User с именем Dmitry добавлен в базу данных");

        userService.saveUser("Aleksey", "Sidorov", (byte) 30);
        System.out.println("User с именем Aleksey добавлен в базу данных");

        userService.saveUser("Oleg", "Petrov", (byte) 22);
        System.out.println("User с именем Oleg добавлен в базу данных");

        userService.getAllUsers().forEach(el -> System.out.println(el.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}