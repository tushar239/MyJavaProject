package springparameterizedclass;

/**
 * @author Tushar Chokshi @ 4/12/15.
 */
public class MyEventHandler<MyChangeEvent> implements MyEventHandlerInterface {

    @Override
    public void execute(MyEvent event) {
        System.out.println("Event id:"+event.getId());
    }
}
