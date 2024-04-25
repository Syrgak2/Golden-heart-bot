package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;
/**
 * Для собак
 */

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class DogHandlerAdviceCommand implements Command {

    private TelegramBotSender telegramBotSender;

    public DogHandlerAdviceCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/takeAnAnimal");

        String message = "Советы кинолога по первичному общению с собакой.\n" +
                "\n" +
                " На дрессировочной площадке можно увидеть собак двух типов: \n" +
                "а)\t большинство — которым интересно все вокруг, кроме владельца. Собака готова бегать, нюхать метки, общаться с другими собаками и т.п., с владельцем связь только через поводок,\n" +
                "б)\t меньшинство — которым интересно с владельцем, и которые ждут от него игры, общения, движухи и т.п. Вторая группа и выглядит эстетичней и гораздо больших успехов добивается в дрессировке, вне зависимости от породы собаки.\n" +
                "\n" +
                "Заводя собаку, большинство владельцев ждет от нее активной коммуникации и желания сотрудничать. Кошек не заводят для этой цели. Но многие не понимают, что коммуникация — это двусторонний процесс, и от человека также требуются усилия для ее формирования.\n" +
                "\n" +
                "Итак, что же такое контакт?\n" +
                "\n" +
                "Контакт — это готовность к общению, и само общение: ожидание и распознавание исходящих друг от друга сигналов.\n" +
                "\n" +
                "Для того чтобы делать что-то совместно, необходимо уметь разговаривать на понятном языке и быть последовательным в своих поступках. Поведение партнера должно быть понятным и прогнозируемым. Вы сами вряд ли захотели бы иметь дело с непредсказуемым иностранцем, болтающим на непонятном языке и не желающего вас слышать.\n" +
                "\n" +
                "Контакт — это и состояние, и процесс. Если с собакой есть контакт, то у вас есть канал, чтобы донести до нее свои желания, пока она находится в состоянии заинтересованного слушания (зрительного, слухового, и др.). Контакт как процесс — это, по сути, разговор, в котором заинтересованы оба собеседника.\n" +
                "\n" +
                "Контакт как состояние\n" +
                "\n" +
                "Контакт создается не за один день, он постепенно формируется в процессе ежедневного общения, состоящего из\n" +
                "\n" +
                "— заботы и ухода;\n" +
                "\n" +
                "— игр, прогулок и другого эмоционально окрашенного взаимодействия;\n" +
                "\n" +
                "— совместной работы.\n" +
                "\n" +
                "С взрослой и самодостаточной собакой это может стать непростой задачей (которая нередко возникает и остается нерешенной при смене проводника).\n" +
                "\n" +
                "Главное условие образования хорошего контакта — получение собакой положительных при общении и работе с проводником. Это не только (а для сильных собак и не столько) приобретение некоего обычного комфорта, но совместное преодоление трудностей и достижение через это общего успеха («общей победы»).\n" +
                "\n" +
                "Контакт как процесс — это непосредственно сама коммуникация, общение доступными способами\n" +
                "\n" +
                "В отличие от человека, у собаки несколько иные приоритеты в каналах коммуникации, используемых для общения с сородичами.\n" +
                "\n" +
                "Человеческие приоритеты (в порядке убывания):\n" +
                " \t 1) вербальное общение (речь) — основной способ общения\n" +
                " \t 2) эмоции\n" +
                " \t 4) позы, жесты, мимика\n" +
                " \t 5) прочие способы.\n" +
                "\n" +
                "Собачьи приоритеты:\n" +
                " \t 1) общение через запахи\n" +
                " \t 2) позы, мимика (язык тела)\n" +
                " \t 3) эмоции\n" +
                " \t 4) прочее.\n" +
                "\n" +
                "Для эффективного общения нам нужно, по-возможности, отдавать приоритет тем способам, которые более понятны и привычны для собаки. Поскольку использование запахового канала коммуникации представляется затруднительным, необходимо основной акцент делать на язык тела и эмоции.\n" +
                "\n" +
                "Эффективные способы коммуникации\n" +
                "Эффективные способы коммуникации\n" +
                "Когда мы научимся понятно выражать свои эмоции и желания этими способами, нам будет гораздо проще донести до собаки необходимую информацию.\n" +
                "\n" +
                "Есть стремление к контакту — есть сам контакт — и есть основа для эффективной дрессировки.\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButns(map));
    }
}
