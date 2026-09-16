
class Main{
    public static void main(String[] args){
        KvStore kv = new KvStore();
        kv.put("name", "John");
        System.out.println(kv.get("name"));
        kv.delete("name");
        System.out.println(kv.get("name"));
    }
}