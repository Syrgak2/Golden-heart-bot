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

    @PutMapping()
    public ResponseEntity<MessageText> editeMessageText(@RequestParam(required = false) Long id,
                                                        @RequestParam(required = false) CommandName commandName,
                                                 @RequestBody String messageText) {

        MessageText foundMessageText = null;
        if (id != null && commandName == null) {
            foundMessageText = messageTextService.edite(id, messageText);
        }
        if (commandName != null && id == null) {
            foundMessageText = messageTextService.edite(commandName, messageText);
        }

        if (commandName == null & id == null || commandName != null && id != null) {
            return ResponseEntity.badRequest().build();
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
        if (id != null && commandName == null) {
            messageText = messageTextService.findById(id);
        }
        if (commandName != null && id == null) {
            messageText = messageTextService.findByCommandName(commandName);
        }

        if (id == null && commandName == null || id != null && commandName != null) {
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
        Boolean bollen = false;

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
