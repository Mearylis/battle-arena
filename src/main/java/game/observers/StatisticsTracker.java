package game.observers;

import game.core.events.GameEvent;
import game.enums.EventType;
import java.util.HashMap;
import java.util.Map;

public class StatisticsTracker implements GameObserver {
    private Map<String, Integer> damageDealt = new HashMap<>();
    private Map<String, Integer> kills = new HashMap<>();
    private int totalBattles = 0;

    @Override
    public void onEvent(GameEvent event) {
        String heroName = event.getSource().getName();

        switch (event.getEventType()) {
            case DAMAGE:
                damageDealt.merge(heroName, event.getValue(), Integer::sum);
                break;
            case DEATH:
                kills.merge(heroName, 1, Integer::sum);
                break;
            case BATTLE_END:
                totalBattles++;
                break;
        }
    }

    public void printStatistics() {
        System.out.println("\n📊 СТАТИСТИКА БИТВ:");
        System.out.println("Всего битв: " + totalBattles);
        System.out.println("Нанесено урона:");
        damageDealt.forEach((name, damage) ->
                System.out.printf("  %s: %d%n", name, damage));
        System.out.println("Побед:");
        kills.forEach((name, killCount) ->
                System.out.printf("  %s: %d%n", name, killCount));
    }
}