package quiz;

public class QuizCard
{
    private String question = null;
    private String answer = null;

    public QuizCard(String question, String answer) // йнмярпсйрнп
    {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() // опнярни церреп дкъ бнопняю
    {
        return question;
    }
    public String getAnswer() // опнярни церреп дкъ нрберю
    {
        return answer;
    }
}
