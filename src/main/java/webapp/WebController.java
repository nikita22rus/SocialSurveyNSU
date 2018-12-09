package webapp;


import enteties.CompletedForm;
import enteties.Form;
import enteties.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

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
        System.out.println("deleteQuestion");
        System.out.println("number to delete: " + number);

        form.deleteQuestion(number);

        model.addAttribute("form",form.getAllQuestions());

        Question question = new Question();
        model.addAttribute("question",question); // Перезаписываем пустой вопрос в модель, для дальнейшего заполнения в функции addQuestion()

        System.out.println(form.getAllQuestions());
        return "redirect:/anketa"; // Эта штука уберает проблему с перезагрузкой страницы
        // С английского переводится как "Переадресовывать", думаю это оно и делает - после выполнения всех действий вызывает нашу самую первую функцию
        // /anketa - , которая просто выводит список
    }


    @RequestMapping(value = "/anketa/complete", method = RequestMethod.GET)
    public String completeForm(Model model){
        System.out.println("completeForm");
        String Name = new String();
        model.addAttribute("Name",Name); // Создаем и добавляем новый атрибут
        model.addAttribute("questions",form.getAllQuestions()); // И вопрсы, чтобы потом их вывести пользователю
        return "completeForm";
    }
    @RequestMapping(value = "/anketa/complete/addCompletedForm", method = RequestMethod.POST)
    public String addCompletedForm(@RequestParam("answer")ArrayList<String> answer,@RequestParam("personName") String personName, Model model) {
        System.out.println("addCompletedForm");
        System.out.println("personName: " + personName);
        ArrayList<Question> questions = form.getAllQuestions();
        for (String q : answer) {
            System.out.println(q);
        }
        for (int i=0;i < questions.size();i++){
            questions.get(i).setAnswer(answer.get(i));
        }

        CompletedForm completedForm = new CompletedForm(questions,personName);
        System.out.println(completedForm);
        return "redirect:/anketa/complete";
    }

//        <table>
//        <tr th:each="q : ${questions}">
//            <td>
//                <span th:text="${q.text}"></span>
//                <br>
//                <textarea
//    th:field="*{q.answer}"></textarea>
//            </td>
//        </tr>
//    </table>









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
