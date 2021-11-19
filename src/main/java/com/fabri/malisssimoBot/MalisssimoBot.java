package com.fabri.malisssimoBot;

import com.fabri.malisssimoBot.services.JokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MalisssimoBot extends TelegramLongPollingBot {

    Logger logger = LoggerFactory.getLogger(MalisssimoBot.class);
    final String START = "/start";
    final String WELCOME_MESSAGE = "Hola, bienvenido!\n[" + this.getBotUsername() +" ]\n";

    @Autowired
    private JokeService jokeService;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (!message.hasText()) {return;}
        final String txtReceived = message.getText();
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(update.getMessage().getChatId().toString());
        logger.info(replyMessage.getChatId() + ": " + txtReceived);
        this.answerLogic(txtReceived, replyMessage);
        sendTelegramMessage(replyMessage);
    }

    private void answerLogic(String txtReceived, SendMessage replyMessage) {
        String instructions = "De momento sólo se contar chistes...\n\n➡️ Escribe \"chiste\" para que te cuente uno... ";
        if (txtReceived.equals(START) || txtReceived.equalsIgnoreCase("hola")) {
            replyMessage.setText(WELCOME_MESSAGE + instructions);
        } else if (txtReceived.toLowerCase().matches(".*chiste+.*")) {
            replyMessage.setText(jokeService.retrieveRandomJoke());
        } else if (txtReceived.matches("(j*h*a*e*o*J*H*A*E*O*)+")) {
            replyMessage.setText(jokeService.retrieveLaugh());
        } else {
            replyMessage.setText("No te entendí...\n" + instructions);
        }
    }

    private void sendTelegramMessage(SendMessage replyMessage) {
        try {
            execute(replyMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
