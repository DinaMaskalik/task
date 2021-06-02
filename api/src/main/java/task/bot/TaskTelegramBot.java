package task.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import task.service.product.ProductService;
import task.utils.ToolsForWord;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskTelegramBot extends TelegramLongPollingBot {

    private int count;
    private String category;
    private String manufacture;
    private String model;
    private Double price;

    final String start = "Здравствуйте, рады видеть Вас здесь!\nЕсли есть вопросы вызовите /help";

    @Autowired
    ProductService productServer;

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

        if ("/start".equals(update.getMessage().getText().trim())) {
            sendStartMsg(update.getMessage(), start);

            return;
        }
        if ("/help".equals(update.getMessage().getText().trim())) {
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                sendMessage.setText("Рады Вам помочь:\n" +
                        "/help - вызов списка с функционалом\n" +
                        "/find - начать поиск товара\n" +
                        "/admin - связаться с администратором");
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }

        if ("/admin".equals(update.getMessage().getText().trim())) {
            sendAdminMsg(update.getMessage(), "Чтобы перейти а тг админа нажмите на кнопку!");

            return;
        }
        if ("/find".equals(update.getMessage().getText().trim())) {
            sendStartFindMsg(update.getMessage(), "Очень приятно что вы всё же доверились нам!\n" +
                    "Для начала введите категорию!");
            count = 0;
            return;
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            if (count == 0) {
                ToolsForWord toolsForWord = new ToolsForWord();
                category = toolsForWord.getWordWithACapitalLetter(message);
                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("Отлично!\n" +
                        "Теперь введите производителя!");
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                count++;
                return;
            }

            if (count == 1) {
                ToolsForWord toolsForWord = new ToolsForWord();
                manufacture = toolsForWord.getWordWithACapitalLetter(message);
                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("Отлично, осталось совсем немного!\n" +
                        "Теперь введите модель!");
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                count++;
                return;
            }

            if (count == 2) {
                model = message;
                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("Отлично, осталось чуть-чуть!\n" +
                        "Теперь введите желаемую цену!");
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                count++;
                return;
            }

            if (count == 3) {
                try {
                    price = Double.valueOf(message);
                    count = 0;
                } catch (NumberFormatException ex) {
                    SendMessage sm = new SendMessage();
                    sm.setChatId(chatId);
                    sm.setText("Ошибка ввода цены!\n" +
                            "Пожалуйста, повторите ввод");
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                count = 0;

                sendFindMsg(
                        update.getMessage(),
                        "Поздравляем! Вы завершили заполнение анкеты!\n" +
                        "Мы можем порекомендовать Вам данный продукт"
                );

                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("Спасибо, что выбрали нас!\n" +
                        "Будем рады помочь ещё!\n" +
                        "Для поиска с начала перейдите по /find");
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                return;
            }

        }
    }

    public void sendStartMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendStartFindMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendAdminMsg(Message message, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Admin");
        inlineKeyboardButton1.setUrl("https://t.me/Bleachnarutou");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendFindMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Рекомендация");

        final String productUrl = productServer.getProductUrl(category, manufacture, model, price);

        if(!productUrl.equals("К сожелению, мы не нашли подходящий товар!\nПопробуйте ещё раз!")) {
            inlineKeyboardButton1.setUrl(productUrl);

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton1);
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);
            inlineKeyboardMarkup.setKeyboard(rowList);

            sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);

            sendMessage.setChatId(message.getChatId().toString());

            sendMessage.setText(text);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else{
            sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);

            sendMessage.setChatId(message.getChatId().toString());

            sendMessage.setText("К сожелению, мы не нашли подходящий товар!\nПопробуйте ещё раз!");
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
