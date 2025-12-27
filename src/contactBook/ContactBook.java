package contactBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactBook {
    static final int DEFAULT_SIZE = 100;

    private int counter;
    private Contact[] contacts;
    private int currentContact;
    private HashMap<Integer, Integer> phoneNbyUsesN;
    private static Integer ONE;
    private static Integer MINUS_ONE;

    public ContactBook() {
        counter = 0;
        contacts = new Contact[DEFAULT_SIZE];
        currentContact = -1;
        phoneNbyUsesN = new HashMap<>();
        ONE = 1;
        MINUS_ONE = -1;
    }

    //Pre: name != null
    public boolean hasContact(String name) {
        return searchIndex(name) >= 0;
    }

    public int getNumberOfContacts() {
        return counter;
    }

    //Pre: name!= null && !hasContact(name)
    public void addContact(String name, int phone, String email) {
        if (counter == contacts.length)
            resize();
        contacts[counter] = new Contact(name, phone, email);
        counter++;
        decreaseIncreseUses(phone, ONE);
    }

    //Pre: name != null && hasContact(name)
    public void deleteContact(String name) {
        int index = searchIndex(name);
        int phonetmp = contacts[index].getPhone();
        for(int i=index; i<counter; i++)
            contacts[i] = contacts[i+1];
        counter--;
        decreaseIncreseUses(phonetmp, MINUS_ONE);
    }

    //Pre: name != null && hasContact(name)
    public int getPhone(String name) {
        return contacts[searchIndex(name)].getPhone();
    }

    //Pre: name != null && hasContact(name)
    public String getEmail(String name) {
        return contacts[searchIndex(name)].getEmail();
    }

    //Pre: name != null && hasContact(name)
    public void setPhone(String name, int phone) {
        int oldPhone = contacts[searchIndex(name)].getPhone();
        decreaseIncreseUses(oldPhone, MINUS_ONE);
        contacts[searchIndex(name)].setPhone(phone);
        decreaseIncreseUses(phone, ONE);
    }

    //Pre: name != null && hasContact(name)
    public void setEmail(String name, String email) {
        contacts[searchIndex(name)].setEmail(email);
    }

    public String getByPhone(int phone) {
        List<Integer> indexes = searchIndexByPhone(phone);
        if (indexes.isEmpty()) {
            return null;
        }
        int theOldestIndex = indexes.get(0);

        return contacts[theOldestIndex].getName();
    }

    public boolean hasDuplicatedPhone() {
        for(int i : phoneNbyUsesN.values()){
            if(i > 1) return true;
        }
        return false;
    }

    private int searchIndex(String name) {
        int i = 0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (contacts[i].getName().equals(name))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    private List<Integer> searchIndexByPhone(int phone) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            if (contacts[i].getPhone() == phone) {
                result.add(i);
            }
        }
        return result;
    }


    private void resize() {
        Contact[] tmp = new Contact[2*contacts.length];
        if (counter >= 0) {
            System.arraycopy(contacts, 0, tmp, 0, counter);
        }
        contacts = tmp;
    }

    public void initializeIterator() {
        currentContact = 0;
    }

    public boolean hasNext() {
        return (currentContact >= 0 ) && (currentContact < counter);
    }

    //Pre: hasNext()
    public Contact next() {
        return contacts[currentContact++];
    }

    private void decreaseIncreseUses(int phone, int delta) {
        if(phoneNbyUsesN.containsKey(phone)) {
            int uses = phoneNbyUsesN.get(phone);
            if(uses + delta <= 0) {
                phoneNbyUsesN.remove(phone);
            }
            phoneNbyUsesN.put(phone, uses + delta);
        } else {
            phoneNbyUsesN.put(phone, delta);
        }
    }


}
