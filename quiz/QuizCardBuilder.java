package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class QuizCardBuilder
{
    private JTextArea question; // онке дкъ бнопняю
    private JTextArea answer; // онке дкъ нрберю
    private ArrayList<QuizCard> cardList; // яохянй йюпрнвей
    private JFrame frame; // нямнбмне нймн

    public static void main(String[] args)
    {
        QuizCardBuilder builder = new QuizCardBuilder(); // янгдюмхе щйгелокъпю йкюяяю
        builder.go(); // гюосяй опнцпюллш
    }

    public void go()
    {
        frame = new JFrame("Quiz Card Builder"); // хмхжхюкхгюжхъ нямнбмнцн нймю
        JPanel mainPanel = new JPanel(); // назъбкемхе х хмхжхюкхгюжхъ нямнбмни оюмекх
        Font bigFont = new Font("sanserif", Font.BOLD, 24); // мюярпнийю ьпхтрю
        question = new JTextArea(6, 20); // хмхжхюкхгюжхъ онкъ дкъ бнопняю
        question.setLineWrap(true); // оепемня ярпнй (дю)
        question.setWrapStyleWord(true); // оепемня он якнбюл (дю)
        question.setFont(bigFont); // хглемемхе ьпхтрю рейярю б онке дкъ бнопняю

        JScrollPane qScroller = new JScrollPane(question); // яйпнккеп дкъ онкъ бнопняю
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // бепрхйюкэмши яйпнккхмц (бяецдю)
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // цнпхгнмрюкэмши яйпнккхмц (мхйнцдю)

        answer = new JTextArea(6, 20); // хмхжхюкхгюжхъ онкъ дкъ нрберю
        answer.setLineWrap(true); // оепемня ярпнй (дю)
        answer.setWrapStyleWord(true); // оепемня он якнбюл (дю)
        answer.setFont(bigFont); // хглемемхе ьпхтрю рейярю б онке дкъ нрберю

        JScrollPane aScroller = new JScrollPane(answer); // яйпнккеп дкъ онкъ нрберю
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // бепрхйюкэмши яйпнккхмц (бяецдю)
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // цнпхгнмрюкэмши яйпнккхмц (мхйнцдю)

        JButton nextButton = new JButton("дЮКЕЕ"); // назъбкемхе х хмхжхюкхгюжхъ ймнойх
        cardList = new ArrayList<QuizCard>(); // хмхжхюкхгюжхъ яохяйю йюпрнвей
        JLabel qLabel = new JLabel("бНОПНЯ"); // назъбкемхе х хмхжхюкхгюжхъ лерйх бнопняю
        JLabel aLabel = new JLabel("нРБЕР"); // назъбкемхе х хмхжхюкхгюжхъ лерйх нрберю

        mainPanel.add(qLabel); // днаюбкемхе лерйх бнопняю мю нямнбмсч оюмекэ
        mainPanel.add(qScroller); // днаюбкемхе яйпнккепю я онкел бнопняю мю нямнбмсч оюмекэ
        mainPanel.add(aLabel); // днаюбкемхе лерйх нрберю мю нямнбмсч оюмекэ
        mainPanel.add(aScroller); // днаюбкемхе яйпнккепю я онкел нрберю мю нямнбмсч оюмекэ
        mainPanel.add(nextButton); // днаюбкемхе ймнойх мю нямнбмсч оюмекэ

        nextButton.addActionListener(new NextCardListener()); // днаюбкемхе яксьюрекъ мю ймнойс

        JMenuBar menuBar = new JMenuBar(); // назъбкемхе х хмхжхюкхгюжхъ оюмекх я лемч
        JMenu fileMenu = new JMenu("тЮИК"); // назъбкемхе х хмхжхюкхгюжхъ ярпнйх лемч
        JMenuItem newMenuItem = new JMenuItem("яНГДЮРЭ"); // назъбкемхе х хмхжхюкхгюжхъ осмйрю лемч
        JMenuItem saveMenuItem = new JMenuItem("яНУПЮМХРЭ"); // назъбкемхе х хмхжхюкхгюжхъ осмйрю лемч

        newMenuItem.addActionListener(new NewMenuListener()); // днаюбкемхе яксьюрекъ мю осмйр лемч
        saveMenuItem.addActionListener(new SaveMenuListener()); // днаюбкемхе яксьюрекъ мю осмйр лемч

        fileMenu.add(newMenuItem); // днаюбкемхе осмйрю "янгдюрэ" б осмйр лемч "тюик"
        fileMenu.add(saveMenuItem); // днаюбкемхе осмйрю "янупюмхрэ" б осмйр лемч "тюик"
        menuBar.add(fileMenu); // днаюбкемхе осмйрю лемч "тюик" б оюмекэ лемч
        frame.setJMenuBar(menuBar); // днаюбкемхе оюмекх лемч б нямнбмне нймн
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // днаюбкемхе нямнбмни оюмекх б жемрп нямнбмнцн нймю
        frame.setSize(500, 600); // нопедекемхе пюглепю нямнбмнцн нймю
        frame.setVisible(true); // бхдхлнярэ нямнбмнцн нймю (дю)
    }

    public class NextCardListener implements ActionListener // яксьюрекэ ймнойх "дюкее"
    {
        public void actionPerformed(ActionEvent event) // пеюкхгюжхъ лерндю хг хмрептеияю
        {
            QuizCard card = new QuizCard(question.getText(), answer.getText()); // янупюмемхе дюммшу б йюпрнвйс
            cardList.add(card); // днаюбкемхе йюпрнвйх б яохянй
            clearCard(); // нвхыемхе онкеи бнопняю х нрберю
        }
    } // OUT OF INNER CLASS

    public class SaveMenuListener implements ActionListener // яксьюрекэ осмйрю лемч "янупюмхрэ"
    {
        public void actionPerformed(ActionEvent event) // пеюкхгюжхъ лерндю хг хмрептеияю
        {
            QuizCard card = new QuizCard(question.getText(), answer.getText()); // янупюмемхе дюммшу б йюпрнвйс
            cardList.add(card); // днаюбкемхе йюпрнвйх б яохянй

            JFileChooser fileSave = new JFileChooser(); // назъбкемхе х хмхжхюкхгюжхъ дхюкнцнбнцн нймю
            fileSave.showSaveDialog(frame); // бшгнб дхюкнцнбнцн нймю б нямнбмнл нйме опнцпюллш
            saveFile(fileSave.getSelectedFile()); // бшгнб лерндю дкъ янупюмемхъ б тюик
        }
    } // OUT OF INNER CLASS

    public class NewMenuListener implements ActionListener // яксьюрекэ осмйрю лемч "янгдюрэ"
    {
        public void actionPerformed(ActionEvent event) // пеюкхгюжхъ лерндю хг хмрептеияю
        {
            cardList.clear(); // нвхярхрэ яохянй йюпрнвей
            clearCard(); // нвхыемхе онкеи бнопняю х нрберю
        }
    } // OUT OF INNER CLASS

    private void clearCard() // лернд дкъ нвхыемхъ онкеи бнопняю х нрберю
    {
        question.setText(""); // нвхярхрэ онке дкъ бнопняю
        answer.setText(""); // нвхярхрэ онке дкъ нрберю
        question.requestFocus(); // онлеярхрэ йспянп б мювюкн онкъ дкъ бнопняю
    }

    private void saveFile(File file) // лернд дкъ гюохях б тюик
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // ябъгйю жеомнцн онрнйю я онрнйнл янедхмемхъ

            for(QuizCard card : cardList) // жхйк FOREACH
            {
                writer.write(card.getQuestion() + "/"); // гюохяэ дюммшу хг онкъ дкъ бнопняю
                writer.write(card.getAnswer() + "\n"); // гюохяэ дюммшу хг онкъ дкъ нрберю
            } // OUT OF LOOP
            writer.close(); // гюйпшрхе онрнйю
        }
        catch(IOException exception) // напюанрйю хяйкчвемхи
        {
            System.out.println("мЕ СДЮКНЯЭ ГЮОХЯЮРЭ ДЮММШЕ"); // бшбнд яннаыемхъ на ньхайе
            exception.printStackTrace(); // бшбнд хмтнплюжхх на ньхайе
        } // OUT OF TRY-CATCH
    }
}
