package com.example.golden.heart.bot.model;

import com.example.golden.heart.bot.command.CommandName;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MessageText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Enumerated(EnumType.STRING)
    private CommandName commandName;

    public MessageText() {
    }

    public MessageText(Long id, String text, CommandName commandName) {
        this.id = id;
        this.text = text;
        this.commandName = commandName;
    }

}
