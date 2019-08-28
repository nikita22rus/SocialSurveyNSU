package enteties;

public class Question {

    private int number;
    protected String text;
    protected String answer;
    protected String type;


    public boolean correctness; // вопрос правильный или неправильный

    // Два конструктора класса
    public Question (){}

    public Question (int number,String text,String type) {
        this.number = number;
        this.text = text;
        this.type = type;
    }


    // Геттеры и Сеттеры для всех атрибутов класса
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public boolean isCorrect() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString(){
        return this.number + ". " + this.text + " - " + this.answer + ". Type = " + this.type;
    }
}
