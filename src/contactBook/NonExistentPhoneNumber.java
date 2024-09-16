package contactBook;

public class NonExistentPhoneNumber extends Exception{

    public NonExistentPhoneNumber() {
        super("Phone Number does not exist");
    }
}
