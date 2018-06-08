package net.orangepeels.cotroller;

import net.orangepeels.model.BlogMarkDown;
import net.orangepeels.service.BlogMarkDownService;
import net.orangepeels.utils.EncryptTools;
import net.orangepeels.utils.MarkDownTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class FileController {

    @Autowired
    private BlogMarkDownService service;

    public FileController() {
    }

    /**
     * 文件上传
     * @param file 对应的上传文件
     * @// TODO: 2018/6/7 并没有完成，不知道该怎么做
     */
    @RequestMapping("/upFile")
    public int upFile(MultipartFile file) throws IOException {
        byte[] item = new byte[10000];
        String fileName = file.getOriginalFilename();
        StringBuilder content = new StringBuilder();
        InputStream input = file.getInputStream();
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(reader);
        String temp;
        while((temp = br.readLine()) != null){
            content.append(temp).append("\r\n");
        }
        input.read(item);
        String md5Code = EncryptTools.getMD5(item);
        BlogMarkDown blog = new BlogMarkDown(fileName, content.toString(), md5Code);
        int reRow = service.saveMarkDown(blog);
        return reRow;
    }

    /**
     * 把markdown转换成HTML代码返回
     * @return 返回一个HTML代码
     */
    @RequestMapping("/getHTMLFromMD")
    public String getHTMLFromMD(HttpServletRequest req){
        String path = req.getParameter("file");
        System.out.println(path);
        String reStr = "";
        File file = new File(path);
        try {
            reStr = MarkDownTools.getHTML(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reStr;
    }
}
