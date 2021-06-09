/*
Name Johnpromise A Ononuju
Student# (220505152)
my Supplierwrite output file class
*/
package za.ac.cput.myassignment;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author John hp
 */
public class ApplicationSupplier {
    
    FileWriter fileW;

    FileInputStream FileIS;
    ObjectInputStream ObjStream;

    public void openFile() {
        try {
            fileW = new FileWriter(new File("supplierOutFile.txt"));//opening file

        } catch (IOException ioe) {
            System.out.println( ioe.getMessage());
            System.exit(1);
        }
    }
//placing supplier object into array
    private ArrayList<Supplier> suppliersArrayList() {
        ArrayList<Supplier> suppliers = new ArrayList<>();

        try {
            FileIS = new FileInputStream("stakeholder.ser");
            ObjStream = new ObjectInputStream(FileIS);

            while (true) {
                Object object = ObjStream.readObject();

                if (object instanceof Supplier) {
                    suppliers.add((Supplier) object);
                }
            }

        } catch (EOFException eofe) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                FileIS.close();//closing the file
                ObjStream.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
//sorting the array
        if (!suppliers.isEmpty()) {

            Collections.sort(suppliers, (Supplier s1, Supplier s2) -> s1.getName().compareTo(s2.getName())
            );
        }

        return suppliers;
    }

    private void supplierOutFile() {

        try {
            fileW.write("=========================================== Supliers================================================================\n");
            fileW.write("ID\t Name\t\t Prod Type\t Description\n");

            fileW.write("-----------------------------------------------------------------------------------------------------------------------------------\n");
            for (int i = 0; i < suppliersArrayList().size(); i++) {
                fileW.write(suppliersArrayList().get(i).getStHolderId() + "\t" + suppliersArrayList().get(i).getName() + "\t"
                        + suppliersArrayList().get(i).getProductType() + "\t\t"
                        + suppliersArrayList().get(i).getProductDescription() + "\n"
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeFile() {
        try {

            fileW.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
       ApplicationSupplier objectOutput = new ApplicationSupplier();

       
        objectOutput.openFile();
        objectOutput.supplierOutFile();
        objectOutput.closeFile();
    }
}
