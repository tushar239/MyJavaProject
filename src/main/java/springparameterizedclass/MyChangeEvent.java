package springparameterizedclass;

/**
 * @author Tushar Chokshi @ 4/12/15.
 */
public class MyChangeEvent implements MyEvent {

    @Override
    public String getId() {
        return "ChangeEvent";
    }
}
