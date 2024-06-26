package com.example.golden.heart.bot.command.commands.start.takeAnAnimal.recommendation;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class TransportationCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public TransportationCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/recommendation");

        String message = "Основные рекомендации перевозки питомца в машине\n" +
                "\n" +
                "Перевозка домашних животных по ПДД приравнивается к перевозке груза. Поэтому, чтобы не получить штраф стоит придерживаться правил транспортировки багажа. Но с человеческой точки зрения, этому «багажу» должно быть комфортно. Поэтому нужно найти компромисс между двумя этими условиями.\n" +
                "\n" +
                "Багажник\n" +
                "\n" +
                "Эта возможность доступна владельцам хетчбеков, универсалов или минивенов. Нахождение в багажнике дает определенную долю свободы животному, но лишает доступа кислорода. Как правило, в конце машины довольно душно. Кинологи рекомендуют ставить защитную сетку перед задним стеклом, чтобы животное не выдавило стекло лапами, если будет сильно волноваться.\n" +
                "\n" +
                "Заднее сидение\n" +
                "\n" +
                "Согласно ПДД, багаж должен быть закреплен. Поэтому если, Вы перевозите питомца на заднем сидении, стоит надеть на него шлейку с креплением для детских кресел Isofix, либо надеть шлейку с адаптером для ремня безопасности. Такая фиксация даст собаке достаточную подвижность, но не даст перелезть на передние сидения, вылезти в окно и минимизирует травмы животного и пассажиров в случае ДТП.\n" +
                "Если пристегивать не хотите, можно воспользоваться сеткой. Она ставится или натягивается перед спинками переднего сидения и не дает животному перебраться вперед. Однако, остается риск травмы при экстренном торможении или ДТП.\n" +
                "Переноска\n" +
                "\n" +
                "Универсальный способ для некрупных животных. Если она исключительно для автомобильного использования, можно взять большего размера, чтобы питомцу было комфортно, и он не ощущал замкнутого пространства. Ограничивает только полезная площадь багажника или заднего дивана. Но, где бы она ни располагалась, она должна быть надежно зафиксирована – пристегнута ремнем безопасности через соответствующие прорези или зафиксирована жгутом.\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}
