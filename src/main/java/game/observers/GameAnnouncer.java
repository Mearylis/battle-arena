package game.observers;

public class GameAnnouncer implements GameObserver {
    @Override
    public void onEvent(String message) {
        if (message.contains("МОЩНЫЙ УДАР") || message.contains("МОЛНИЮ") ||
                message.contains("СНАЙПЕРСКИЙ") || message.contains("СМЕРТЕЛЬНЫЙ")) {
            System.out.println("\n🎯 ВАЖНОЕ СОБЫТИЕ: " + message + "\n");
        } else if (message.contains("погиб")) {
            System.out.println("\n⚰️  ГЕРОЙ ПАЛ: " + message + "\n");
        }
    }
}