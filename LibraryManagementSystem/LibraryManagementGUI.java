import java.util.ArrayList;
import java.util.Scanner;

class Book {
    String title;
    String author;
    boolean isAvailable;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return title + " by " + author + " [" + (isAvailable ? "Available" : "Borrowed") + "]";
    }
}

public class LibraryManagementTerminal {
    private final ArrayList<Book> books = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n====== Library Management System ======");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Remove Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> addBook();
                case 2 -> displayBooks();
                case 3 -> searchBook();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> removeBook();
                case 7 -> {
                    System.out.println("Exiting Library System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();

        if (!title.isEmpty() && !author.isEmpty()) {
            books.add(new Book(title, author));
            System.out.println("Book added: " + title);
        } else {
            System.out.println("Both title and author are required.");
        }
    }

    private void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("\n--- All Books ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    private void searchBook() {
        System.out.print("Enter title or author to search: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Please enter a title or author.");
            return;
        }

        for (Book b : books) {
            if (b.title.toLowerCase().contains(query.toLowerCase()) ||
                b.author.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("Found: " + b);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private void borrowBook() {
        System.out.print("Enter book title to borrow: ");
        String title = scanner.nextLine().trim();

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                if (b.isAvailable) {
                    b.isAvailable = false;
                    System.out.println("Borrowed: " + b.title);
                } else {
                    System.out.println("Book already borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private void returnBook() {
        System.out.print("Enter book title to return: ");
        String title = scanner.nextLine().trim();

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                if (!b.isAvailable) {
                    b.isAvailable = true;
                    System.out.println("Returned: " + b.title);
                } else {
                    System.out.println("Book was not borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    private void removeBook() {
        System.out.print("Enter book title to remove: ");
        String title = scanner.nextLine().trim();

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                books.remove(b);
                System.out.println("Removed: " + title);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public static void main(String[] args) {
        new LibraryManagementTerminal().start();
    }
}
