package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ReasonsForRefusalCommand implements Command {

        private TelegramBotSender telegramBotSender;

        public ReasonsForRefusalCommand(TelegramBotSender telegramBotSender) {
            this.telegramBotSender = telegramBotSender;
        }

        @Override
        public void execute(Update update) {
            Map<String,String> map = new HashMap<>();

            String message = "причины, почему могут отказать и не дать забрать животное из приюта.";

            telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
        }
}