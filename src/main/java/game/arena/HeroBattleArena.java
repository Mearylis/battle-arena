package game.arena;

import game.core.GameManager;

public class HeroBattleArena {
    public static void main(String[] args) {
        try {
            GameManager gameManager = new GameManager();
            gameManager.initializeGame();
            gameManager.startBattleSequence();

            System.out.println("\nСпасибо за игру! До новых встреч в Арене Героев! 🎮");
        } catch (Exception e) {
            System.out.println("Произошла ошибка во время игры: " + e.getMessage());
            e.printStackTrace();
        }
    }
}