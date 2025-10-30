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
                if (event.getDescription().contains("Отравление") ||
                        event.getDescription().contains("яд")) {
                    System.out.printf("☠️  %s%n", event.getDescription());
                } else {
                    System.out.printf("💥 %s получает %d урона (HP: %d)%n",
                            event.getTarget().getName(), event.getValue(),
                            event.getTarget().getHealth());
                }
                break;
            case HEAL:
                System.out.printf("💚 %s восстанавливает %d здоровья%n",
                        event.getSource().getName(), event.getValue());
                break;
            case STRATEGY_CHANGE:
                System.out.printf("🔄 %s%n", event.getDescription());
                break;
            case ULTIMATE_USED:
                System.out.printf("✨ %s%n", event.getDescription());
                break;
            case MANA_USED:
                System.out.printf("🔷 %s тратит %d маны%n",
                        event.getSource().getName(), event.getValue());
                break;
            case BUFF_APPLIED:
                System.out.printf("🌟 %s%n", event.getDescription());
                break;
            case POISON_APPLIED:
                System.out.printf("☠️  %s%n", event.getDescription());
                break;
            case BUFF_EXPIRED:
                System.out.printf("💨 %s%n", event.getDescription());
                break;
        }
    }
}