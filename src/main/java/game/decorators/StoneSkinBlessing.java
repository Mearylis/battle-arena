package game.decorators;

import game.core.Hero;
import game.core.events.GameEvent;
import game.enums.EventType;

public class StoneSkinBlessing extends HeroDecorator {
    private static final int DEFENSE_BONUS = 15;

    public StoneSkinBlessing(Hero hero) {
        super(hero);

        hero.notifyObservers(new GameEvent(
                EventType.BUFF_APPLIED, hero, null,
                hero.getName() + " получает каменную кожу! +" + DEFENSE_BONUS + " к защите"
        ));
    }

    @Override
    public void receiveDamage(int damage) {
        int reducedDamage = Math.max(0, damage - DEFENSE_BONUS);
        super.receiveDamage(reducedDamage);
    }

    @Override
    public String getDescription() {
        return decoratedHero.getDescription() + " [Каменная кожа]";
    }
}