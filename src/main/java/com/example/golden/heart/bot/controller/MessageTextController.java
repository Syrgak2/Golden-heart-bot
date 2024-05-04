package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.service.MessageTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageText")
public class MessageTextController {

    @Autowired
    private MessageTextService messageTextService;

    @PostMapping
    public ResponseEntity<MessageText> createMessageText(@RequestBody MessageText messageText) {
        return ResponseEntity.ok(messageTextService.save(messageText));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageText> editeMessageText(@PathVariable Long id,
                                                 @RequestBody MessageText messageText) {
        MessageText foundMessageText = null;
        try {
            foundMessageText = messageTextService.edite(id, messageText);
        } catch (IllegalArgumentException e) {
            ResponseEntity.badRequest().build();
        }
        if (foundMessageText == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundMessageText);
    }

    @GetMapping
    public ResponseEntity<MessageText> getMessageText(@RequestParam(required = false) Long id,
                                                      @RequestParam(required = false) CommandName commandName) {
        MessageText messageText = null;
        if (id != null) {
            messageText = messageTextService.findById(id);
        }
        if (commandName != null) {
            messageText = messageTextService.findByCommandName(commandName);
        }

        if (id == null && commandName == null) {
            return ResponseEntity.badRequest().build();
        }

        if (messageText == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messageText);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteMessageText(@RequestParam(required = false) Long id,
                                                     @RequestParam(required = false) CommandName commandName) {
        Boolean bollen = null;

        if (id != null) {
            bollen = messageTextService.remove(id);
        }

        if (commandName != null) {
            bollen = messageTextService.removeByCommandName(commandName);
        }

        if (commandName == null && id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bollen) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(true);
    }
}
