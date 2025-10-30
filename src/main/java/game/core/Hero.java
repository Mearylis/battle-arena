package game.core;

import game.observers.GameObserver;
import game.core.events.GameEvent;
import game.strategies.attack.AttackStrategy;
import game.strategies.defense.DefenseStrategy;
import game.strategies.defense.ShieldBlock;
import game.enums.EventType;
import java.util.ArrayList;
import java.util.List;

public abstract class Hero {
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int attackPower;
    private boolean isAlive;

    private AttackStrategy activeAttack;
    private DefenseStrategy activeDefense;
    private List<GameObserver> observers;

    public Hero(String name, int health, int mana, int attackPower) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.attackPower = attackPower;
        this.isAlive = true;
        this.observers = new ArrayList<>();
        this.activeDefense = new ShieldBlock();
    }

    public void performAttack(Hero target) {
        if (!isAlive || !target.isAlive()) return;

        if (mana >= activeAttack.getManaCost()) {
            if (activeAttack.getManaCost() > 0) {
                useMana(activeAttack.getManaCost());
            }
            activeAttack.execute(this, target);
        } else {
            notifyObservers(new GameEvent(
                    EventType.ATTACK, this, target,
                    name + " пытается атаковать, но не хватает маны!", 0
            ));
        }
    }

    public void receiveDamage(int damage) {
        if (!isAlive) return;

        health = Math.max(0, health - damage);

        notifyObservers(new GameEvent(
                EventType.DAMAGE, this, this,
                name + " получает урон", damage
        ));

        if (health <= 0) {
            isAlive = false;
            notifyObservers(new GameEvent(
                    EventType.DEATH, this, this, name + " пал в бою"
            ));
        }
    }

    public void heal(int amount) {
        if (!isAlive) return;

        int oldHealth = health;
        health = Math.min(maxHealth, health + amount);
        int actualHeal = health - oldHealth;

        if (actualHeal > 0) {
            notifyObservers(new GameEvent(
                    EventType.HEAL, this, this,
                    name + " лечит раны", actualHeal
            ));
        }
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
        if (amount > 0) {
            notifyObservers(new GameEvent(
                    EventType.MANA_USED, this, this, "", amount
            ));
        }
    }

    public void restoreMana(int amount) {
        int oldMana = mana;
        mana = Math.min(maxMana, mana + amount);
        int actualRestore = mana - oldMana;

        if (actualRestore > 0) {
            notifyObservers(new GameEvent(
                    EventType.HEAL, this, this,
                    name + " восстанавливает ману", actualRestore
            ));
        }
    }

    public void setAttackStrategy(AttackStrategy strategy) {
        this.activeAttack = strategy;
        notifyObservers(new GameEvent(
                EventType.STRATEGY_CHANGE, this, null,
                name + " меняет стиль атаки на: " + strategy.getDescription()
        ));
    }

    public void setDefenseStrategy(DefenseStrategy strategy) {
        this.activeDefense = strategy;
        notifyObservers(new GameEvent(
                EventType.STRATEGY_CHANGE, this, null,
                name + " меняет стиль защиты на: " + strategy.getDescription()
        ));
    }

    public void registerObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }

    public abstract void useUltimateAbility(Hero target);
    public abstract String getDescription();

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setMana(int mana) {
        this.mana = mana;
    }

    protected void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getAttackPower() { return attackPower; }
    public boolean isAlive() { return isAlive; }
    public AttackStrategy getActiveAttack() { return activeAttack; }
    public DefenseStrategy getActiveDefense() { return activeDefense; }
    public List<GameObserver> getObservers() { return observers; }
}