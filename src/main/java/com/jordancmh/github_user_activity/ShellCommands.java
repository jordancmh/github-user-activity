package com.jordancmh.github_user_activity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellCommands {

    private Map<String, String> users = new HashMap<>();
    private boolean connected = false;

    @ShellMethod("Prints say: + arg.")
    public String say(@ShellOption(defaultValue = "Hello Spring") String arg) {
        return "say: " + arg;
    }

    @ShellMethod("Register an account")
    public String register(String username, String password) {
        if (users.containsKey(username)) {
            return "Username taken.";
        }
        users.put(username, password);
        return "Registration successful.";
    }

    @ShellMethod("Connect to the server.")
    public String connect(String username, String password) {

        if (!users.containsKey(username)) {
            return "Username does not exist.";
        }

        String storedPassword = users.get(username);
        if (!storedPassword.equals(password)) {
            return "Wrong password.";
        }

        connected = true;
        return "Connected successfully.";
    }

    @ShellMethod(key = "get-users", value = "Returns all registered users.")
    public String getUsers() {
        return users.toString();
    }
    
    @ShellMethod(key = "github-activity", value = "Returns the recent activity of a GitHub user.")
    public void getUserActivity(String username) {
        
    }

    @ShellMethodAvailability({"say", "get-users"})
    public Availability availabilityCheck() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }
}