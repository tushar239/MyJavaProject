package algorithms.crackingcodinginterviewbook._3stackandqueue;

import java.util.LinkedList;

// pg. 238 of Cracking Coding Interview book
/*
    Create an animal shelter of dogs and cats.
    when people come to adopt an animal, shelter should give them the oldest (as per arrival time) animal to them.
    people can also ask specifically for a dog or a cat. you should give them the oldest dog or cat.
    You can use java's inbuilt LinkedList structure.
    Implement dequeueAnyAnimal(), dequeueCat(), dequeueDog(), enqueueAnimal() methods.

    Solution:
    use two different linked lists dogs and cats. As new dog/cat arrives, keep adding them to end of linked nilList, so that linked lists have dogs/cats in already sorted form (oldest (who came to shelter first) first in the nilList).
 */
public class _6AnimalShelter {
    int order = 0;
    private LinkedList<Animal> dogs = new LinkedList<>();
    private LinkedList<Animal> cats = new LinkedList<>();


    public void enqueueAnimal(Animal animal) {
        animal.setOrder(order++);
        if(animal instanceof Dog) {
            dogs.addLast(animal);
        } else {
            cats.addLast(animal);
        }
    }
    public Animal dequeueAnyAnimal() {
        Animal dog = null;
        Animal cat = null;
        if(!dogs.isEmpty()) {
            dog = dogs.peek();
        }
        if(!cats.isEmpty()) {
            cat = cats.peek();
        }

        if(dog == null && cat != null) {
            return cat;
        } else if(dog != null && cat == null) {
            return dog;
        } else if(dog == null && cat == null) {
            return null;
        }
        else {
            if(dog.getOrder() < cat.getOrder()) {
                return dogs.pop();
            } else {
                return cats.pop();
            }
        }
    }
    public Animal dequeueDog() {
        if(!dogs.isEmpty()) {
            return dogs.pop();
        }
        return null;
    }
    public Animal dequeueCat() {
        if(!cats.isEmpty()) {
            return cats.pop();
        }
        return null;
    }
    static class Animal {
        String name;
        int order;

        public Animal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
    static class Dog extends Animal {

        public Dog(String name) {
            super(name);
        }
    }
    static class Cat extends Animal {

        public Cat(String name) {
            super(name);
        }
    }

    public static void main(String[] args) {
        _6AnimalShelter animalShelter = new _6AnimalShelter();
        animalShelter.enqueueAnimal(new Dog("d1"));
        animalShelter.enqueueAnimal(new Dog("d2"));
        animalShelter.enqueueAnimal(new Cat("c1"));
        animalShelter.enqueueAnimal(new Dog("d3"));
        animalShelter.enqueueAnimal(new Cat("c2"));
        animalShelter.enqueueAnimal(new Cat("c3"));

        {
            Animal animal = animalShelter.dequeueAnyAnimal();
            System.out.println(animal.getName());
        }
        {
            Animal animal = animalShelter.dequeueDog();
            System.out.println(animal.getName());
        }
        {
            Animal animal = animalShelter.dequeueCat();
            System.out.println(animal.getName());
        }
        {
            Animal animal = animalShelter.dequeueAnyAnimal();
            System.out.println(animal.getName());
        }
        {
            Animal animal = animalShelter.dequeueDog();
            System.out.println(animal);
        }
    }
}
