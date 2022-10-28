package dev.davidutz;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.util.Objects;

public class Radio {
    private Player player;
    private Thread thread;

    public void start() throws Exception {
        Objects.requireNonNull(player);

        thread = new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Main.showMenu();
    }

    public void stop() throws Exception {
        if (isRunning()) {
            thread.interrupt();
            thread = null;

            if (player != null) {
                player.close();
            }
        }
        Main.showMenu();
    }

    public void setStream(InputStream stream) {
        try {
            player = new Player(stream);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        return thread != null;
    }
}
