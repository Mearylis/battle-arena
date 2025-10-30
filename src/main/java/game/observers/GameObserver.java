package game.observers;

import game.core.events.GameEvent;

public interface GameObserver {
    void onEvent(GameEvent event);
}