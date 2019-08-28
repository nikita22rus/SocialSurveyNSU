package enteties;

import java.util.*;

public class Form { // Класс нашей формы. Здесь хранятся наши вопросы. А также прописаны методы для работы с формой
    private ArrayList<Question> form = new ArrayList<Question>(); // Список вопросов

    public void questionVerifier(Question question){ // Функция проверки вопроса на правильность написания
        if (question.text.length() >= 3 && question.text.length() <= 512 ){
            question.setCorrectness(true); // Изменить атрибут вопроса на true - правильный
        }
        else {
            question.setCorrectness(false);
        }
    }

    public void saveQuestion(Question question){
        form.add(question); // Добавить вопрос в наш список вопросов
        numbersRecalculation(); // Пересчетать нумерацию вопросов
    }

    public void deleteQuestion(int number){
        form.remove(number-1); // Удали из нашего списка вопрос с индексом number-1
        numbersRecalculation();
    }

    public void numbersRecalculation(){
        if (form.size()!= 0) { // Если список не пустой
            for (int i = 0; i < form.size(); i++) {
                form.get(i).setNumber(i + 1); // Для каждого элемента списка с индексом i поставь номер i+1
            }
        }
    }

    public ArrayList<Question> getAllQuestions(){ // Возвращает список вопросов
        return form;
    }

}
