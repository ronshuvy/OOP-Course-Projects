
/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 * @author ronshuvy
 * @see Patron
 * @see Book
 */
class Library {

	/* Class constants */
	final static private int LIBRARY_IS_FULL = -1, BOOK_NOT_FOUND = -1, PATRON_NOT_FOUND = -1;

	/* The maximal number of books this library can hold. */
	final int maxBookCapacity;

	/* The maximal number of books this library allows a single patron to borrow at the same time. */
	final int maxBorrowedBooks;

	/* The maximal number of registered patrons this library can handle. */
	final int maxPatronCapacity;

	/* Total number of books in the library inventory */
	int totalBooks;

	/* Total number of registered patrons */
	int totalPatrons;

	/* A list of all books in the library inventory. */
	Book[] books;

	/* A list of all registered patrons of this library. */
	Patron[] patrons;

	/*----=  Constructors  =-----*/

	/**
	 * Creates a new library with the given parameters.
	 * @param maxBookCapacity The maximal number of books this library can hold.
	 * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at
	 * 		the same time.
	 * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
	 */
	Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
		this.maxBookCapacity = maxBookCapacity;
		this.maxBorrowedBooks = maxBorrowedBooks;
		this.maxPatronCapacity = maxPatronCapacity;
		this.books = new Book[maxBookCapacity];
		this.patrons = new Patron[maxPatronCapacity];
	}

	/*----=  Instance Methods  =-----*/

	/**
	 * Adds the given book to this library, if there is place available, and it isn't already in the library.
	 * @param book The book to add to this library.
	 * @return a non-negative id number for the book if there was a spot and the book was successfully added,
	 * 		or if the book was already in the library; a negative number otherwise.
	 */
	int addBookToLibrary(Book book) {
		int bookId = getBookId(book);
        if (bookId >= 0) {
            return bookId; // the book is already in the library!
        }

        if (totalBooks == maxBookCapacity) {
            return LIBRARY_IS_FULL;
        }

		books[totalBooks] = book;
		return totalBooks++;
	}

	/**
	 * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
	 * book is available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
	 * @param bookId The id number of the book to borrow.
	 * @param patronId The id number of the patron that will borrow the book.
	 * @return true if the book was borrowed successfully, false otherwise.
	 */
	boolean borrowBook(int bookId, int patronId) {
        if (!isBookAvailable(bookId) || !isPatronIdValid(patronId) ||
			!patrons[patronId].willEnjoyBook(books[bookId]) ||
            totalBorrowedBooksByPatron(patronId) == maxBorrowedBooks) {
            return false;
        }

		books[bookId].setBorrowerId(patronId);
		return true;
	}

	/*
	 * Returns the total amount of borrowed books by the patron with the given patron id
	 * @param patronId The id number of patron
	 * @return the amount of borrowed books by the patron with the given patron id
	 */
	int totalBorrowedBooksByPatron(int patronId) {
		int countBooks = 0;
        for (int i = 0; i < totalBooks; i++) {
            if (books[i].getCurrentBorrowerId() == patronId) {
                countBooks++;
            }
        }
		return countBooks;
	}

	/**
	 * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
	 * @param book The book for which to find the id number.
	 * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
	 */
	int getBookId(Book book) {
        for (int i = 0; i < totalBooks; i++) {
            if (books[i] == book) {
                return i; // the book is already in the library
            }
        }
		return BOOK_NOT_FOUND;
	}

	/*
	 * Returns the non-negative id number of the given patron if he is registered to this library,
	 * -1 otherwise.
	 * @param patron The patron for which to find the id number.
	 * @return a non-negative id number of the given patron if he is registered to this library, -1
	 * otherwise.
	 */
	int getPatronId(Patron patron) {
        for (int i = 0; i < totalPatrons; i++) {
            if (patrons[i] == patron) {
                return i; // the book is already in the library
            }
        }
		return PATRON_NOT_FOUND;
	}

	/**
	 * Returns true if the book with the given id is available, false otherwise.
	 * @param bookId The id number of the book to check.
	 * @return true if the book with the given id is available, false otherwise.
	 */
	boolean isBookAvailable(int bookId) {
		return isBookIdValid(bookId) && books[bookId].getCurrentBorrowerId() == PATRON_NOT_FOUND;
	}

	/**
	 * Returns true if the given number is an id of some book in the library, false otherwise.
	 * @param bookId The id to check.
	 * @return true if the given number is an id of some book in the library, false otherwise.
	 */
	boolean isBookIdValid(int bookId) {
		return (bookId >= 0) && (bookId < totalBooks);
	}

	/**
	 * Returns true if the given number is an id of a patron in the library, false otherwise.
	 * @param patronId The id to check.
	 * @return true if the given number is an id of a patron in the library, false otherwise.
	 */
	boolean isPatronIdValid(int patronId) {
		return (patronId >= 0) && (patronId < totalPatrons);
	}

	/**
	 * Registers the given Patron to this library, if there is a spot available.
	 * @param patron The patron to register to this library.
	 * @return a non-negative id number for the patron if there was a spot and the patron was successfully
	 * 		registered or if the patron was already registered. a negative number otherwise.
	 */
	int registerPatronToLibrary(Patron patron) {
		int patronId = getPatronId(patron);
        if (patronId >= 0) {
            return patronId; // the patron is already registered
        }

        if (totalPatrons == maxPatronCapacity) {
            return LIBRARY_IS_FULL; // registered patron capacity is full
        }

		patrons[totalPatrons] = patron;
		return totalPatrons++;
	}

	/**
	 * Return the given book.
	 * @param bookId The id number of the book to return.
	 */
	void returnBook(int bookId) {
        if (isBookIdValid(bookId) && books[bookId].getCurrentBorrowerId() != PATRON_NOT_FOUND) {
            books[bookId].returnBook();
        }
	}

	/**
	 * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
	 * will enjoy, if any such exist.
	 * @param patronId The id number of the patron to suggest the book to.
	 * @return The available book the patron with the given ID will enjoy the most. Null if no book is
	 * 		available.
	 */
	Book suggestBookToPatron(int patronId) {
        if (!isPatronIdValid(patronId)) {
            return null;
        }

		Book suggestedBook = null;
		int topBookScore = 0;
		for (int i = 0; i < totalBooks; i++) {
			Book currentBook = books[i];
			if (isBookAvailable(i) &&
				patrons[patronId].willEnjoyBook(currentBook) &&
				patrons[patronId].getBookScore(currentBook) > topBookScore) {
				suggestedBook = currentBook;
				topBookScore = patrons[patronId].getBookScore(currentBook);
			}
		}
		return suggestedBook;
	}

}
