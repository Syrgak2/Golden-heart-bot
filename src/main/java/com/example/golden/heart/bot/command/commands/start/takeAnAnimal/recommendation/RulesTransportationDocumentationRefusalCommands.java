package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.service.MessageTextService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getData;

public class RulesTransportationDocumentationRefusalCommands implements Command {

    private String message = " ";
    private Map<String, String> buttons;
    private TelegramBotSender telegramBotSender;
    private MessageTextService messageTextService;

    public RulesTransportationDocumentationRefusalCommands(TelegramBotSender telegramBotSender, MessageTextService messageTextService) {
        this.telegramBotSender = telegramBotSender;
        this.messageTextService = messageTextService;
    }

    @Override
    public void execute(Update update) {
        if (getData(update).equals(DOCUMENTATION.getCommand())) {
            collectMessage(DOCUMENTATION);
        }

        if (getData(update).equals(TRANSPORTATION.getCommand())) {
            collectMessage(TRANSPORTATION);
        }

        if (getData(update).equals(RULES.getCommand())) {
            collectMessage(RULES);
        }
        if (getData(update).equals(REASONS_FOR_REFUSAL.getCommand())) {
            collectMessage(REASONS_FOR_REFUSAL);
        }

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(buttons));

    }

    private void collectMessage(CommandName commandName) {
        MessageText messageText = messageTextService.findByCommandName(commandName);
        message = messageText.getText();

        buttons = new LinkedHashMap<>();
        buttons.put("назад", TAKE_AN_ANIMAL.getCommand());
    }
}
