package enteties;

public class Question {

    private int number;
    protected String text;
    public boolean correctness; // вопрос правильный или неправильный

    private Long id; // id пока не нужен

    // Два конструктора класса
    public Question (){}

    public Question (String text) {
        this.text = text;
    }


    // Геттеры и Сеттеры для всех атрибутов класса
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString(){
        return this.text;
    }
}
