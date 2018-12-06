package webapp;


import enteties.Form;
import enteties.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    private Form form = new Form(); // Создаем форму, чтобы можно было пользоваться ее методами

    @RequestMapping(value = "/anketa", method = RequestMethod.GET) // Функция первого запуска
    public String anketa (Model model){
        model.addAttribute(new Question());
        model.addAttribute("form",form.getAllQuestions());
        return "anketa";
    }

    @RequestMapping(value = "/anketa/add", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("question") Question question,Model model){
        form.questionVerifier(question); // Впроверка вопроса
        if (question.isCorrect()) {
            form.saveQuestion(question); // Если правильно, сохраняй
        }

        model.addAttribute("form", form.getAllQuestions()); // Перезапишем В модели список вопросов
        return "redirect:/anketa"; // Запускаем наш шаблон anketa
    }

    @RequestMapping(value = "/anketa/delete", method = RequestMethod.POST)
    public String deleteQuestion(@ModelAttribute("number") int number,Model model) { // Получаем номер из web интерфейса
        System.out.println("Controller --------------------- deleteQuestion");
        System.out.println("number to delete: " + number);

        form.deleteQuestion(number);

        model.addAttribute("form",form.getAllQuestions());

        Question question = new Question();
        model.addAttribute("question",question); // Перезаписываем пустой вопрос в модель, для дальнейшего заполнения в функции addQuestion()

        System.out.println(form.getAllQuestions());
        return "redirect:/anketa";
    }

//    @RequestMapping(value = "/anketa/delete/{number}", method = RequestMethod.GET)
//    @ResponseBody
//    public String deleteQuestion(@PathVariable int number, Model model){
//        System.out.println("Controller ----------------- deleteQuestion");
//        System.out.println("number: " + number);
//        form.deleteQuestion(number);
//        Question question = new Question();
//        model.addAttribute("question",question);
//        System.out.println(form.getAllQuestions());
//        model.addAttribute("form",form.getAllQuestions());
//        return "anketa";
//    }

}
