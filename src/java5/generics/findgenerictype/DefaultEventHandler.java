package java5.generics.findgenerictype;

/**
 * Created by chokst on 2/14/15.
 */
public class DefaultEventHandler extends EventHandler<DefaultEvent> {
    public void execute(DefaultEvent event) {
        System.out.println("Inside DefaultEventHandler");
    }
}
