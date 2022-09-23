package quiz;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class QuizCardPlayer
{
    private JTextArea display; // ���� ������
    private JTextArea answer; // ���� ��� ������
    private ArrayList<QuizCard> cardList; // ������ ��������
    private QuizCard currentCard; // ������� ��������
    private int currentCardIndex; // ������ ������� ��������
    private JFrame frame; // �������� ����
    private JButton nextButton; // ������ "�����"
    private boolean isShowAnswer; // ������� �� �����

    public static void main(String[] args)
    {
        QuizCardPlayer reader = new QuizCardPlayer(); // �������� ���������� ������
        reader.go(); // ������ ���������
    }

    public void go()
    {
        frame = new JFrame("Quiz Card Player"); // ������������� ��������� ����
        JPanel mainPanel = new JPanel(); // ������������� ������
        Font bigFont = new Font("sanserif", Font.BOLD, 24); // ��������� ������

        display = new JTextArea(10, 20);
        display.setFont(bigFont); // ��������� ������
        display.setLineWrap(true); // ������� ������ (��)
        display.setEditable(false); // ������ �� ��������������

        JScrollPane qScroller = new JScrollPane(display); // �������� ��� ���� DISPLAY
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // ������������ ���������
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // �������������� ���������
        mainPanel.add(qScroller); // �������� �������� �� ������

        nextButton = new JButton("Show Question"); // ������������� ������
        mainPanel.add(nextButton); // ���������� ������ �� ������
        nextButton.addActionListener(new NextCardListener()); // ���������� ��������� ��� ������

        JMenuBar menuBar = new JMenuBar(); // ������ ����
        JMenu fileMenu = new JMenu("File"); // ����� ����
        JMenuItem loadMenuItem = new JMenuItem("Load card set"); // �������� ����
        loadMenuItem.addActionListener(new openMenuListener()); // ��������� ��� ������ ����
        fileMenu.add(loadMenuItem); // ���������� ��������� � �����
        menuBar.add(fileMenu); // ���������� ������ � ������
        frame.setJMenuBar(menuBar); // ���������� ���� �� �����
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // ������� ������ � ����� ������
        frame.setSize(640, 500); // ������ ������
        frame.setVisible(true); // ��������� ������
    } // OUT OF GO METHOD

    public class NextCardListener implements ActionListener // ��������� ��� ������
    {
        public void actionPerformed(ActionEvent event) // ���������� ������ ����������
        {
            if(isShowAnswer) // ���� ����� �������
            {
                display.setText(currentCard.getAnswer()); // ����� ������ �� ������� ��������
                nextButton.setText("Next Card"); // ������� ��� ������
                isShowAnswer = false; // ��������� ������ ������
            }
            else
            {
                if(currentCardIndex < cardList.size()) // ���� ������ ��� �� �������
                {
                    showNextCard(); // �������� ��������� ������
                }
                else
                {
                    display.setText("That was last card"); // ����� ���������
                    nextButton.setEnabled(false); // ������� ������ �����������
                } // OUT OF INNER IF-ELSE
            } // OUT OF OUTER IF-ELSE
        } // OUT OF METHOD
    } // OUT OF INNER CLASS

    public class openMenuListener implements ActionListener // ��������� ��� ������ ����
    {
        public void actionPerformed(ActionEvent event) // ���������� ������ ����������
        {
            JFileChooser fileOpen = new JFileChooser(); // ���� ������ �����
            fileOpen.showOpenDialog(frame); // �������� ���������� ����
            loadFile(fileOpen.getSelectedFile()); // ������� ����� ��� ���������� �����
        }
    } // OUT OF INNER CLASS

    private void loadFile(File file) // ����� ��� �������� �����
    {
        cardList = new ArrayList<QuizCard>(); // ������������� ������
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file)); // ����������� � �����
            String line = null;
            while((line = reader.readLine()) != null) // ���� ���� ������
            {
                makeCard(line); // ����� ������ �������� ��������
            }
            reader.close(); // �������� ������ �����
        }
        catch(Exception exc)
        {
            System.out.println("couldn't read the card file"); // ����� ��������� �� �������
            exc.printStackTrace(); // ������ ���� ������
        } // OUT OF TRY-CATCH
        showNextCard();
    } // OUT OF METHOD

    private void makeCard(String lineToParse) // ����� �������� ��������
    {
        String[] result = lineToParse.split("/"); // ��������� ������ ������� � ������
        QuizCard card = new QuizCard(result[0], result[1]); // �������� ��������
        cardList.add(card); // ���������� �������� � ������
        System.out.println("made a card"); // ����� ��������� � �������
    } // OUT OF METHOD

    private void showNextCard() // ����� ������ ����������
    {
        currentCard = cardList.get(currentCardIndex); // ������� ��������� �������� �� ������
        currentCardIndex++; // ��������� �������� �������
        display.setText(currentCard.getQuestion()); // ����� ������� �� �������
        nextButton.setText("Show Answer"); // ������� ��� ������
        isShowAnswer = true; // �������� ������
    } // OUT OF METHOD
} // OUT OF CLASS
