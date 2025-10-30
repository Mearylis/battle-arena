package game.decorators;

import game.core.Hero;
import game.core.events.GameEvent;
import game.enums.EventType;

public class FireEnchantment extends HeroDecorator {
    private static final int BONUS_DAMAGE = 8;

    public FireEnchantment(Hero hero) {
        super(hero);

        hero.notifyObservers(new GameEvent(
                EventType.BUFF_APPLIED, hero, null,
                hero.getName() + " получает огненное зачарование! +" + BONUS_DAMAGE + " к урону"
        ));
    }

    @Override
    public void performAttack(Hero target) {
        super.performAttack(target);

        int fireDamage = BONUS_DAMAGE;
        target.receiveDamage(fireDamage);

        decoratedHero.notifyObservers(new GameEvent(
                EventType.DAMAGE, decoratedHero, target,
                "Дополнительный огненный урон", fireDamage
        ));
    }

    @Override
    public String getDescription() {
        return decoratedHero.getDescription() + " [Огненное зачарование]";
    }
}