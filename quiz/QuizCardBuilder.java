package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class QuizCardBuilder
{
    private JTextArea question; // ���� ��� �������
    private JTextArea answer; // ���� ��� ������
    private ArrayList<QuizCard> cardList; // ������ ��������
    private JFrame frame; // �������� ����

    public static void main(String[] args)
    {
        QuizCardBuilder builder = new QuizCardBuilder(); // �������� ���������� ������
        builder.go(); // ������ ���������
    }

    public void go()
    {
        frame = new JFrame("Quiz Card Builder"); // ������������� ��������� ����
        JPanel mainPanel = new JPanel(); // ���������� � ������������� �������� ������
        Font bigFont = new Font("sanserif", Font.BOLD, 24); // ��������� ������
        question = new JTextArea(6, 20); // ������������� ���� ��� �������
        question.setLineWrap(true); // ������� ����� (��)
        question.setWrapStyleWord(true); // ������� �� ������ (��)
        question.setFont(bigFont); // ��������� ������ ������ � ���� ��� �������

        JScrollPane qScroller = new JScrollPane(question); // �������� ��� ���� �������
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // ������������ ��������� (������)
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // �������������� ��������� (�������)

        answer = new JTextArea(6, 20); // ������������� ���� ��� ������
        answer.setLineWrap(true); // ������� ����� (��)
        answer.setWrapStyleWord(true); // ������� �� ������ (��)
        answer.setFont(bigFont); // ��������� ������ ������ � ���� ��� ������

        JScrollPane aScroller = new JScrollPane(answer); // �������� ��� ���� ������
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // ������������ ��������� (������)
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // �������������� ��������� (�������)

        JButton nextButton = new JButton("�����"); // ���������� � ������������� ������
        cardList = new ArrayList<QuizCard>(); // ������������� ������ ��������
        JLabel qLabel = new JLabel("������"); // ���������� � ������������� ����� �������
        JLabel aLabel = new JLabel("�����"); // ���������� � ������������� ����� ������

        mainPanel.add(qLabel); // ���������� ����� ������� �� �������� ������
        mainPanel.add(qScroller); // ���������� ��������� � ����� ������� �� �������� ������
        mainPanel.add(aLabel); // ���������� ����� ������ �� �������� ������
        mainPanel.add(aScroller); // ���������� ��������� � ����� ������ �� �������� ������
        mainPanel.add(nextButton); // ���������� ������ �� �������� ������

        nextButton.addActionListener(new NextCardListener()); // ���������� ��������� �� ������

        JMenuBar menuBar = new JMenuBar(); // ���������� � ������������� ������ � ����
        JMenu fileMenu = new JMenu("����"); // ���������� � ������������� ������ ����
        JMenuItem newMenuItem = new JMenuItem("�������"); // ���������� � ������������� ������ ����
        JMenuItem saveMenuItem = new JMenuItem("���������"); // ���������� � ������������� ������ ����

        newMenuItem.addActionListener(new NewMenuListener()); // ���������� ��������� �� ����� ����
        saveMenuItem.addActionListener(new SaveMenuListener()); // ���������� ��������� �� ����� ����

        fileMenu.add(newMenuItem); // ���������� ������ "�������" � ����� ���� "����"
        fileMenu.add(saveMenuItem); // ���������� ������ "���������" � ����� ���� "����"
        menuBar.add(fileMenu); // ���������� ������ ���� "����" � ������ ����
        frame.setJMenuBar(menuBar); // ���������� ������ ���� � �������� ����
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); // ���������� �������� ������ � ����� ��������� ����
        frame.setSize(500, 600); // ����������� ������� ��������� ����
        frame.setVisible(true); // ��������� ��������� ���� (��)
    }

    public class NextCardListener implements ActionListener // ��������� ������ "�����"
    {
        public void actionPerformed(ActionEvent event) // ���������� ������ �� ����������
        {
            QuizCard card = new QuizCard(question.getText(), answer.getText()); // ���������� ������ � ��������
            cardList.add(card); // ���������� �������� � ������
            clearCard(); // �������� ����� ������� � ������
        }
    } // OUT OF INNER CLASS

    public class SaveMenuListener implements ActionListener // ��������� ������ ���� "���������"
    {
        public void actionPerformed(ActionEvent event) // ���������� ������ �� ����������
        {
            QuizCard card = new QuizCard(question.getText(), answer.getText()); // ���������� ������ � ��������
            cardList.add(card); // ���������� �������� � ������

            JFileChooser fileSave = new JFileChooser(); // ���������� � ������������� ����������� ����
            fileSave.showSaveDialog(frame); // ����� ����������� ���� � �������� ���� ���������
            saveFile(fileSave.getSelectedFile()); // ����� ������ ��� ���������� � ����
        }
    } // OUT OF INNER CLASS

    public class NewMenuListener implements ActionListener // ��������� ������ ���� "�������"
    {
        public void actionPerformed(ActionEvent event) // ���������� ������ �� ����������
        {
            cardList.clear(); // �������� ������ ��������
            clearCard(); // �������� ����� ������� � ������
        }
    } // OUT OF INNER CLASS

    private void clearCard() // ����� ��� �������� ����� ������� � ������
    {
        question.setText(""); // �������� ���� ��� �������
        answer.setText(""); // �������� ���� ��� ������
        question.requestFocus(); // ��������� ������ � ������ ���� ��� �������
    }

    private void saveFile(File file) // ����� ��� ������ � ����
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // ������ ������� ������ � ������� ����������

            for(QuizCard card : cardList) // ���� FOREACH
            {
                writer.write(card.getQuestion() + "/"); // ������ ������ �� ���� ��� �������
                writer.write(card.getAnswer() + "\n"); // ������ ������ �� ���� ��� ������
            } // OUT OF LOOP
            writer.close(); // �������� ������
        }
        catch(IOException exception) // ��������� ����������
        {
            System.out.println("�� ������� �������� ������"); // ����� ��������� �� ������
            exception.printStackTrace(); // ����� ���������� �� ������
        } // OUT OF TRY-CATCH
    }
}
