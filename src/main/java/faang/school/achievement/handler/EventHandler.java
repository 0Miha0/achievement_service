package faang.school.achievement.handler;

public interface EventHandler<T> {

    boolean supportsEvent(Class<?> event);

    void handleEvent(T event);
}
