package com.example.golden.heart.bot.command.commands.start.info;

import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.example.golden.heart.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;


import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.model.Role.VOLUNTEER;

public class VolunteerCommand implements com.example.golden.heart.bot.command.Command {
    private String message;
    private TelegramBotSender telegramBotSender;
    private UserService userService;

    public VolunteerCommand(TelegramBotSender telegramBotSender, UserService userService) {
        this.userService = userService;
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/catAndDog");
        markup.addRow(new InlineKeyboardButton("Назад").callbackData("/catAndDog"));


        if (collectVolunteerUrl() != null) {
            markup.addRow(new InlineKeyboardButton("Перейти к чату").url(collectVolunteerUrl()));
            message = "Нажмите кнопку 'Перейти к чату' чтобы связатся ч волонтером";
        }

        telegramBotSender.sendMessage(message, getChatId(update), markup);
    }

    private String collectVolunteerUrl() {
        User user = userService.findVolunteerByRole(VOLUNTEER);
        if (user == null) {
            message = "Извините я не смог найти волонтера";
            return null;
        }
        return "http://t.me/" + user.getUserName();
    }
}
