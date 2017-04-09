package com.helospark.telnetsnake.server.game;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helospark.telnetsnake.server.game.domain.SnakeGameSession;

@Component
public class SnakeDataSender {
    @Autowired
    private FrameDataCreator frameDataCreator;
    @Autowired
    private FinalDataCreator finalDataCreator;

    public void sendDataToClient(SnakeGameSession domain, PrintWriter printWriter) {
        String frameData = frameDataCreator.createFrameData(domain);
        sendOutputToClient(printWriter, frameData);
    }

    public void sendFinalResult(String ip, int points, PrintWriter printWriter) {
        String finalResult = finalDataCreator.createFinalData(ip, points);
        sendOutputToClient(printWriter, finalResult);
    }

    private void sendOutputToClient(PrintWriter printWriter, String frameData) {
        printWriter.print(frameData);
        printWriter.flush();
    }

}
