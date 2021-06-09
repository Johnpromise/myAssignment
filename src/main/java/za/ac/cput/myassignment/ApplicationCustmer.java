/*
Name Johnpromise A Ononuju
Student# (220505152)
my Customer write output file class
*/
package za.ac.cput.myassignment;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John hp
 */
public class ApplicationCustmer {

    FileWriter fwriter;

    FileInputStream fStream;
    ObjectInputStream objectStream;

    public void openFile() {
        try {
            fwriter = new FileWriter(new File("customerOutFile.txt"));

        } catch (IOException ioe) {
            System.out.println( ioe.getMessage());
            System.exit(1);
        }
    }
// inserting customer object into array
    private ArrayList<Customer> customersList() {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            fStream = new FileInputStream("stakeholder.ser");
            objectStream = new ObjectInputStream(fStream);

            while (true) {
                Object object = objectStream.readObject();

                if (object instanceof Customer) {
                    customers.add((Customer) object);
                }
            }

        } catch (EOFException eofe) {
            System.out.println(eofe.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);

        } finally {

            try {
                fStream.close();
                objectStream.close();
                System.out.println("File Closed");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ApplicationCustmer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
 //sorting the arrray
        if (!customers.isEmpty()) {
           
            Collections.sort(customers, (Customer c1, Customer c2) -> c1.getStHolderId().compareTo(c2.getStHolderId())
            );
        }

        return customers;
    }

    private int rentCars() {
        int rentCar = 0;

        for (int i = 0; i < customersList().size(); i++) {
            if (customersList().get(i).getCanRent()) {
                rentCar += 1;
            }
        }

        return rentCar;
    }

    private int canNotRentCar() {
        int cantRent = 0;

        for (int i = 0; i < customersList().size(); i++) {

            if (!customersList().get(i).getCanRent()) {
                cantRent += 1;
            }
        }
        return cantRent;
    }

    private void customerOutfile() {

        try {
            
            fwriter.write("============================== CUSTOMERS =============================================================\n");
           
            fwriter.write("ID\t Name\t Surname\t   Date Of Birth\t  Age\t\n ");
            
            fwriter.write("------------------------------------------------------------------------------------------------------\n");
            
            for (int i = 0; i < customersList().size(); i++) {
                fwriter.write(
                        customersList().get(i).getStHolderId() + "\t"
                        + customersList().get(i).getFirstName() + "\t"
                        + customersList().get(i).getSurName() + "\t"
                        + formatDate(customersList().get(i).getDateOfBirth()) + "\t"
                        + calculateAge(customersList().get(i).getDateOfBirth()) + "\n"
                );
            }

            fwriter.write("\nNumber of customers who can rent:  "
                    + rentCars());

            fwriter.write(
                    "\nNumber of customers who cannot rent:  "
                    + canNotRentCar());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String formatDate(String dob) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate changeDob = LocalDate.parse(dob);
        return changeDob.format(dtf);
    }
//calculating age of customer
    private int calculateAge(String dob) {
        LocalDate changeDob = LocalDate.parse(dob);
        int dobYear = changeDob.getYear();

        ZonedDateTime currentDate = ZonedDateTime.now();
        int currentYear = currentDate.getYear();

        return currentYear - dobYear;
    }

    public void closeFile() {
        try {

            fwriter.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        ApplicationCustmer objectOutput = new ApplicationCustmer();

        objectOutput.openFile();
        objectOutput.customerOutfile();
        objectOutput.closeFile();

    }
}
