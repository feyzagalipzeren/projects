package booksorterproject;

public class Book {

	private String bookName;
	private int numberOfpages;
	private String writer;
	private int publicationYear;
	
	
	public Book(String bookName, int numberOfpages, String writer, int publicationYear) {
		super();
		this.bookName = bookName;
		this.numberOfpages = numberOfpages;
		this.writer = writer;
		this.publicationYear = publicationYear;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public int getNumberOfpages() {
		return numberOfpages;
	}


	public void setNumberOfpages(int numberOfpages) {
		this.numberOfpages = numberOfpages;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public int getPublicationYear() {
		return publicationYear;
	}


	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}


	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", numberOfpages=" + numberOfpages + ", writer=" + writer
				+ ", publicationYear=" + publicationYear + "]";
	}

	
	
}
