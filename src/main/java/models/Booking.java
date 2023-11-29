package models;

public class Booking {

    public String fromDate;
    public String toDate;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;

    public Booking(String fromDate, String toDate, String firstName, String lastName, String email, String phoneNumber)
    {

        this.fromDate = fromDate;
        this.toDate = toDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = email;
        this.phoneNumber = phoneNumber;

    }
}
