package com.example.golden.heart.bot.repository;

import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.model.MessageText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageTextRepository extends JpaRepository<MessageText, Long> {

    Optional<MessageText> findByCommandName(CommandName commandName);

    void deleteByCommandName(CommandName commandName);
}
