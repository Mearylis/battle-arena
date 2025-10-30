package game.decorators;

import game.core.Hero;
import game.core.events.GameEvent;
import game.enums.EventType;

public class FireEnchantment extends HeroDecorator {
    private static final int BONUS_DAMAGE = 5;

    public FireEnchantment(Hero hero) {
        super(hero);

        hero.notifyObservers(new GameEvent(
                EventType.BUFF_APPLIED, hero, null,
                hero.getName() + " получает огненное зачарование! Атаки наносят дополнительный урон"
        ));
    }

    @Override
    public void performAttack(Hero target) {
        super.performAttack(target);

        // Добавляем дополнительный огненный урон
        int fireDamage = BONUS_DAMAGE;
        target.receiveDamage(fireDamage);

        decoratedHero.notifyObservers(new GameEvent(
                EventType.DAMAGE, decoratedHero, target,
                "Огненный урон от зачарования", fireDamage
        ));
    }

    @Override
    public String getDescription() {
        return decoratedHero.getDescription() + " [Огненное зачарование]";
    }

    // Добавляем реализацию абстрактного метода
    @Override
    public void useUltimateAbility(Hero target) {
        decoratedHero.useUltimateAbility(target);
    }
}