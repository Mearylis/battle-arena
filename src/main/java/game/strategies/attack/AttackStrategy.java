package game.strategies.attack;

import game.core.Hero;

public interface AttackStrategy {
    void execute(Hero attacker, Hero target);
    String getDescription();
    int getManaCost();
}