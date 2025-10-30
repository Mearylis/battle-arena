package game.observers;

import game.core.events.GameEvent;
import game.enums.EventType;

public class ConsoleBattleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        switch (event.getEventType()) {
            case ATTACK:
                System.out.printf("⚔️  %s (урон: %d)%n",
                        event.getDescription(), event.getValue());
                break;
            case DAMAGE:
                System.out.printf("💥 %s получил %d урона (здоровье: %d)%n",
                        event.getTarget().getName(), event.getValue(),
                        event.getTarget().getHealth());
                break;
            case HEAL:
                System.out.printf("💚 %s восстановил %d здоровья%n",
                        event.getSource().getName(), event.getValue());
                break;
            case STRATEGY_CHANGE:
                System.out.printf("🔄 %s%n", event.getDescription());
                break;
            case ULTIMATE_USED:
                System.out.printf("🔥 %s%n", event.getDescription());
                break;
        }
    }
}