package quiz;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class QuizCardPlayer
{
    private JTextArea display; // онке бшбндю
    private JTextArea answer; // онке дкъ нрберю
    private ArrayList<QuizCard> cardList; // яохянй йюпрнвей
    private QuizCard currentCard; // рейсыюъ йюпрнвйю
    private int currentCardIndex; // хмдейя рейсыеи йюпрнвйх
    private JFrame frame; // нямнбмне нймн
    private JButton nextButton; // ймнойю "дюкее"
    private boolean isShowAnswer; // онйюгюм кх нрбер

    public static void main(String[] args)
    {
        QuizCardPlayer reader = new QuizCardPlayer(); // янгдюмхе щйгелокъпю йкюяяю
        reader.go(); // гюосяй опнцпюллш
    }

    public void go()
    {
        frame = new JFrame("Quiz Card Player"); // хмхжхюкхгюжхъ нямнбмнцн нймю
        JPanel mainPanel = new JPanel(); // хмхжхюкхгюжхъ оюмекх
        Font bigFont = new Font("sanserif", Font.BOLD, 24); // мюярпнийю ьпхтрю

        display = new JTextArea(10, 20);
        display.setFont(bigFont); // сярюмнбйю ьпхтрю
        display.setLineWrap(true); // оепемня ярпнйх (дю)
        display.setEditable(false); // гюопер мю педюйрхпнбюмхе

        JScrollPane qScroller = new JScrollPane(display); // яйпнккеп дкъ онкъ DISPLAY
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // бепрхйюкэмши яйпнккхмц
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // цнпхгнмрюкэмши яйпнккхмц
        mainPanel.add(qScroller); // днаюбхрэ яйпнккеп мю оюмекэ

        nextButton = new JButton("Show Question"); // хмхжхюкхгюжхъ ймнойх
        mainPanel.add(nextButton); // днаюбкемхе ймнойх мю оюмекэ
        nextButton.addActionListener(new NextCardListener()); // днаюбкемхе яксьюрекъ дкъ ймнойх

        JMenuBar menuBar = new JMenuBar(); // оюмекэ лемч
        JMenu fileMenu = new JMenu("File"); // осмйр лемч
        JMenuItem loadMenuItem = new JMenuItem("Load card set"); // ондосмйр лемч
        loadMenuItem.addActionListener(new openMenuListener()); // яксьюрекэ дкъ осмйрю лемч
        fileMenu.add(loadMenuItem); // днаюбкемхе ондосмйрю б осмйр
        menuBar.add(fileMenu); // днаюбкемхе осмйрю б оюмекэ
        frame.setJMenuBar(menuBar); // днаюбкемхе лемч бн тпеил
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // цкюбмсч оюмекэ б жемрп тпеилю
        frame.setSize(640, 500); // пюглеп тпеилю
        frame.setVisible(true); // бхдхлнярэ бпеилю
    } // OUT OF GO METHOD

    public class NextCardListener implements ActionListener // яксьюрекэ дкъ ймнойх
    {
        public void actionPerformed(ActionEvent event) // пеюкхгюжхъ лерндю хмрептеияю
        {
            if(isShowAnswer) // еякх нрбер онйюгюм
            {
                display.setText(currentCard.getAnswer()); // бшбнд нрберю хг рейсыеи йюпрнвйх
                nextButton.setText("Next Card"); // ялемхрэ хлъ ймнойх
                isShowAnswer = false; // нрйкчвюел люпйеп нрберю
            }
            else
            {
                if(currentCardIndex < cardList.size()) // еякх яохянй еые ме носярек
                {
                    showNextCard(); // онйюгюрэ якедсчыхи бнопня
                }
                else
                {
                    display.setText("That was last card"); // бшбнд яннаыемхъ
                    nextButton.setEnabled(false); // ядекюрэ ймнойс меднярсомни
                } // OUT OF INNER IF-ELSE
            } // OUT OF OUTER IF-ELSE
        } // OUT OF METHOD
    } // OUT OF INNER CLASS

    public class openMenuListener implements ActionListener // яксьюрекэ дкъ осмйрю лемч
    {
        public void actionPerformed(ActionEvent event) // пеюкхгюжхъ лерндю хмрептеияю
        {
            JFileChooser fileOpen = new JFileChooser(); // нймн бшанпю тюикю
            fileOpen.showOpenDialog(frame); // онйюгюрэ дхюкнцнбне нймн
            loadFile(fileOpen.getSelectedFile()); // бшгбюрэ лернд дкъ бшапюммнцн тюикю
        }
    } // OUT OF INNER CLASS

    private void loadFile(File file) // лернд дкъ гюцпсгйх тюикю
    {
        cardList = new ArrayList<QuizCard>(); // хмхжхюкхгюжхъ яохяйю
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file)); // ондйкчвемхе й тюикс
            String line = null;
            while((line = reader.readLine()) != null) // онйю еярэ ярпнйю
            {
                makeCard(line); // бшгнб лерндю янгдюмхъ йюпрнвей
            }
            reader.close(); // гюйпшрхе онрнйю ббндю
        }
        catch(Exception exc)
        {
            System.out.println("couldn't read the card file"); // бшбнд яннаыемхъ мю йнмянкэ
            exc.printStackTrace(); // онкмши осрэ ньхайх
        } // OUT OF TRY-CATCH
        showNextCard();
    } // OUT OF METHOD

    private void makeCard(String lineToParse) // лернд янгдюмхъ йюпрнвей
    {
        String[] result = lineToParse.split("/"); // пюгдекъел ярпнйх бнопняю х нрберю
        QuizCard card = new QuizCard(result[0], result[1]); // янгдюмхе йюпрнвйх
        cardList.add(card); // днаюбкемхе йюпрнвйх б яохянй
        System.out.println("made a card"); // бшбнд яннаыемхъ б йнмянкэ
    } // OUT OF METHOD

    private void showNextCard() // лернд бшбндю хмтнплюжхх
    {
        currentCard = cardList.get(currentCardIndex); // бшапюрэ якедсчысч йюпрнвйс хг яохяйю
        currentCardIndex++; // сбекхвхрэ гмювемхе хмдейяю
        display.setText(currentCard.getQuestion()); // бшбнд бнопняю мю дхяокеи
        nextButton.setText("Show Answer"); // ялемхрэ хлъ ймнойх
        isShowAnswer = true; // хглемхрэ люпйеп
    } // OUT OF METHOD
} // OUT OF CLASS
