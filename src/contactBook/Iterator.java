package contactBook;

public interface Iterator<E>
{
	boolean hasNext();

    //Pre: hasNext()
    E next();
    
    void rewind();
}
