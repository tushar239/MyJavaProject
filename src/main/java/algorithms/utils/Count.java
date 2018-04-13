package algorithms.utils;

public class Count {
    int count;

    public Count(int count) {
        this.count = count;
    }

    public int incrementByOne() {
        return ++count;
    }

    public int getCount() {
        return count;
    }

    public int increment(int cnt) {
        count += cnt;
        return count;
    }
}