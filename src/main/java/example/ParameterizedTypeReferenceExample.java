package example;

import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author Tushar Chokshi @ 7/13/15.
 */
public class ParameterizedTypeReferenceExample {
    public static void main(String[] args) {
        Type type = new ParameterizedTypeReference<ArrayList<? extends Object>>(){}.getType();
        System.out.println(type.getClass().getName());
        System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
    }
}
