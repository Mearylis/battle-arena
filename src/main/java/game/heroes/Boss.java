package game.heroes;

import game.core.Hero;
import game.strategies.attack.MeleeAttack;
import game.strategies.defense.ShieldBlock;

public class Boss extends Hero {
    private boolean justDrainedMana = false;
    private int storedReflectionDamage = 0;

    public Boss(String name) {
        super(name, 400, 200, 25);
        setAttack(new MeleeAttack());
        setDefense(new ShieldBlock());
    }

    @Override
    public void attack(Hero target) {
        justDrainedMana = false;

        if (Math.random() < 0.4 && getMana() >= 20) {
            stealMana(target);
        } else {

            super.attack(target);
        }

        if (justDrainedMana) {
            reflectStoredDamage(target);
        }
    }

    private void stealMana(Hero target) {
        useMana(20);

        int stolenMana = target.getMana();
        target.useMana(stolenMana);

        storedReflectionDamage = stolenMana / 2;

        notifyWatchers("💀 " + getName() + " ставит attendance на 0 у " + target.getName() + "!");
        notifyWatchers("🛡️ " + getName() + " готов отразить " + storedReflectionDamage + " урона!");

        justDrainedMana = true;
    }

    private void reflectStoredDamage(Hero target) {
        if (storedReflectionDamage > 0) {
            target.takeDamage(storedReflectionDamage);
            notifyWatchers("⚡ " + getName() + " отражает " + storedReflectionDamage + " урона на " + target.getName() + "!");
            storedReflectionDamage = 0;
        }
    }

    @Override
    public void takeDamage(int damage) {
        // Босс получает меньше урона
        int reducedDamage = (int)(damage * 0.6);
        super.takeDamage(reducedDamage);

        // Иногда контратакует когда его бьют
        if (isAlive() && Math.random() < 0.25) {
            notifyWatchers("💢 " + getName() + " контратака");
            getAttack().execute(this, null);
        }
    }

    @Override
    public void useUltimate(Hero target) {
        if (getMana() < 50) {
            notifyWatchers(getName() + " хочет использовать ультимейт, но нет маны!");
            return;
        }

        useMana(50);

        // Ультимейт: останавливает время
        notifyWatchers("⏰ " + getName() + " ОСТАНАВЛИВАЕТ ВРЕМЯ! Мир замирает...");

        int timeDamage = (int)(getAttackPower() * 1.5);
        target.takeDamage(timeDamage);

        // Лечится от урона
        int healAmount = timeDamage / 2;
        heal(healAmount);

        notifyWatchers("🕒 " + target.getName() + " заморожен во времени! Получает " + timeDamage + " урона");
        notifyWatchers("💚 " + getName() + " лечится на " + healAmount + " HP от манипуляции временем");
    }

    @Override
    public String getDescription() {
        return "Темиргалы Динмухаммед - Легендарный Босс (Много ХП, Воровство Маны, Отражение Урона, Много чего еще короче БОГ)";
    }
}