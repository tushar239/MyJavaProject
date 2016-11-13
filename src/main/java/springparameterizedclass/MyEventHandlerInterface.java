package springparameterizedclass;

/**
 * @author Tushar Chokshi @ 4/12/15.
 */
public interface MyEventHandlerInterface<T extends MyEvent> {
    void execute(T event);
}
