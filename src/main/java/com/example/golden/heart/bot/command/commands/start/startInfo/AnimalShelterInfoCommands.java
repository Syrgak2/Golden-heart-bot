package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.service.MessageTextService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getData;

/**
  * В этом классе содержится обработка всех команд, которые дают информацию о приюте.
 */
public class AnimalShelterInfoCommands implements Command {

    private String message = " ";

    Map<String, String> buttons;

    private TelegramBotSender telegramBotSender;

    private MessageTextService messageTextService;

    public AnimalShelterInfoCommands(TelegramBotSender telegramBotSender, MessageTextService messageTextService) {
        this.telegramBotSender = telegramBotSender;
        this.messageTextService = messageTextService;
    }

    @Override
    public void execute(Update update) {
        if (getData(update).equals(SAFETY_PRECAUTIONS.getCommand())) {
            collectMessageFoSafetyPrecautions();
        }
        if (getData(update).equals(SECURITY.getCommand())) {
            collectMessageFoSecurity();
        }

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(buttons));
    }

    private void collectMessageFoSafetyPrecautions() {
        MessageText messageText =  messageTextService.findByCommandName(SAFETY_PRECAUTIONS);
        message = messageText.getText();

        buttons = new LinkedHashMap<>();
        buttons.put("Назад", START_INFO.getCommand());

    }

    private void collectMessageFoSecurity() {
        MessageText messageText = messageTextService.findByCommandName(SECURITY);

        message = messageText.getText();
        buttons = new LinkedHashMap<>();
        buttons.put("Назад", START_INFO.getCommand());
    }
}
