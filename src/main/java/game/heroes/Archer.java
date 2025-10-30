package game.heroes;

import game.core.Hero;
import game.strategies.attack.RangedAttack;
import game.strategies.defense.DodgeDefense;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Archer extends Hero {
    public Archer(String name) {
        super(name, 110, 70, 30);
        setAttackStrategy(new RangedAttack());
        setDefenseStrategy(new DodgeDefense());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 35) {
            notifyObservers(new GameEvent(
                    EventType.ATTACK, this, target,
                    getName() + " пытается сделать снайперский выстрел, но маны недостаточно!", 0
            ));
            return;
        }

        useMana(35);
        int damage = (int)(getAttackPower() * 2.8);

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " делает СНАЙПЕРСКИЙ ВЫСТРЕЛ! Наносит " + damage + " урона"
        ));

        target.receiveDamage(damage);
    }

    @Override
    public String getDescription() {
        return "Лучник - меткий стрелок с шансом критического удара";
    }
}