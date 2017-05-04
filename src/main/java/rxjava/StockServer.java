package rxjava;

import rx.Observable;
import rx.Subscriber;

import java.util.List;
import java.util.Random;

/**
 * @author Tushar Chokshi @ 5/3/17.
 */
public class StockServer {

    public static Observable<StockInfo> getFeed(List<String> symbols) {
        // Observable has a variable 'onSubscribe', which takes a function (Func1<Observable.OnSubscribe, Observable.OnSubscribe>)
        return Observable.create(subscriber -> processRequest(subscriber, symbols)); // it's like a passing a consumer
    }

    private static void processRequest(Subscriber<? super StockInfo> subscriber, List<String> symbols) {
        System.out.println("processing...");

        while (!subscriber.isUnsubscribed()) {
            symbols.stream()
                    .map(symbol -> new StockInfo(symbol, new Random().nextInt(100 - 1) + 1))
                    .forEach(stockInfo -> {

                        /*// Sending Error to Error Channel
                        if (stockInfo.getTicker().equalsIgnoreCase("AAPL")) {
                            subscriber.onError(new RuntimeException("some error"));
                        }*/

                        // Sending Data to Data Channel established between Observable and Subscriber(Observer)
                        subscriber.onNext(stockInfo);
                    });
        }
        subscriber.onCompleted(); // sending 'data end' signal to Data Channel. So, Data Channel is closed. No more data is sent to Data Channel.
        //subscriber.onNext(new StockInfo("blah", 10.3)); // This data will never be sent to Data Channel because 'End Signal' is sent to Data Channel using subscriber.onCompleted()
    }


}
