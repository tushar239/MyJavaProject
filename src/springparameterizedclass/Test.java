package springparameterizedclass;

import java.lang.reflect.TypeVariable;

/**
 * @author Tushar Chokshi @ 4/12/15.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        MyChangeEvent event = new MyChangeEvent();

        // Using spring, you can actually find all beans of type MyEventHandlerInterface and for all those classes, you can find TypeParameters
        // whichever class has first TypeParameter as event name, you can call that Event Handler class

        TypeVariable<Class<MyEventHandler>>[] typeParameters = MyEventHandler.class.getTypeParameters();
        for (TypeVariable<Class<MyEventHandler>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getName());
            if(typeParameter.getName().equals(event.getClass().getSimpleName())) {
                MyEventHandler myEventHandler = (MyEventHandler)Class.forName("generics.parameterizedclass.MyEventHandler").newInstance();
                myEventHandler.execute(event);

            }
        }

    }
}
