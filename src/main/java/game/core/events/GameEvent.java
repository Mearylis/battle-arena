package game.core.events;


import game.enums.EventType;
import game.core.Hero;

public class GameEvent {
    private final EventType eventType;
    private final Hero source;
    private final Hero target;
    private final String description;
    private final int value;

    public GameEvent(EventType eventType, Hero source, Hero target, String description) {
        this(eventType, source, target, description, 0);
    }

    public GameEvent(EventType eventType, Hero source, Hero target, String description, int value) {
        this.eventType = eventType;
        this.source = source;
        this.target = target;
        this.description = description;
        this.value = value;
    }

    // Getters
    public EventType getEventType() { return eventType; }
    public Hero getSource() { return source; }
    public Hero getTarget() { return target; }
    public String getDescription() { return description; }
    public int getValue() { return value; }
}