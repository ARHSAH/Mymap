import javax.imageio.metadata.IIOMetadataNode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class test {
    public static void main(String args[]){
        MyMap<String, Integer> a = new MyMap<>();
        Integer[] keys = new Integer[3];
        String[] values =  {"mamad", "arman"};
        a.put("salam", 5);
        a.put("mamad", 6);
        a.put("arman", 7);
        a.put("aliverdi", 8);
        //Predicate<Integer> predicate = num -> num > 4;
//        Comparator<String> comparator = new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return  o1.compareTo(o2);
//            }
//        };
        //a.sort(comparator);
        //a.removeAll(values);
        //a.removeIfValue(predicate);

        System.out.println(a.get("mamad"));
        System.out.println(a.containsKey("salam"));
        System.out.println(Arrays.toString(a.keys));
    }
}
