package mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping("/hello")
    public String greeting(@RequestParam(name = "name")String name,
                           @RequestParam(name = "country",required = false,defaultValue = "vietnam")String country){
        return "hello" + name + "from" + country;
    }
//    @RequestMapping("/hello2")
//    public String greeting2(@RequestParam(name = "name")String name,
//                           @RequestParam(name = "country",required = false,defaultValue = "vietnam")String country){
//        Map<String, String> result = new HashMap<>();
//        result.put("name",name);
//        result.put("country",country);
//        return result
//    }
}
