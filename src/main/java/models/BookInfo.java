package models;



import com.google.gson.Gson;

public class BookInfo {
    public Status status;
    public Data data;

    public static BookInfo fromJson(String json) {
        return new Gson().fromJson(json, BookInfo.class);
    }
}
//public class BookInfo {
//    public Status status;
//    public Data data;
//
//}
