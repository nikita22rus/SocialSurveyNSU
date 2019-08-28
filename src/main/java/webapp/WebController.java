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
        form.questionVerifier(question); // Проверка вопроса
        if (question.isCorrect()) {
            form.saveQuestion(question); // Сохранить, если правильно
        }

        model.addAttribute("form", form.getAllQuestions()); // Перезапишем В модели список вопросов
        return "redirect:/anketa"; // Запускаем шаблон anketa
    }

    @RequestMapping(value = "/anketa/delete", method = RequestMethod.POST)
    public String deleteQuestion(@ModelAttribute("number") int number,Model model) { // Получаем номер из web интерфейса
        form.deleteQuestion(number);
        model.addAttribute("form",form.getAllQuestions());

        Question question = new Question();
        model.addAttribute("question",question); // Перезаписываем пустой вопрос в модель,
        // для дальнейшего заполнения в функции addQuestion()

        return "redirect:/anketa";
    }



    @RequestMapping(value = "/anketa/complete", method = RequestMethod.GET)
    public String completeForm(Model model){
        String Name = new String();
        model.addAttribute("Name",Name);
        model.addAttribute("questions",form.getAllQuestions());
        return "completeForm";
    }
    @RequestMapping(value = "/anketa/complete/addCompletedForm", method = RequestMethod.POST)
    public String addCompletedForm(@RequestParam("answer")ArrayList<String> answer,@RequestParam("personName") String personName, Model model) {
        ArrayList<Question> questions = form.getAllQuestions();

        ArrayList<Question> questionsDuplicates = new ArrayList<>();


        for (int i=0;i < questions.size();i++){
            Question q = questions.get(i);
            Question qd = new Question(q.getNumber(),q.getText(),q.getType());
            qd.setAnswer(answer.get(i));

            questionsDuplicates.add(qd);
        }

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = localDate.format(formatter);

        CompletedForm completedForm = new CompletedForm(questionsDuplicates,personName,date); // Создаем заполненную форму

        completedFormRepository.saveCompletedForm(completedForm);// Сохраняем заполненную фопру в наш репозиторий
        return "redirect:/anketa/complete";
    }
    @RequestMapping(value = "/anketa/listOfCompleted/", method = RequestMethod.GET)
    public String showCompletedForm(Model model){
        model.addAttribute("completedFormRepository", completedFormRepository.getList());
        return "listOfCompleted";
    }

    @RequestMapping(value = "/anketa/singleForm", method = RequestMethod.POST)
    public String showOneAnketa(@ModelAttribute("name") String name,@ModelAttribute("id") String id, Model model){

        model.addAttribute("name",name);
        ArrayList<Question> questions = completedFormRepository.getById(Long.valueOf(id)).form;
        CompletedForm completedForm = completedFormRepository.getById(Long.valueOf(id));

        model.addAttribute("questions",questions);
        return "singleForm";
        }
    }




