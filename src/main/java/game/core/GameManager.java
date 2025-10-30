package game.core;

import game.observers.*;
import game.factories.HeroFactory;
import game.decorators.FireEnchantment;
import game.decorators.StoneSkinBlessing;
import game.strategies.defense.MagicBarrier;
import game.strategies.defense.ShieldBlock;
import game.strategies.defense.DodgeDefense;
import game.strategies.attack.MeleeAttack;
import game.strategies.attack.RangedAttack;
import game.strategies.attack.MagicAttack;
import game.core.events.GameEvent;
import game.enums.EventType;
import game.enums.HeroType;

import java.util.*;

public class GameManager {
    private Scanner scanner;
    private List<GameObserver> globalObservers;
    private StatisticsTracker statisticsTracker;

    public GameManager() {
        this.scanner = new Scanner(System.in);
        this.globalObservers = new ArrayList<>();
        this.statisticsTracker = new StatisticsTracker();
        setupObservers();
    }

    private void setupObservers() {
        globalObservers.add(new ConsoleBattleLogger());
        globalObservers.add(new GameAnnouncer());
        globalObservers.add(statisticsTracker);
    }

    public void initializeGame() {
        System.out.println("🎮 ДОБРО ПОЖАЛОВАТЬ В ARENA OF HEROES! 🎮");
        System.out.println("=" .repeat(50));
    }

    public void startBattleSequence() {
        Hero player = selectPlayerHero();
        int battlesWon = 0;

        System.out.println("\nВаш герой: " + player.getDescription());
        System.out.println("Начинаем серию битв!\n");

        for (int battleNum = 1; battleNum <= 3; battleNum++) {
            Hero ai = generateRandomAI();

            System.out.println("Битва " + battleNum + " из 3");
            System.out.println("Противник: " + ai.getDescription());

            // Применяем баффы после каждой победы
            if (battlesWon > 0) {
                applyVictoryBuff(player, battlesWon);
            }

            boolean playerWon = executeBattle(player, ai);

            if (playerWon) {
                battlesWon++;
                System.out.println("🎉 Вы победили в битве " + battleNum + "!");

                // Восстановление после боя
                player.heal(30);
                player.restoreMana(25);
                System.out.printf("💚 Восстановлено: 30 здоровья, 25 маны%n");
            } else {
                System.out.println("💥 Вы проиграли битву " + battleNum);
                break;
            }

            if (battleNum < 3) {
                System.out.println("\n" + "─".repeat(40));
                System.out.println("Приготовьтесь к следующей битве...");
            }
        }

        System.out.println("\n" + "⭐".repeat(50));
        System.out.println("СЕРИЯ БИТВ ЗАВЕРШЕНА!");
        System.out.println("Всего побед: " + battlesWon + " из 3");

        statisticsTracker.printStatistics();
    }

    private Hero selectPlayerHero() {
        System.out.println("\nВыберите своего героя:");
        System.out.println("1. Воин - высокое здоровье, сильная защита");
        System.out.println("2. Маг - мощные заклинания, низкая защита");
        System.out.println("3. Лучник - меткие выстрелы, критические удары");

        int choice;
        while (true) {
            System.out.print("Ваш выбор (1-3): ");
            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Пожалуйста, введите число от 1 до 3");
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число от 1 до 3");
                scanner.next();
            }
        }

        System.out.print("Введите имя вашего героя: ");
        String name = scanner.next();

        HeroType[] types = {HeroType.WARRIOR, HeroType.MAGE, HeroType.ARCHER};
        Hero player = HeroFactory.createHero(types[choice - 1], name);

        // Регистрируем глобальных наблюдателей для игрока
        globalObservers.forEach(player::registerObserver);

