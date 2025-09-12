package contactBook;

public class ContactIterator implements Iterator<Contact>{
    
    private int size;
    private Contact[] contacts;
    private int currentContact;

    public ContactIterator(Contact[] contacts, int size) {
        this.size = size;
        this.contacts = contacts;
        currentContact = 0;
    }
    
    @Override
    public boolean hasNext() {
        return (size != 0) && (currentContact >= 0 ) && (currentContact < size);
    }

    //Pre: hasNext()
    @Override
    public Contact next() {
        return contacts[currentContact++];
    }

    @Override
    public void rewind() {
        currentContact = 0;
    }
    
}
