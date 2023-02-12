import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ciweimao.novel.chapter.ChapterList;
import com.ciweimao.novel.chapter.ContentCommand;
import com.ciweimao.novel.chapter.ContentText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.ciweimao.novel.bookinfo.BookInfo;
import com.ciweimao.novel.request.HttpResponse;
import com.util.tools.ReadJson;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpResponse.config = ReadJson.Read(); // 读取配置文件, 用于设置token
        JSONObject bookInfo = BookInfo.getBookInfo("100336050");
        if (bookInfo != null) {
            System.out.println("书名: " + bookInfo.getString("book_name"));
            System.out.println("作者: " + bookInfo.getString("author_name"));
            System.out.println("简介: " + bookInfo.getString("description"));
        }
        String filePath = new FileInputStream(System.getProperty("user.dir") + "\\" + bookInfo.getString("book_name") + ".txt").toString();
        System.out.println(filePath);
        try {
            File file = new File(filePath);
            FileOutputStream fos = null;
            if (!file.exists()) {
                file.createNewFile();//如果文件不存在，就创建该文件
                fos = new FileOutputStream(file);//首次写入获取
            } else {
                //如果文件已存在，那么就在文件末尾追加写入
                fos = new FileOutputStream(file, true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }

            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);//指定以UTF-8格式写入文件
            for (Object chapter : Objects.requireNonNull(ChapterList.getChapterList("100336050"))) {
                for (Object chapterInfo : ((JSONObject) chapter).getJSONArray("chapter_list")) {
                    String chapter_id = ((JSONObject) chapterInfo).getString("chapter_id");
//                System.out.println(chapter_id);
//                Object chapter_index = ((JSONObject) chapterInfo).get("chapter_index");
//                System.out.println(chapter_index);
                    Object chapter_title = ((JSONObject) chapterInfo).get("chapter_title");
                    String commandKey = ContentCommand.getChapterCommand(chapter_id);
                    String Content = ContentText.getContentText(chapter_id, commandKey);
//                    System.out.println(Content);
                    System.out.println(chapter_title);
                    osw.write(chapter_title + "\n" + Content + "\n");
                }
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


