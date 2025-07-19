package io.github.alinacozy.bishop_prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.github.alinacozy.model.Command;
import io.github.alinacozy.service.CommandService;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping
    public ResponseEntity<String> addCommand(@RequestBody Command command) {
        commandService.proccessCommand(command);
        return ResponseEntity.ok("Command accepted");
    }
}
