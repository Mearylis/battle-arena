package game.observers;

public class BattleLogger implements GameObserver {
    @Override
    public void onEvent(String message) {
        if (message.contains("получает") && message.contains("урона")) {
            System.out.println("💥 " + message);
        } else if (message.contains("лечится") || message.contains("восстанавливает")) {
            System.out.println("💚 " + message);
        } else if (message.contains("тратит") && message.contains("маны")) {
            System.out.println("🔷 " + message);
        } else if (message.contains("бьет") || message.contains("стреляет") || message.contains("бросает")) {
            System.out.println("⚔️  " + message);
        } else if (message.contains("погиб")) {
            System.out.println("💀 " + message);
        } else if (message.contains("МОЩНЫЙ") || message.contains("МОЛНИЮ") ||
                message.contains("СНАЙПЕРСКИЙ") || message.contains("СМЕРТЕЛЬНЫЙ")) {
            System.out.println("✨ " + message);
        } else if (message.contains("Огненное") || message.contains("каменную кожу")) {
            System.out.println("🌟 " + message);
        } else if (message.contains("отравлен") || message.contains("Яд наносит")) {
            System.out.println("☠️  " + message);
        } else if (message.contains("рассеивается")) {
            System.out.println("💨 " + message);
        } else {
            System.out.println("🔄 " + message);
        }
    }
}