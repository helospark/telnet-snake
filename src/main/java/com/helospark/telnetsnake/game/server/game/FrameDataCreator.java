package com.helospark.telnetsnake.game.server.game;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.annotation.Component;

import com.helospark.telnetsnake.game.server.game.domain.SnakeGameSession;

@Component
public class FrameDataCreator {
    @Autowired
    private MapDrawer mapDrawer;
    @Value("${MAP_WIDTH}")
    private int width;
    @Value("${CONSOLE_HEIGHT}")
    private int consoleHeight;

    public String createFrameData(SnakeGameSession domain) {
        StringBuilder stringBuilder = new StringBuilder();
        addClearScreenToStringBuilder(stringBuilder);

        drawLogoToStringBuilder(stringBuilder);

        char[][] map = mapDrawer.createMap(domain);
        drawMap(stringBuilder, map);

        addPoints(stringBuilder, domain.points);
        addInstructions(stringBuilder);
        return stringBuilder.toString();
    }

    private void addPoints(StringBuilder stringBuilder, int points) {
        stringBuilder.append("Points: |" + points + " | ");
    }

    private void drawLogoToStringBuilder(StringBuilder stringBuilder) {
        stringBuilder.append("Telnet snake! Use w,a,s,d,q commands (may need to press enter to flush)\r\n");
    }

    private void addInstructions(StringBuilder stringBuilder) {
        stringBuilder.append("w,a,s,d,q: ");
    }

    private void drawMap(StringBuilder stringBuilder, char[][] map) {
        drawHorizontalWall(stringBuilder, '_');
        for (int i = 0; i < map.length; ++i) {
            stringBuilder.append("|");
            for (int j = 0; j < map[i].length; ++j) {
                stringBuilder.append(map[i][j]);
            }
            stringBuilder.append("|\r\n");
        }
        drawHorizontalWall(stringBuilder, '-');
    }

    private void drawHorizontalWall(StringBuilder stringBuilder, char character) {
        for (int i = 0; i < width + 1; ++i) {
            stringBuilder.append(character);
        }
        stringBuilder.append("\r\n");
    }

    private void addClearScreenToStringBuilder(StringBuilder stringBuilder) {
        for (int i = 0; i < consoleHeight; ++i) {
            stringBuilder.append("\r\n");
        }
    }
}
