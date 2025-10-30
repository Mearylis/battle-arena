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
        this.activeDefense = new ShieldBlock(); // Защита по умолчанию
    }

    public void performAttack(Hero target) {
        if (!isAlive || !target.isAlive()) return;

        if (mana >= activeAttack.getManaCost()) {
            useMana(activeAttack.getManaCost());
            activeAttack.execute(this, target);
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
                    EventType.DEATH, this, this, name + " погибает в бою"
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
                    name + " восстанавливает здоровье", actualHeal
            ));
        }
    }

    public void useMana(int amount) {
        mana = Math.max(0, mana - amount);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    // Стратегии
    public void setAttackStrategy(AttackStrategy strategy) {
        this.activeAttack = strategy;
        notifyObservers(new GameEvent(
                EventType.STRATEGY_CHANGE, this, null,
                name + " меняет тактику атаки на: " + strategy.getDescription()
        ));
    }

    public void setDefenseStrategy(DefenseStrategy strategy) {
        this.activeDefense = strategy;
        notifyObservers(new GameEvent(
                EventType.STRATEGY_CHANGE, this, null,
                name + " меняет тактику защиты на: " + strategy.getDescription()
        ));
    }

    // Наблюдатели
    public void registerObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        observers.forEach(observer -> observer.onEvent(event));
    }

    // Абстрактные методы
    public abstract void useUltimateAbility(Hero target);
    public abstract String getDescription();

    // Getters
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

    // Setters
    public void setHealth(int health) { this.health = health; }
    public void setMana(int mana) { this.mana = mana; }
}