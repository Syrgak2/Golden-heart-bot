package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.command.commands.start.info.UnknownCommand;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.MessageTextService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.CommandName.*;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getData;

public class HomeImprovementCommands implements Command {

    private String message;

    private Map<String, String> buttons;

    private TelegramBotSender telegramBotSender;
    private MessageTextService messageTextService;
    private UserService userService;

    public HomeImprovementCommands(TelegramBotSender telegramBotSender, MessageTextService messageTextService,
                                   UserService userService) {
        this.userService = userService;
        this.telegramBotSender = telegramBotSender;
        this.messageTextService = messageTextService;
    }

    @Override
    public void execute(Update update) {
        if (getData(update).equals(HOME_IMPROVEMENT_ADULT.getCommand())) {
            collectMessage(HOME_IMPROVEMENT_ADULT);
        }
        if ((getData(update).equals(HOME_IMPROVEMENT_FOR_DISABLED.getCommand()))) {
            collectMessage(HOME_IMPROVEMENT_FOR_DISABLED);
        }

        if (getData(update).equals(HOME_IMPROVEMENT_YOUNG.getCommand())) {
            collectMessageForCatOrDog(update);
        }
        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(buttons));

    }

    /**
     * Собирает сообщение для страрых животных
     */
    private void collectMessage(CommandName commandName) {
        MessageText messageText = messageTextService.findByCommandName(commandName);
        message = messageText.getText();

        buttons = new LinkedHashMap<>();
        buttons.put("Назад", RECOMMENDATION.getCommand());
    }

    private void collectMessageForCatOrDog(Update update) {
        User user = userService.findByChatId(getChatId(update));
        if (user.getChosenPetType().equals(CAT.getCommand())) {
            collectMessage(CAT);
        } else if (user.getChosenPetType().equals(DOG.getCommand())) {
            collectMessage(DOG);
        } else {
            UnknownCommand unknownCommand = new UnknownCommand(telegramBotSender);
            unknownCommand.execute(update);
        }

    }

}
