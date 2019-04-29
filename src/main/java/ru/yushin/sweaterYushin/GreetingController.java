package ru.yushin.sweaterYushin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yushin.sweaterYushin.domain.Message;
import ru.yushin.sweaterYushin.repos.MessageRepo;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepo messageRepo;

    /**
     *
     * @param model модель данных в виде Map
     * @return адрес мапинга странички
     */
    @GetMapping("/")
    public String greeting(Map<String, Object> model){

        return "greeting";
    }

    /**
     * мапинг из main.mustache
     * @param model модель данных, отображает значение из параметра model
     * @return адрес странички на которую мапим данные из метода
     */
    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "main";
    }

    /**
     * мапинг из main.mustache
     * @param text текст который вписывает в поле
     * @param tag
     * @param model модель куда складываем данные
     * @return адрес странички на которую мапим данные из метода
     */
    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }

    /**
     * мапинг из main.mustache
     * @param filter слово по которому ищем что мы вводили, если поле пустое, возвращает все что есть,
     *               если введено неверно, возвращает ничего
     * @param model
     * @return
     */
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }
}