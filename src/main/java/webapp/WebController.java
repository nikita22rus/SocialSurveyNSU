package webapp;


import enteties.CompletedForm;
import enteties.CompletedFormRepository;
import enteties.Form;
import enteties.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class WebController {
    private Form form = new Form(); // Создаем форму, чтобы можно было пользоваться ее методами
    private CompletedFormRepository completedFormRepository = new CompletedFormRepository();

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
        form.deleteQuestion(number);

        model.addAttribute("form",form.getAllQuestions());

        Question question = new Question();
        model.addAttribute("question",question); // Перезаписываем пустой вопрос в модель, для дальнейшего заполнения в функции addQuestion()

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
        ArrayList<Question> questions = form.getAllQuestions();
        for (int i=0;i < questions.size();i++){
            questions.get(i).setAnswer(answer.get(i));
            System.out.println(questions.get(i));
        }

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = localDate.format(formatter);

        CompletedForm completedForm = new CompletedForm(questions,personName,date); // Создаем заполненную форму

        System.out.println("--- " + completedForm);
        completedFormRepository.saveCompletedForm(completedForm);// Сохраняем заполненную фопру в наш репозиторий
        System.out.println("--- " + completedFormRepository.getList());
        return "redirect:/anketa/complete";
    }
    @RequestMapping(value = "/anketa/listOfCompleted/", method = RequestMethod.GET)
    public String showCompletedForm(Model model){
        System.out.println("listOfCompleted");
        model.addAttribute("completedFormRepository", completedFormRepository.getList());

        return "listOfCompleted";
    }

    @RequestMapping(value = "/anketa/singleForm", method = RequestMethod.POST)
    public String showOneAnketa(@ModelAttribute("name") String name,@ModelAttribute("id") String id, Model model){
        System.out.println("showOneAnketa");
        System.out.println("name: " + name);
        System.out.println("id: " + id);
        model.addAttribute("name",name);
        ArrayList<Question> questions = completedFormRepository.getById(Long.valueOf(id)).form;

        CompletedForm completedForm = completedFormRepository.getById(Long.valueOf(id));

        System.out.println("************************************");
        for (int i=0;i < questions.size();i++){

            System.out.println(questions.get(i));
        }
        System.out.println("************************************");
        model.addAttribute("questions",questions);

        return "singleForm";

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
