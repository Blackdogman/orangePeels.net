package net.orangepeels.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainCotroller {

    @GetMapping("/")
    public String WelcomeToWebsite(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("新请求：" +req.getRequestedSessionId() );
        for (Cookie cok:
             cookies) {
            System.out.println("-----------------------------------");
            System.out.println("cookies.name ----> " + cok.getName());
            System.out.println("cookies.val ----> " + cok.getValue());
            System.out.println("cookies.doMain ----> " + cok.getDomain());
            System.out.println("cookies.path ----> " + cok.getPath());
            System.out.println("cookies.comment ----> " + cok.getComment());
            System.out.println("cookies.version ----> " + cok.getVersion());
            System.out.println("cookies.secure ----> " + cok.getSecure());
            System.out.println("cookies.maxAge ----> " + cok.getMaxAge());
        }
        return "homePage";
    }

    @GetMapping("/playground")
    public String gotoUploadFile(){
        return "study/playground";
    }

}
