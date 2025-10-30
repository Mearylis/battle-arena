package game.decorators;

import game.core.Hero;
import game.core.events.GameEvent;
import game.enums.EventType;

public class PoisonEffect extends HeroDecorator {
    private static final int POISON_DAMAGE = 5;
    private static final int POISON_DURATION = 3;
    private int poisonTurnsRemaining;

    public PoisonEffect(Hero hero) {
        super(hero);
        this.poisonTurnsRemaining = POISON_DURATION;

        hero.notifyObservers(new GameEvent(
                EventType.POISON_APPLIED, hero, null,
                hero.getName() + " отравлен! Будет получать урон каждый ход в течение " + POISON_DURATION + " раундов"
        ));
    }

    @Override
    public void performAttack(Hero target) {
        super.performAttack(target);
        applyPoisonDamage();
    }

    private void applyPoisonDamage() {
        if (poisonTurnsRemaining > 0 && decoratedHero.isAlive()) {
            int currentHealth = decoratedHero.getHealth();
            decoratedHero.receiveDamage(POISON_DAMAGE);
            poisonTurnsRemaining--;

            decoratedHero.notifyObservers(new GameEvent(
                    EventType.DAMAGE, decoratedHero, decoratedHero,
                    "Отравление наносит урон " + decoratedHero.getName(), POISON_DAMAGE
            ));

            if (poisonTurnsRemaining == 0) {
                decoratedHero.notifyObservers(new GameEvent(
                        EventType.BUFF_EXPIRED, decoratedHero, null,
                        "Яд рассеялся с " + decoratedHero.getName()
                ));
            }
        }
    }

    @Override
    public String getDescription() {
        String base = decoratedHero.getDescription();
        if (poisonTurnsRemaining > 0) {
            return base + " [Отравлен: " + poisonTurnsRemaining + " ходов]";
        }
        return base;
    }
}