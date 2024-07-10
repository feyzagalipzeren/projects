package booksorterproject;

import java.util.Comparator;

public class OrderByNumberOfPage implements Comparator <Book>{

	@Override
	public int compare(Book b1, Book b2) {
		// TODO Auto-generated method stub
		return b1.getNumberOfpages() - b2.getNumberOfpages();
	}

}
