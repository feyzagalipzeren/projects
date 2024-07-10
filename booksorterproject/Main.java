package booksorterproject;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TreeSet<Book> bookSet = new TreeSet<Book>(new OrderByName());
		
		bookSet.add(new Book("CodePlus", 200, "Kleurrijk", 2010));
		bookSet.add(new Book("Snow", 150, "Orhan Pamuk", 1980));
		bookSet.add(new Book("Delfste", 70, "Delf University", 2015));
		bookSet.add(new Book("Nederlands Ingang", 210, "Groningen University", 2020));
		bookSet.add(new Book("TaalCompleet", 170, "Kleurrijk", 2024));
		
		System.out.println("\n***************\n" + "Sorting by publication year"+ "\n***************\n");
		
		for(Book book : bookSet)
		{
			System.out.println(book.toString());
		}
		
		System.out.println("\n***************\n" + "Sorting by number of page"+ "\n***************\n");
		
		
		 Set<Book> sortPageBookSet = new TreeSet<>(new OrderByNumberOfPage());
		 
		 sortPageBookSet.add(new Book("CodePlus", 200, "Kleurrijk", 2010));
		 sortPageBookSet.add(new Book("Snow", 150, "Orhan Pamuk", 1980));
		 sortPageBookSet.add(new Book("Delfste", 70, "Delf University", 2015));
		 sortPageBookSet.add(new Book("Nederlands Ingang", 210, "Groningen University", 2020));
		 sortPageBookSet.add(new Book("TaalCompleet", 170, "Kleurrijk", 2024));
		  
		 Iterator bookIterator = sortPageBookSet.iterator();
		 
		 while (bookIterator.hasNext())
		 {
			 System.out.println(bookIterator.next().toString());
		 }

	}

}
