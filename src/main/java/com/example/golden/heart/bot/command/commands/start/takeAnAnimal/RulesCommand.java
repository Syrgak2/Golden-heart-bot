package com.example.golden.heart.bot.command.commands.start.takeAnAnimal;

import com.example.golden.heart.bot.command.Command;
import com.example.golden.heart.bot.service.TelegramBotSender;
import com.pengrad.telegrambot.model.Update;

import java.util.HashMap;
import java.util.Map;

import static com.example.golden.heart.bot.command.commands.CommandUtils.getChatId;

public class RulesCommand implements Command {
    private TelegramBotSender telegramBotSender;

    public RulesCommand(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

    @Override
    public void execute(Update update) {
        Map<String,String> map = new HashMap<>();
        map.put("Назад", "/takeAnAnimal");

        String message = "Правила знакомства с животными перед усыновлением.\n" +
                "\n" +
                "Усыновление животного - это важное решение, которое требует тщательного обдумывания и подготовки.\n" +
                "\n" +
                "1. Изучи тип животного, который подходит твоему образу жизни и жизненной ситуации: Прежде чем взять животное, важно изучить тип животного, который лучше всего подходит для вашего образа жизни и жизненной ситуации. Учитывай такие факторы, как размер животного, уровень энергии, потребность в физических нагрузках, а также любые поведенческие особенности или особенности породы.\n" +
                "\n" +
                "2. Рассчитай свой бюджет: Усыновление животного влечет за собой финансовые обязательства, включая расходы на питание, ветеринарное обслуживание, развлечения, воспитание (курсы дрессировки и кинолог), груминг (уход за животным). Перед тем как взять животное, подумай о своем бюджете и убедись, что можешь позволить себе расходы, связанные с уходом за питомцем.\n" +
                "\n" +
                "3. Подготовь свой дом: Прежде чем взять животное домой, подготовь свой дом, создав для животного безопасную и комфортную обстановку. Это может включать покупку ящика, кровати и игрушек, а также убедись, что все опасные предметы или растения находятся в недоступном месте. Помни, что животное из приюта из-за новых обстоятельств и обстановки вести себя странно и может пакостить. Подготовься к этому тоже.\n" +
                "\n" +
                "4. Найди хороший приют: При усыновлении животного важно найти место, которое уделяет первостепенное внимание благополучию животных, находящихся на их попечении. Изучи местные организации и прочитай отзывы предыдущих усыновителей, чтобы найти надежный источник для вашего нового питомца. (В сюжетах в сообществе можешь почитать подробнее об этом в прошлых статьях и сюжетах)\n" +
                "\n" +
                "5. Запланируй встречу и знакомство: После того как ты нашёл потенциальное животное, запланируй встречу с ним, чтобы убедиться, что оно подходит тебе. Это также даст вам возможность задать все интересующие вас вопросы об истории животного, его темпераменте и здоровье.\n" +
                "\n" +
                "6. Подготовься к периоду адаптации: Появление нового животного в доме может стать испытанием как для тебя, так и для животного. Прояви терпение и обеспечь постоянный распорядок дня для нового питомца.\n" +
                "\n" +
                "7. Запланируй ветеринарный осмотр: Вскоре после усыновления запланируй ветеринарный осмотр, чтобы убедиться, что питомец здоров и имеет все необходимые прививки. Это также дает возможность установить отношения с надежным ветеринаром, который сможет обеспечить постоянный уход за питомцем.\n";

        telegramBotSender.sendMessage(message, getChatId(update), telegramBotSender.setButtons(map));
    }
}