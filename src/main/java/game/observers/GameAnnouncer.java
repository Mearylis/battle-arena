package game.observers;

import game.core.events.GameEvent;
import game.enums.EventType;

public class GameAnnouncer implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        switch (event.getEventType()) {
            case DEATH:
                System.out.printf("\n💀 %s повержен!%n", event.getTarget().getName());
                break;
            case ULTIMATE_USED:
                System.out.printf("\n🌟 МОЩНАЯ АТАКА! %s%n", event.getDescription());
                break;
            case BATTLE_START:
                System.out.printf("\n🎯 БИТВА: %s vs %s%n",
                        event.getSource().getName(), event.getTarget().getName());
                System.out.println("=" .repeat(40));
                break;
            case BATTLE_END:
                System.out.println("=" .repeat(40));
                System.out.printf("🏆 ПОБЕДИТЕЛЬ: %s%n%n", event.getSource().getName());
                break;
        }
    }
}