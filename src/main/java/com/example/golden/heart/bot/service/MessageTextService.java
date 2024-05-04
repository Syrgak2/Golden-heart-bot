package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.command.CommandName;
import com.example.golden.heart.bot.model.MessageText;
import com.example.golden.heart.bot.repository.MessageTextRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageTextService {

    @Autowired
    private MessageTextRepository messageTextRepository;

    /**
     * Сохраняет текст сообщения в базе данных.
     * У одной команды может быть только один текст. Если
     * в базе данных существует текст команды, который был передан методу save, он заменяет существующий текст
     * в базе данных на переданный текст.
     * @param messageText текст сообщения
     * @return сохроненое сообщение
     */
    public MessageText save(MessageText messageText) {
        MessageText foundedMessageText = findByCommandName(messageText.getCommandName());
        if (foundedMessageText == null) {
            return messageTextRepository.save(messageText);
        }

        foundedMessageText.setText(messageText.getText());
        return messageTextRepository.save(foundedMessageText);
    }

    /**
     * Редактирует текст для сообщения
     * В базе может храниться только одно сообщения для одной команды
     * Если команда переданного текста не совпадает с командой текста из базы будет выброшена исключение
     * @param id messageText's id
     * @param messageText текст для хронение.
     * @return сохранную messageText
     */
    public MessageText edite(Long id, String messageText) {
        return messageTextRepository.findById(id)
                .map(foundMessageText -> {
                    foundMessageText.setText(messageText);
                    return messageTextRepository.save(foundMessageText);
                }).orElse(null);
    }

    public MessageText edite(CommandName commandName, String messageText) {
        return messageTextRepository.findByCommandName(commandName)
                .map(foundMessageText -> {
                    foundMessageText.setText(messageText);
                    return messageTextRepository.save(foundMessageText);
                }).orElse(null);
    }

    public Boolean remove(Long id) {
        if (findById(id) == null) {
            return false;
        }
        messageTextRepository.deleteById(id);
        return true;
    }

    public MessageText findById(Long id) {
        return messageTextRepository.findById(id).orElse(null);
    }

    public MessageText findByCommandName(CommandName commandName) {
        return messageTextRepository.findByCommandName(commandName).orElse(null);
    }

    @Transactional
    public Boolean removeByCommandName(CommandName commandName) {
        if (findByCommandName(commandName) == null) {
            return false;
        }
        messageTextRepository.deleteByCommandName(commandName);
        return true;
    }
}
