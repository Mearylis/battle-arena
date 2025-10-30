package game.heroes;

import game.core.Hero;
import game.strategies.attack.MagicAttack;
import game.strategies.defense.MagicBarrier;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 85, 150, 32);
        setAttackStrategy(new MagicAttack());
        setDefenseStrategy(new MagicBarrier());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 60) {
            notifyObservers(new GameEvent(
                    EventType.ATTACK, this, target,
                    getName() + " пытается призвать молнию, но маны недостаточно!", 0
            ));
            return;
        }

        useMana(60);
        int damage = (int)(getAttackPower() * 2.2);
        target.receiveDamage(damage);

        restoreMana(25);

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " призывает УДАР МОЛНИИ! Наносит " + damage + " урона и восстанавливает ману"
        ));
    }

    @Override
    public String getDescription() {
        return "Маг - могущественный заклинатель с низкой защитой";
    }
}