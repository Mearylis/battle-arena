package game.heroes;

import game.core.Hero;
import game.strategies.attack.MagicAttack;
import game.strategies.defense.MagicBarrier;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 80, 120, 30);
        setAttackStrategy(new MagicAttack());
        setDefenseStrategy(new MagicBarrier());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 50) return;

        useMana(50);
        int damage = (int)(getAttackPower() * 2.0);
        target.receiveDamage(damage);

        // Восстановление маны после ультимейта
        restoreMana(20);

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " призывает ЦЕПНУЮ МОЛНИЮ! Наносит " + damage + " урона и восстанавливает ману"
        ));
    }

    @Override
    public String getDescription() {
        return "Маг - мощный заклинатель с низким здоровьем";
    }
}
