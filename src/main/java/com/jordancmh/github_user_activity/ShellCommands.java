package com.jordancmh.github_user_activity;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.jordancmh.github_user_activity.service.GithubActivityService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellCommands {

    GithubActivityService service;

    public ShellCommands(GithubActivityService service) {
        this.service = service;
    }

    @ShellMethod(key = "github-activity", value = "Returns the recent activity of a GitHub user.")
    public void getGithubActivity(@ShellOption(arity = Integer.MAX_VALUE, defaultValue = "") String[] username) {
        if (username.length != 1) {
            System.out.println("Usage: github-activity <username>");
        }
        service.returnRecentGithubActivity(username);
    }
}