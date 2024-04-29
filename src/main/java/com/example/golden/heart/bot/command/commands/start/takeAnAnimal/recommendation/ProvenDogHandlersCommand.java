package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class ProvenDogHandlersCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public ProvenDogHandlersCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/recommendation");

        /**
         * Реализация разделения по питомникам
         */

        String message = "Существуют рекомендации, следуя которым, вам будет проще сориентироваться и выбрать кинолога для своей собаки.\n" +
                "\n" +
                "1. Важно, чтобы ваши взгляды на воспитание и обучение собак совпадали. Существует множество методов дрессировки, однако лучше всего выбирать кинолога, который использует не удавки, электроошейники, битье и рывки, а лакомство, похвалу, игры и игрушки. Такой подход основан на принципе, согласно которому подкрепляемое поведение проявляется все чаще, а то, что не подкрепляется, угасает. При этом методы, основанные на насилии, делают собаку пассивной, запуганной и формируют у нее отвращение к занятиям и страх перед владельцем – нужен ли вам такой эффект?\n" +
                "\n" +
                "2. Будьте осторожны. Сейчас многие тренеры пишут о себе как о специалистах, использующих «только гуманные методы», но при этом на практике не гнушаются советовать владельцам, например, лишать собаку воды и пищи, запирать в клетке на целый день «в воспитательных целях» или использовать другие способы физического и психологического насилия. А если кинолог рассказывает о том, что собака пытается «доминировать», это однозначно повод вычеркнуть его из списка – теория доминирования давно и безнадежно устарела и еще в конце прошлого века признана не имеющей никакого отношения к реальности.\n" +
                "\n" +
                "3. Поинтересуйтесь образованием специалиста. Хороший кинолог не просто «любит собак» и «всю жизнь с ними общается». Он еще разбирается в психологии собак, языке тела, может предложить несколько способов решения возникающих проблем и знает, как замотивировать собаку, чтобы не пришлось ее принуждать. И еще хороший кинолог никогда не перестает учиться.\n" +
                "\n" +
                "4. Посмотрите, что выкладывает и пишет кинолог в Интернете, в том числе в социальных сетях.\n" +
                "\n" +
                "5. Если кинолог рассказывает о том, что некоторые породы собак «не поддаются дрессировке», лучше поискать другого специалиста.\n" +
                "\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
