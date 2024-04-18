package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class SafetyPrecautionsCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public SafetyPrecautionsCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();

        String message = "общие рекомендации о технике безопасности на территории приюта.";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}