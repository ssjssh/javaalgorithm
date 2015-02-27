package ssj.algorithm.lang;

/**
 * Created by shenshijun on 15/2/14.
 */
final public class Tuple2<F, S> {
    final private F first;
    final private S second;
    private Integer hash_code = null;
    private String to_string = null;

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        if (to_string == null) {
            final StringBuilder sb = new StringBuilder("Tuple2{");
            sb.append("first=").append(first);
            sb.append(", second=").append(second);
            sb.append('}');
            to_string = sb.toString();
        }
        return to_string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple2 tuple2 = (Tuple2) o;

        if (first != null ? !first.equals(tuple2.first) : tuple2.first != null) return false;
        if (second != null ? !second.equals(tuple2.second) : tuple2.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (hash_code == null) {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            hash_code = result;
        }
        return hash_code;
    }

    public Tuple2(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
