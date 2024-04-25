package com.example.golden.heart.bot.command.commands.start.startInfo;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class StartInfoCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public StartInfoCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("1. расписание работы приюта и адрес, схема проезда", "/address");
        map.put("2. контактные данные охраны для оформления пропуска на машину", "/security");
        map.put("3. общие рекомендации о технике безопасности на территории приюта.", "/safetyPrecautions");
        map.put("4. принять и записать контактные данные для связи.", "/contactDetails");

        map.put("Позвать волонтёра. Помощь", "/volunteer");
        map.put("Назад", "/catAndDog");

        String message =
                " Что хочешь узнать?";


        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }

}
