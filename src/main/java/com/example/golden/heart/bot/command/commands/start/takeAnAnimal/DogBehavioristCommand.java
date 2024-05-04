package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.model.DogBehaviorist;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.service.DogBehavioristService;
import com.example.golden.heart.bot.service.MessageTextService;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import static com.example.golden.heart.bot.command.CommandName.DOG_BEHAVIORIST_ADVICE;
import static com.example.golden.heart.bot.command.CommandName.GET_DOG_BEHAVIORIST;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;
import static com.example.golden.heart.bot.command.commands.CommandUtils.getData;

public class DogBehavioristCommand implements Command {

    private String message;

    private TelegramBotSender telegramBotSender;
    private DogBehavioristService dogBehavioristService;
    private MessageTextService messageTextService;


    public DogBehavioristCommand(TelegramBotSender telegramBotSender, DogBehavioristService dogBehavioristService, MessageTextService messageTextService) {
        this.messageTextService = messageTextService;
        this.telegramBotSender = telegramBotSender;
        this.dogBehavioristService = dogBehavioristService;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("Назад", "/takeAnAnimal");

        if (getData(update).equals(DOG_BEHAVIORIST_ADVICE.getCommand())) {
            dogBehavioristAdviceMessage();
        } else if (getData(update).equals(GET_DOG_BEHAVIORIST.getCommand())) {
            geDogBehaviorist();
        }

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));

    }


    private void dogBehavioristAdviceMessage() {
        MessageText messageText = messageTextService.findByCommandName(DOG_BEHAVIORIST_ADVICE);
        message = messageText.getText();

    }

    private void geDogBehaviorist() {
        List<DogBehaviorist> dogBehaviorists = dogBehavioristService.findAll();

        if (dogBehaviorists.isEmpty()) {
            message = "У нас пока нет кинологов для рекомендации";
        }
        message = "Вот список рекомендованных кинологов\n" + collectDogBehaviorist(dogBehaviorists);
    }

    private String collectDogBehaviorist(List<DogBehaviorist> dogBehaviorists) {
        StringBuilder message = new StringBuilder(" ");
        for (DogBehaviorist element : dogBehaviorists) {
            message.append(element.getName());
            message.append(" Номер телефона: ");
            message.append(element.getPhone());
            message.append("\n");
        }
        return message.toString();
    }
}
