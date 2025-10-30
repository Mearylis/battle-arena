package game.arena;

import game.core.GameManager;

public class HeroBattleArena {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.initializeGame();
        gameManager.startBattleSequence();

        System.out.println("\nСпасибо за игру! До встречи в Arena of Heroes! 🎮");
    }
}