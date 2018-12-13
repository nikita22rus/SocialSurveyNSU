package enteties;

import java.util.ArrayList;

public class CompletedForm {
    private Long id;
    public ArrayList<Question> form = new ArrayList<Question>();
    private String name;
    private String date;




    public CompletedForm(){}

    public CompletedForm(ArrayList<Question> list, String name, String date){
        this.form = list;
        this.name = name;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Question> getAllQuestions(){ // Возвращает список вопросов
        return form;
    }

    public String toString(){return "Name: " + this.name + " form: " + this.form;}

}
