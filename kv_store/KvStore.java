import java.util.HashMap;
public class KvStore{
    private HashMap<String,String>  cache ;
    public  KvStore(){
       cache = new HashMap<>();
    }
    protected void put(String key,String value){
        cache.put(key,value);
    }
    protected String get(String key){
        return cache.get(key);
    }
    protected void delete(String key){
        cache.remove(key);
    }

    
}