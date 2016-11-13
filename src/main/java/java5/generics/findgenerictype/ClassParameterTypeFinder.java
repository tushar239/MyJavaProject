package java5.generics.findgenerictype;

import java.lang.reflect.ParameterizedType;

/**
 * Created by chokst on 2/14/15.
 */
// http://www.javacodegeeks.com/2013/12/advanced-java-generics-retreiving-generic-type-arguments.html
public class ClassParameterTypeFinder {

    public static void main(String[] args) {
        ClassParameterTypeFinder classParameterTypeFinder = new ClassParameterTypeFinder();
        
        //Class parameterType = classParameterTypeFinder.findSuperClassParameterType(new DefaultEventHandler(), EventHandler.class, 0);
        ParameterizedType parameterizedType = (ParameterizedType) ((new DefaultEventHandler()).getClass().getGenericSuperclass());
        if(parameterizedType.getActualTypeArguments()[0] == DefaultEvent.class) {
            System.out.println("Generic Parameter Type is DefaultEvent");
        }
    }
}
