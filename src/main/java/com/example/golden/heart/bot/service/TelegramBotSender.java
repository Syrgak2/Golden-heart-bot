package com.example.golden.heart.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class TelegramBotSender {
    @Autowired
    private TelegramBot telegramBot;

    /**
     *  Отправляет сообщения к указанному chatId
     */
    public void sendMessage(String messageText, Long chatId, InlineKeyboardMarkup markupInline) {
        SendResponse response = telegramBot.execute(new SendMessage(chatId, messageText).replyMarkup(markupInline));
    }

    public InlineKeyboardMarkup setButtons(Map<String, String> buttons){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Map.Entry<String, String> entry : buttons.entrySet()){
            markup.addRow(new InlineKeyboardButton(entry.getKey()).callbackData(entry.getValue()));
        }
        return markup;
    }

    public void sendPhoto(Long chatID, File file) {
        SendResponse response = telegramBot.execute(new SendPhoto(chatID, file));
    }
}