        return player;
    }

    private Hero generateRandomAI() {
        HeroType[] types = HeroType.values();
        HeroType randomType = types[new Random().nextInt(types.length)];
        String[] names = {"Гаррош", "Джайна", "Сильвана", "Тралл", "Артас"};
        String randomName = names[new Random().nextInt(names.length)];

        Hero ai = HeroFactory.createHero(randomType, randomName);
        globalObservers.forEach(ai::registerObserver);

        return ai;
    }

    private boolean executeBattle(Hero player, Hero ai) {
        // Уведомляем о начале битвы
        globalObservers.forEach(observer ->
                observer.onEvent(new GameEvent(
                        EventType.BATTLE_START, player, ai, "Начало битвы"
                )));

        int round = 1;

        while (player.isAlive() && ai.isAlive() && round <= 20) {
            System.out.printf("\n--- Раунд %d ---%n", round);

            // Ход игрока
            playerTurn(player, ai);
            if (!ai.isAlive()) break;

            // Ход AI
            aiTurn(ai, player);
            if (!player.isAlive()) break;

            round++;
        }

        Hero winner = player.isAlive() ? player : ai;

        // Уведомляем о конце битвы
        globalObservers.forEach(observer ->
                observer.onEvent(new GameEvent(
                        EventType.BATTLE_END, winner, null, "Конец битвы"
                )));

        return player.isAlive();
    }

    private void playerTurn(Hero player, Hero ai) {
        System.out.println("\nВаш ход:");
        System.out.println("1. Обычная атака");
        System.out.println("2. Сменить тактику атаки");
        System.out.println("3. Сменить тактику защиты");
        System.out.println("4. Ультимейт способность");
        System.out.printf("Здоровье: %d/%d, Мана: %d/%d%n",
                player.getHealth(), player.getMaxHealth(), player.getMana(), player.getMaxMana());

        int choice;
        while (true) {
            System.out.print("Выберите действие (1-4): ");
            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) break;
                System.out.println("Пожалуйста, введите число от 1 до 4");
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число от 1 до 4");
                scanner.next();
            }
        }

        switch (choice) {
            case 1:
                player.performAttack(ai);
                break;
            case 2:
                changeAttackStrategy(player);
                break;
            case 3:
                changeDefenseStrategy(player);
                break;
            case 4:
                player.useUltimateAbility(ai);
                break;
        }
    }

    private void aiTurn(Hero ai, Hero player) {
        System.out.println("\nХод противника:");

        // AI логика
        if (ai.getMana() >= 40 && Math.random() < 0.3) {
            ai.useUltimateAbility(player);
        } else if (ai.getHealth() < ai.getMaxHealth() * 0.3 && Math.random() < 0.5) {
            // Меняет защиту при низком здоровье
            ai.setDefenseStrategy(new ShieldBlock());
            ai.performAttack(player);
        } else if (ai.getMana() < 20 && ai.getMana() > 0) {
            // Герой с маной меняет тактику при низкой мане
            ai.setDefenseStrategy(new MagicBarrier());
            ai.performAttack(player);
        } else {
            ai.performAttack(player);
        }
    }

    private void changeAttackStrategy(Hero hero) {
        System.out.println("\nВыберите тактику атаки:");
        System.out.println("1. Ближний бой - надежно, не требует маны");
        System.out.println("2. Дальний бой - высокий урон, шанс крита");
        System.out.println("3. Магическая атака - самый высокий урон, требует маны");

        int choice;
        while (true) {
            System.out.print("Ваш выбор (1-3): ");
            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Пожалуйста, введите число от 1 до 3");
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число от 1 до 3");
                scanner.next();
            }
        }

        switch (choice) {
            case 1:
                hero.setAttackStrategy(new MeleeAttack());
                break;
            case 2:
                hero.setAttackStrategy(new RangedAttack());
                break;
            case 3:
                hero.setAttackStrategy(new MagicAttack());
                break;
        }
    }

    private void changeDefenseStrategy(Hero hero) {
        System.out.println("\nВыберите тактику защиты:");
        System.out.println("1. Щит - надежно блокирует урон");
        System.out.println("2. Уклонение - шанс полностью избежать урона");
        System.out.println("3. Магический барьер - лучшая защита от магии");

        int choice;
        while (true) {
            System.out.print("Ваш выбор (1-3): ");
            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) break;
                System.out.println("Пожалуйста, введите число от 1 до 3");
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите число от 1 до 3");
                scanner.next();
            }
        }

        switch (choice) {
            case 1:
                hero.setDefenseStrategy(new ShieldBlock());
                break;
            case 2:
                hero.setDefenseStrategy(new DodgeDefense());
                break;
            case 3:
                hero.setDefenseStrategy(new MagicBarrier());
                break;
        }
    }

    private void applyVictoryBuff(Hero hero, int battlesWon) {
        System.out.println("\n🎁 За победу вы получаете усиление!");

        if (battlesWon == 1) {
            System.out.println("Огненное зачарование: атаки наносят дополнительный урон");

            Hero decorated = new FireEnchantment(hero);

        } else if (battlesWon == 2) {
            System.out.println("Благословение каменной кожи: повышена защита");
            Hero decorated = new StoneSkinBlessing(hero);
        }
    }
}