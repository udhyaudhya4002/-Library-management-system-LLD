import java.util.*;

// Book Class
class Book {
    private String title;
    private String author;
    private String isbn;
    private int copies;

    public Book(String title, String author, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getCopies() {
        return copies;
    }

    public void borrowCopy() throws BookNotAvailableException {
        if (copies > 0) {
            copies--;
        } else {
            throw new BookNotAvailableException("No copies available for: " + title);
        }
    }

    public void returnCopy() {
        copies++;
    }

    @Override
    public String toString() {
        return title + " by " + author + " | ISBN: " + isbn + " | Copies left: " + copies;
    }
}

// Library Class
class Library {
    private List<Book> books = new ArrayList<>();

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added Book: " + book);
    }

    // Borrow a book by ISBN
    public void borrowBook(String isbn) throws BookNotAvailableException {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.borrowCopy();
                System.out.println("Borrowed Book: " + book.getTitle());
                return;
            }
        }
        throw new BookNotAvailableException("Book not found: " + isbn);
    }

    // Return a book by ISBN
    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.returnCopy();
                System.out.println("Returned Book: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book not found: " + isbn);
    }

    // Print Catalog of all books
    public void printCatalog() {
        System.out.println("\nLibrary Catalog:");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

// User Class
class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void borrowBook(Library library, String isbn) {
        try {
            library.borrowBook(isbn);
            System.out.println(name + " successfully borrowed the book.\n");
        } catch (BookNotAvailableException e) {
            System.out.println(name + " couldn't borrow the book: " + e.getMessage() + "\n");
        }
    }

    public void returnBook(Library library, String isbn) {
        library.returnBook(isbn);
        System.out.println(name + " returned the book.\n");
    }
}

// Custom Exception
class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

// Main Class
public class LibraryDemo {
    public static void main(String[] args) {

        // Create Library
        Library library = new Library();

        // Create Books
        Book cleanCode = new Book("Clean Code", "Robert C. Martin", "123", 1);
        Book javaPrac = new Book("Effective Java", "Joshua Bloch", "456", 2);

        // Add Books to Library
        library.addBook(cleanCode);
        library.addBook(javaPrac);

        // Create User
        User udhaya = new User("Udhaya");

        // Borrowing process
        udhaya.borrowBook(library, "123"); // OK
        udhaya.borrowBook(library, "123"); // No copies left

        // Return process
        udhaya.returnBook(library, "123"); // Book now available again

        // Try to borrow again
        udhaya.borrowBook(library, "123");

        // Final catalog view
        library.printCatalog();
    }
}
