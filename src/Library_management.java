import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Library_management extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel label1, label2, label3, label4, label5, label6, label7, label8;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton, searchButton;
    private JComboBox<String> searchCriteria;
    private JPanel panel;
    private ArrayList<String[]> books = new ArrayList<>();
    private DefaultTableModel tableModel;

    private static final String DATA_FILE = "library_data.txt";

    public Library_management() {
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");
        label8 = new JLabel("Student ID");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);
        textField8 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        searchButton = new JButton("Search");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);
        searchButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(label7);
        panel.add(textField7);
        panel.add(label8);
        panel.add(textField8);
        panel.add(new JLabel());
        panel.add(searchButton);

        
        String[] searchOptions = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies", "Student ID"};
        searchCriteria = new JComboBox<>(searchOptions);
        panel.add(searchCriteria);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(exitButton);

        getContentPane().add(panel);
        setVisible(true);

        
        loadData();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
           
            String[] book = new String[8]; 
            book[0] = textField1.getText();
            book[1] = textField2.getText();
            book[2] = textField3.getText();
            book[3] = textField4.getText();
            book[4] = textField5.getText();
            book[5] = textField6.getText();
            book[6] = textField7.getText();
            book[7] = textField8.getText(); 
            books.add(book);
            JOptionPane.showMessageDialog(this, "Book added successfully");
            clearFields();
            saveData(); 
        } else if (e.getSource() == viewButton) {
           
            Object[][] data = new Object[books.size()][8]; 
            for (int i = 0; i < books.size(); i++) {
                data[i] = books.get(i);
            }
            tableModel = new DefaultTableModel(data, getColumnNames());
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("View Books");
            frame.getContentPane().add(scrollPane);
            frame.setSize(800, 400);
            frame.setVisible(true);
        } else if (e.getSource() == editButton) {
            
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
        	for (int i = 0; i < books.size(); i++) {
        	if (books.get(i)[0].equals(bookID)) {
        	String[] book = new String[7];
        	book[0] = bookID;
        	book[1] = textField2.getText();
        	book[2] = textField3.getText();
        	book[3] = textField4.getText();
        	book[4] = textField5.getText();
        	book[5] = textField6.getText();
        	book[6] = textField7.getText();
        	books.set(i, book);
        	JOptionPane.showMessageDialog(this, "Book edited successfully");
        	clearFields();
        	return;
        	}
        	}
        	JOptionPane.showMessageDialog(this, "Book not found");
        } else if (e.getSource() == deleteButton) {
            
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
        	for (int i = 0; i < books.size(); i++) {
        	if (books.get(i)[0].equals(bookID)) {
        	books.remove(i);
        	JOptionPane.showMessageDialog(this, "Book deleted successfully");
        	clearFields();
        	return;
        	}
        	}
        	JOptionPane.showMessageDialog(this, "Book not found");
            String selectedCriteria = (String) searchCriteria.getSelectedItem();
            String searchText = JOptionPane.showInputDialog(this, "Enter " + selectedCriteria + " to delete:");
            deleteBook(selectedCriteria, searchText);
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == searchButton) {
            
            String selectedCriteria = (String) searchCriteria.getSelectedItem();
            String searchText = JOptionPane.showInputDialog(this, "Enter " + selectedCriteria + " to search:");
            searchBook(selectedCriteria, searchText);
        }
    }
    private void deleteBook(String criteria, String searchText) {
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            String[] book = books.get(i);
            if (book[getCriteriaIndex(criteria)].equalsIgnoreCase(searchText)) {
                found = true;
                books.remove(i);
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                clearFields();
                saveData();
                return;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(this, "No match found for " + criteria + " '" + searchText + "'");
        }
    }
    private void searchBook(String criteria, String searchText) {
        boolean found = false;
        StringBuilder searchResult = new StringBuilder();
        for (String[] book : books) {
            if (book[getCriteriaIndex(criteria)].equalsIgnoreCase(searchText)) {
                found = true;
                for (int i = 0; i < book.length; i++) {
                    searchResult.append(getLabelHeading(i)).append(": ").append(book[i]).append("\n");
                }
                searchResult.append("\n");
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(this, "Search Result:\n\n" + searchResult.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No match found for " + criteria + " '" + searchText + "'");
        }
    }

    private int getCriteriaIndex(String criteria) {
        switch (criteria) {
            case "Book ID":
                return 0;
            case "Book Title":
                return 1;
            case "Author":
                return 2;
            case "Publisher":
                return 3;
            case "Year of Publication":
                return 4;
            case "ISBN":
                return 5;
            case "Number of Copies":
                return 6;
            case "Student ID":
                return 7;
            default:
                return -1;
        }
    }

    private String getLabelHeading(int index) {
        switch (index) {
            case 0:
                return "Book ID";
            case 1:
                return "Book Title";
            case 2:
                return "Author";
            case 3:
                return "Publisher";
            case 4:
                return "Year of Publication";
            case 5:
                return "ISBN";
            case 6:
                return "Number of Copies";
            case 7:
                return "Student ID";
            default:
                return "";
        }
    }


    private String[] getColumnNames() {
        return new String[]{"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies", "Student ID"};
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
        textField8.setText(""); 
    }

    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (String[] book : books) {
                writer.println(String.join(",", book));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookData = line.split(",");
                books.add(bookData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Library_management();
    }
}
