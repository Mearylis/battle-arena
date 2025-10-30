package game.strategies.attack;

import game.core.Hero;
import game.core.events.GameEvent;
import game.enums.EventType;

public interface AttackStrategy {
    void execute(Hero attacker, Hero target);
    String getDescription();
    int getManaCost();

    default void notifyAttack(Hero attacker, Hero target, String description, int damage) {
        attacker.notifyObservers(new GameEvent(
                EventType.ATTACK, attacker, target, description, damage
        ));
    }
}