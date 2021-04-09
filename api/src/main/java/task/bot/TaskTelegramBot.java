package task.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import task.service.town.TownAttractionService;
import task.utils.ToolsForWord;

@Component
public class TaskTelegramBot extends TelegramLongPollingBot {

    final String start = "Здравствуйте, о достопримечательностях какого города вы хотели бы узнать?:)";
    @Autowired
    TownAttractionService townAttractionServer;

    @Value("${bot.name}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText().trim());
        if("/start".equals(update.getMessage().getText().trim())) {
            String chatId = update.getMessage().getChatId().toString();
            System.out.println(update.getMessage().getText().trim());

            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText(start);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            ToolsForWord toolsForWord = new ToolsForWord();
            message=toolsForWord.getWordWithACapitalLetter(message);
            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText(townAttractionServer.getTownAttraction(message));

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
