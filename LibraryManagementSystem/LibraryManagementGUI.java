import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Book {
    String title;
    String author;
    boolean isAvailable;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String toString() {
        return title + " by " + author + " [" + (isAvailable ? "Available" : "Borrowed") + "]";
    }
}

public class LibraryManagementGUI extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();

    private JTextField titleField = new JTextField();
    private JTextField authorField = new JTextField();
    private JComboBox<String> actionBox = new JComboBox<>(new String[]{
        "Add Book", "Display All Books", "Search Book", "Borrow Book", "Return Book", "Remove Book"
    });

    private JLabel messageLabel = new JLabel(" ", SwingConstants.CENTER);

    public LibraryManagementGUI() {
        setTitle("📚 Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fonts and Colors
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        Color lightGray = Color.decode("#F2F2F2");
        Color navyBlue = Color.decode("#2C3E50");
        Color darkGray = Color.decode("#333333");
        Color white = Color.decode("#FFFFFF");
        Color blueButton = Color.decode("#2980B9");
        Color clearGray = Color.decode("#95A5A6");
        Color redButton = Color.decode("#C0392B");

        // Header
        JLabel headerLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(navyBlue);
        headerLabel.setForeground(white);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.setBackground(lightGray);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Book Title:");
        titleLabel.setForeground(darkGray);
        titleLabel.setFont(font);
        inputPanel.add(titleLabel);

        titleField.setBackground(white);
        titleField.setFont(font);
        titleField.setBorder(BorderFactory.createLineBorder(Color.decode("#DDDDDD")));
        inputPanel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setForeground(darkGray);
        authorLabel.setFont(font);
        inputPanel.add(authorLabel);

        authorField.setBackground(white);
        authorField.setFont(font);
        authorField.setBorder(BorderFactory.createLineBorder(Color.decode("#DDDDDD")));
        inputPanel.add(authorField);

        JLabel actionLabel = new JLabel("Choose Action:");
        actionLabel.setForeground(darkGray);
        actionLabel.setFont(font);
        inputPanel.add(actionLabel);

        actionBox.setBackground(white);
        actionBox.setFont(font);
        actionBox.setBorder(BorderFactory.createLineBorder(Color.decode("#DDDDDD")));
        inputPanel.add(actionBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(blueButton);
        submitButton.setForeground(white);
        submitButton.setFont(font);
        inputPanel.add(submitButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(clearGray);
        clearButton.setForeground(white);
        clearButton.setFont(font);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(lightGray);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(redButton);
        exitButton.setForeground(white);
        exitButton.setFont(font);
        bottomPanel.add(exitButton, BorderLayout.NORTH);

        messageLabel.setForeground(darkGray);
        messageLabel.setFont(font);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(messageLabel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        submitButton.addActionListener(e -> performAction());
        clearButton.addActionListener(e -> clearFields());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void performAction() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String action = (String) actionBox.getSelectedItem();

        switch (action) {
            case "Add Book":
                if (!title.isEmpty() && !author.isEmpty()) {
                    books.add(new Book(title, author));
                    messageLabel.setText("✅ Book added: " + title);
                } else {
                    messageLabel.setText("❗ Enter both title and author.");
                }
                break;

            case "Display All Books":
                if (books.isEmpty()) {
                    messageLabel.setText("📚 No books in library.");
                } else {
                    StringBuilder list = new StringBuilder("<html>");
                    for (Book b : books) {
                        list.append(b.toString()).append("<br>");
                    }
                    list.append("</html>");
                    JOptionPane.showMessageDialog(this, list.toString(), "Library Books", JOptionPane.INFORMATION_MESSAGE);
                    messageLabel.setText("📖 Showing all books.");
                }
                break;

            case "Search Book":
                if (!title.isEmpty() || !author.isEmpty()) {
                    for (Book b : books) {
                        if (
                            (!title.isEmpty() && b.title.toLowerCase().contains(title.toLowerCase())) ||
                            (!author.isEmpty() && b.author.toLowerCase().contains(author.toLowerCase()))
                        ) {
                            messageLabel.setText("🔍 Found: " + b);
                            return;
                        }
                    }
                    messageLabel.setText("❌ Book not found.");
                } else {
                    messageLabel.setText("❗ Enter title or author to search.");
                }
                break;

            case "Borrow Book":
                if (!title.isEmpty()) {
                    for (Book b : books) {
                        if (b.title.equalsIgnoreCase(title)) {
                            if (b.isAvailable) {
                                b.isAvailable = false;
                                messageLabel.setText("📕 Borrowed: " + b.title);
                            } else {
                                messageLabel.setText("❌ Book already borrowed.");
                            }
                            return;
                        }
                    }
                    messageLabel.setText("❌ Book not found.");
                } else {
                    messageLabel.setText("❗ Enter title to borrow.");
                }
                break;

            case "Return Book":
                if (!title.isEmpty()) {
                    for (Book b : books) {
                        if (b.title.equalsIgnoreCase(title)) {
                            if (!b.isAvailable) {
                                b.isAvailable = true;
                                messageLabel.setText("✅ Returned: " + b.title);
                            } else {
                                messageLabel.setText("❗ Book was not borrowed.");
                            }
                            return;
                        }
                    }
                    messageLabel.setText("❌ Book not found.");
                } else {
                    messageLabel.setText("❗ Enter title to return.");
                }
                break;

            case "Remove Book":
                if (!title.isEmpty()) {
                    for (Book b : books) {
                        if (b.title.equalsIgnoreCase(title)) {
                            books.remove(b);
                            messageLabel.setText("🗑️ Removed book: " + title);
                            return;
                        }
                    }
                    messageLabel.setText("❌ Book not found to remove.");
                } else {
                    messageLabel.setText("❗ Enter title to remove.");
                }
                break;
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        messageLabel.setText(" ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementGUI().setVisible(true));
    }
}
