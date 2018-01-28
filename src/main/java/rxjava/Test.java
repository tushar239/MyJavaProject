package rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tushar Chokshi @ 5/3/17.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<String> symbols = Arrays.asList("GOOG", "AAPL", "MSFFT", "INTC");
        Observable<StockInfo> feed = StockServer.getFeed(symbols);
        // you can apply all these functions like map, filter etc on Observable also. Those kind of changed/filtered data will be pushed to Data Channel for the consumption by a Subscriber.
        feed
                // Transforming Observables
                .map(stockInfo -> new StockInfo(stockInfo.getTicker(), stockInfo.getPrice() * 1.1))
                //.reserve(10)
                //.skip(10)
                //.takeWhile(stockInfo -> stockInfo.getPrice() > 60)
                //.skipWhile(stockInfo -> stockInfo.getPrice() > 60)
                ;

        // here, you are subscribing an Action class (Observer) to Observable.
        // As soon as you do that, subscribe method runs a function set in Observable during its creation in StockServer
        // If Action(Consumer) for listening to Error channel is not specified, then subscribe method will just throw an exception, if error comes through error channel.
        // If Action(Consumer) for listening to Error channel is specified, then that Action will be executed and subscribe method is still in running condition. It just that Observable will stop pushing anymore data to Data Channel.

        // This code creates ActionSubscriber(onNext Action, onError Action, onCompletion Action).
        // ActionSubscriber is a subclass of an abstract class Subscriber
        Subscription subscription =
                feed
                        //.subscribeOn(Schedulers.computation()) // this line is to make the subscription asynchronous. You are telling RxJava that use number of thread as per availability of the cpu cores. It's like Java 8's ForkJoinPool's commonPool.
                        //.subscribeOn(Schedulers.io()) // you are saying that my processes are IO intensive, so it's better to have more threads than what is provided by cpu cores.
                        .subscribeOn(Schedulers.newThread()) // create new single thread
                        //.subscribeOn(Schedulers.from(your own executor)) // provide your own executor
                        //.share() //Cold vs Hot Observables. Normally, when more than one subscribers are subscribed to the same Observable, Observable starts pushing the data from the beginning to each of those subscribers. This is called COLD Observerable. If you make Observable shared, subsequent subscribers will start receiving data from that that point of time. This is called HOT Observable
                        .onErrorResumeNext( // In case of Error, Observable will stop pushing more data Data Channel, but what if you want data to be continuously pushed somehow? You need to subscribe this subscriber to a new Observable, in case of Error.
                                exception -> {
                                    System.out.println(exception.getMessage());
                                    return StockServer.getFeed(symbols); // return a new Observable, which will start pushing data to this subscriber
                                })
                        .subscribe(
                                stockInfo -> System.out.println("subscriber-1: " + stockInfo), // Action(Consumer) listening to Data Channel
                                exception -> System.out.println("subscriber-1: " + exception.getMessage()),// Action(Consumer) listening to Error Channel
                                () -> System.out.println("subscriber-1: " + "end signal came from Data Channel"));
        //subscription.unsubscribe();

        // Subscriber without Action(Consumer) for Error Channel.
        //feed.subscribe(stockInfo -> System.out.println(stockInfo));

        Thread.sleep(5000);

        // Subscribing another subscriber using your own subclass of abstract class Subscriber.
        // By doing this, you have a more control over the subscription.
        feed
                .subscribeOn(Schedulers.newThread()) // for async processing
                .onErrorResumeNext( // In case of Error, Observable will stop pushing more data Data Channel, but what if you want data to be continuously pushed somehow? You need to subscribe this subscriber to a new Observable, in case of Error.
                        exception -> {
                            System.out.println(exception.getMessage());
                            return StockServer.getFeed(symbols); // return a new Observable, which will start pushing data to this subscriber
                        })
                .subscribe(new Subscriber<StockInfo>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("subscriber-2: " + "end signal came from Data Channel");
                    }

                    @Override
                    public void onError(Throwable exception) {
                        System.out.println("subscriber-2: " + exception.getMessage());
                    }

                    @Override
                    public void onNext(StockInfo stockInfo) {
                        System.out.println("subscriber-2: " + stockInfo);

                        if (stockInfo.getTicker().equalsIgnoreCase("AAPL") && stockInfo.getPrice() > 90) {
                            System.out.println("Unsubscribing. Thank you. I don't want anymore data.");
                            unsubscribe();
                        }

                    }
                });

        Thread.sleep(50000);

    }
}
