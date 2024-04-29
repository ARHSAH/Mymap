
    import java.util.Comparator;
    import java.util.Iterator;
    import java.util.function.BiFunction;
    import java.util.function.Consumer;
    import java.util.function.Predicate;

    public class MyMap<K,V> {

        interface MyIterator<K> extends Iterator<K> {
            K nextByAmount(int amount);

            K prevByAmount(int amount);

            boolean hasPrevious();

            K previous();

            void remove();
            // same as Iterator remove

            int previousIndex();

            int nextIndex();
        }


        K[] keys = (K[]) new Object[0];
        V[] values = (V[]) new Object[0];
        int size = 0;

        public V get(K key) {
            for (int i = 0; i < size; i++) {
                if (keys[i].equals(key)) {
                    return (V) values[i];
                }
            }
            return null;
        }

        public V getOrDefault(K key, V defaultValue) {
            if (get(key) != null) {
                return get(key);
            }
            return defaultValue;
        }

        public V put(K key, V value) {

            for (int i = 0; i < size; i++) {
                if (keys[i].equals(key)) {
                    values[i] = value;
                    return value;
                }
            }
            K[] extendedKeys = (K[]) new Object[size + 1];
            V[] extendedValues = (V[]) new Object[size + 1];
            for (int i = 0; i < size ; i++) {
                extendedKeys[i] = keys[i];
                extendedValues[i] = values[i];
            }
            extendedValues[size] = value;
            extendedKeys[size] = key;
            size ++;
            keys = (K[]) new Object[size];
            values = (V[]) new Object[size];
            for (int i = 0; i < size ; i++) {
                keys[i] = extendedKeys[i] ;
                values[i] = extendedValues[i] ;
            }
            return value;
        }

        public int indexOf(K key){
            for(int i = 0 ; i < size ; i++){
                if(keys[i].equals(key)){
                    return i;
                }
            }
            return 0;
        }
        public void putIfAbsent(K key, V value){
            if(get(key) == null){
                put(key, value);
            }
        }
        public K[] keys(){
            return keys;
        }
        public V[] values(){
            return values;
        }
        public void putAll(MyMap<? extends K, ? extends V> addMap){
            for (int i = 0; i < addMap.size ; i++) {
                K newKey = addMap.keys[i];
                V newValue = addMap.values[i];
                put(newKey, newValue);
            }
        }
        public boolean containsKey(K key){
            for (int i = 0; i < size ; i++) {
                if(keys[i].equals(key)){
                    return true;
                }
            }
            return false;
        }

        public boolean containsValue(V value){
            for (int i = 0; i < size ; i++) {
                if(values[i].equals(value)){
                    return true;
                }
            }
            return false;
        }

        public int size(){
            return size;
        }
        public void clear(){
            size = 0;
            K[] clearKeys = (K[]) new Object[0];
            V[] clearValues = (V[]) new Object[0];
            keys = clearKeys;
            values = clearValues;
        }

        public V remove(K key){
            int index  = indexOf(key);
            return remove(index);
        }
        public V remove(int index){
            K[] removedKeys = (K[]) new Object[size - 1];
            V[] removedValues = (V[]) new Object[size - 1];
            V removedValue = values[index];
            for(int i = 0 ; i < index ; i++){
                removedKeys[i] = keys[i];
                removedValues[i] = values[i];
            }
            for(int i = index ; i < size - 1 ; i++){
                removedKeys[i] = keys[i + 1];
                removedValues[i] = values[i + 1];
            }

            size --;
            keys = (K[]) new Object[size];
            values = (V[]) new Object[size];
            for (int i = 0; i < size ; i++) {
                keys[i] = removedKeys[i] ;
                values[i] = removedValues[i] ;
            }
            return removedValue;
        }

        public void removeAll(K[] removeKeys){
            for(K value : removeKeys){
                remove(value);
            }
        }

        public void removeIfValue(Predicate<V> predicate){
            for(int i = 0 ; i < size ; i++){
                if(predicate.test(values[i])){
                    remove(i);
                    i--;
                }
            }
        }

        public void removeIfKey(Predicate<K> predicate){
            for(int i = 0 ; i < size ; i++){
                if(predicate.test(keys[i])){
                    remove(i);
                    i--;
                }
            }
        }
        public void sort(Comparator<K> comparator){
            for(int i = 0 ; i < size ; i ++){
                for(int j = i + 1 ; j < size ; j ++){
                    if(comparator.compare(keys[i], keys[j]) > 0){
                        K key = keys[i];
                        keys[i] = keys[j];
                        keys[j] = key;
                    }
                }
            }

        }

        public MyIterator<K> iterator(){
            return new MyIterator<K>() {
                int index = -1;
                @Override
                public K nextByAmount(int amount) {
                    for (int i = 0 ; i < amount ; i++){
                        index ++;
                    }
                    return keys[index];
                }

                @Override
                public K prevByAmount(int amount) {
                    for (int i = 0 ; i < amount ; i++){
                        index --;
                    }
                    return keys[index];
                }

                @Override
                public boolean hasPrevious() {
                    if(index > 0){
                        return true;
                    }
                    return false;
                }

                @Override
                public K previous() {
                    if(hasPrevious()){
                        index --;
                        return keys[index];
                    }
                    return null;
                }

                @Override
                public void remove() {
                    MyMap.this.remove(index);
                }

                @Override
                public int previousIndex() {
                    return index - 1;
                }

                @Override
                public int nextIndex() {
                    if(index == size - 1){
                        return -1;
                    }
                    return index + 1;
                }

                @Override
                public boolean hasNext() {
                    if(index < size - 1){
                        return true;
                    }
                    return false;
                }

                @Override
                public K next() {
                    if(hasNext()){
                        index ++;
                        return keys[index + 1];
                    }
                    return null;
                }
            };
        }

        public void replaceAll(BiFunction<K,V,V> biFunction){
            for(int i = 0 ; i < size ; i++){
                values[i] = biFunction.apply(keys[i], values[i]);
            }
        }
        public V compute(K key, BiFunction<K, V, V> biFunction){
            int index = indexOf(key);
            values[index] = biFunction.apply(keys[index], values[index]);
            return values[index];
        }




    }