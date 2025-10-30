package game.heroes;

import game.core.Hero;
import game.strategies.attack.MeleeAttack;
import game.strategies.defense.ShieldBlock;
import game.core.events.GameEvent;
import game.enums.EventType;

public class Warrior extends Hero {
    private boolean hasStunned = false;

    public Warrior(String name) {
        super(name, 150, 50, 25);
        setAttackStrategy(new MeleeAttack());
        setDefenseStrategy(new ShieldBlock());
    }

    @Override
    public void useUltimateAbility(Hero target) {
        if (getMana() < 30) return;

        useMana(30);
        int damage = (int)(getAttackPower() * 1.8);
        target.receiveDamage(damage);

        hasStunned = true;

        notifyObservers(new GameEvent(
                EventType.ULTIMATE_USED, this, target,
                getName() + " использует ЩИТОВОЙ УДАР! Оглушает противника и наносит " + damage + " урона"
        ));
    }

    @Override
    public String getDescription() {
        return "Воин - мастер ближнего боя с высокой защитой";
    }

    public boolean hasStunned() {
        return hasStunned;
    }
}