package enteties;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Form { // Класс нашей формы. Хранятся наши вопросы. А также прописаны методы для работы с формой
    private ArrayList<Question> form = new ArrayList<Question>(); // Список вопросов
    private static AtomicLong counter = new AtomicLong(); // Не обращайте внимания, эта штука нужна для генерации ID,
    // Который нам не нужен сейчас

    public void questionVerifier(Question question){ // Функция проверки вопроса на правильность написания
        System.out.println("questionVerifier");
        if (question.text.length() >= 3 && question.text.length() <= 512 ){
            question.setCorrectness(true); // Измени атрибут вопроса на true - правильный
        }
        else {
            question.setCorrectness(false);
        }
    }

    public void saveQuestion(Question question){
        System.out.println("saveQuestion");
        form.add(question); // Добавить вопрос в наш список вопросов
        System.out.println(form);
//        question.setId(counter.incrementAndGet());
        numbersRecalculation(); // Пересчетать нумерацию вопросов
    }

    public void deleteQuestion(int number){
        System.out.println("deleteQuestion");
        System.out.println("number: " + number);
        System.out.println("index: " + (number-1) + " " + form.get(number-1));

        form.remove(number-1); // Удали из нашего списка вопрос с индексом number-1
        numbersRecalculation();
    }

    public void numbersRecalculation(){
        System.out.println("numbersRecalculation");
        if (form.size()!= 0) { // Если список не пустой
            for (int i = 0; i < form.size(); i++) {

                System.out.println("i: " + i + " " + form.get(i));
                System.out.println("number: " + (i + 1));

                form.get(i).setNumber(i + 1); // Для каждого элемента списка с индексом i поставь номер i+1
            }
        }
    }

    public ArrayList<Question> getAllQuestions(){ // Возвращает список вопросов
        return form;
    }

}
