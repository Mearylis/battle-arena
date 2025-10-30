package game.heroes;

import game.core.Hero;
import game.strategies.attack.MeleeAttack;
import game.strategies.defense.ShieldBlock;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Warrior extends Hero {
    public Warrior(String name) {
        super(name, 160, 40, 28);
        setAttackStrategy(new MeleeAttack());
        setDefenseStrategy(new ShieldBlock());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 30) {
            notifyObservers(new GameEvent(
                    EventType.ATTACK, this, target,
                    getName() + " пытается использовать щитовой удар, но маны недостаточно!", 0
            ));
            return;
        }

        useMana(30);
        int damage = (int)(getAttackPower() * 2.0);
        target.receiveDamage(damage);

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " использует МОЩНЫЙ ЩИТОВОЙ УДАР! Наносит " + damage + " урона"
        ));
    }

    @Override
    public String getDescription() {
        return "Воин - сильный боец ближнего боя с высокой защитой";
    }
}