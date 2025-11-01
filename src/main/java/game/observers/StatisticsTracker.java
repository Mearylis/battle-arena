package game.observers;

import java.util.HashMap;
import java.util.Map;

public class StatisticsTracker implements GameObserver {
    private Map<String, Integer> damage = new HashMap<>();
    private Map<String, Integer> kills = new HashMap<>();

    @Override
    public void onEvent(String message) {
        // Считаем урон
        if (message.contains("получает") && message.contains("урона")) {
            String heroName = extractHeroName(message);
            int damageValue = extractNumber(message);
            damage.put(heroName, damage.getOrDefault(heroName, 0) + damageValue);
        }

        // Считаем смерти
        if (message.contains("погиб")) {
            String heroName = extractHeroName(message);
            kills.put(heroName, kills.getOrDefault(heroName, 0) + 1);
        }
    }

    private String extractHeroName(String message) {
        // Простая логика извлечения имени
        if (message.contains("получает")) {
            return message.split(" ")[0];
        } else if (message.contains("погиб")) {
            return message.split(" ")[0];
        }
        return "Неизвестный";
    }

    private int extractNumber(String message) {
        // Простая логика извлечения числа
        String[] words = message.split(" ");
        for (String word : words) {
            try {
                return Integer.parseInt(word);
            } catch (NumberFormatException e) {
                // Пропускаем нечисловые слова
            }
        }
        return 0;
    }

    public void printStats() {
        System.out.println("\n📊 СТАТИСТИКА БИТВ:");
        System.out.println("Нанесено урона:");
        damage.forEach((name, dmg) -> System.out.println("  " + name + ": " + dmg));
        System.out.println("Победы:");
        kills.forEach((name, killCount) -> System.out.println("  " + name + ": " + killCount));
    }
}