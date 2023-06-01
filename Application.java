import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;

public class Application extends Thread {

    public static void app() {

            try {
                // Open a file chooser dialog to select the GPX file
                JFileChooser chooser = new JFileChooser();
                int result;
                File file = null;

                // Create a TCP socket and connect to the Master class
                Socket socket = new Socket("localhost", 1234);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                DecimalFormat df = new DecimalFormat("#.##");
                Scanner scanner = new Scanner(System.in);
                int input = 1;

                while (input ==1) {
                    // Read the file content
                    chooser.setDialogTitle("Select GPX file");
                    result = chooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        file = chooser.getSelectedFile();
                    }
                    assert file != null;

                    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                    Files.copy(file.toPath(), byteOutputStream);
                    byte[] fileContent = byteOutputStream.toByteArray();

                    // Send the file content to the Master class using an ObjectOutputStream
                    out.writeObject(fileContent);
                    out.flush();

                    file = null;
                    Arrays.fill(fileContent, (byte) 0);

                    Object inputObject = in.readObject();
                    String user = (String) inputObject;
                    inputObject = in.readObject();
                    double totalD = (double) inputObject;
                    inputObject = in.readObject();
                    double totalE = (double) inputObject;
                    inputObject = in.readObject();
                    double totalT = (double) inputObject;
                    inputObject = in.readObject();
                    double averageS = (double) inputObject;
                    System.out.println(user + " your results for this run are: ");
                    System.out.println("Distance: " + df.format(totalD) + " km");
                    System.out.println("Elevation: " + df.format(totalE) + " m");
                    System.out.println("Time: " + df.format(totalT) + " minutes");
                    System.out.println("Average speed: " + df.format(averageS) + " km/h");

                    System.out.println("\n ===================================== \n");

                    inputObject = in.readObject();
                    double totalDT = (double) inputObject;
                    inputObject = in.readObject();
                    double totalET = (double) inputObject;
                    inputObject = in.readObject();
                    double totalTT = (double) inputObject;
                    inputObject = in.readObject();
                    double totalAT = (double) inputObject;
                    System.out.println(user + " your total statistics are: ");
                    System.out.println("Total distance: " + df.format(totalDT) + " km");
                    System.out.println("Total elevation: " + df.format(totalET) + " m");
                    System.out.println("Total time: " + df.format(totalTT) + " minutes");
                    System.out.println("Total average speed: " + df.format(totalAT) + " km/h\n");

                    System.out.print("1. For GPX file \n2. For statistics \n3. For Exit \n");
                    input = scanner.nextInt();
                    while (input == 2) {
                        System.out.println(user + " your results for this run are: ");
                        System.out.println("Distance: " + df.format(totalD) + " km");
                        System.out.println("Elevation: " + df.format(totalE) + " m");
                        System.out.println("Time: " + df.format(totalT) + " minutes");
                        System.out.println("Average speed: " + df.format(averageS) + " km/h");
                        System.out.println("\n ===================================== \n");
                        System.out.println(user + " your total statistics are: ");
                        System.out.println("Total distance: " + df.format(totalDT) + " km");
                        System.out.println("Total elevation: " + df.format(totalET) + " m");
                        System.out.println("Total time: " + df.format(totalTT) + " minutes");
                        System.out.println("Total average speed: " + df.format(totalAT) + " km/h\n");
                        System.out.print("1. For GPX file \n 2. For statistics \n 3. For Exit \n");
                        input = scanner.nextInt();
                    }
                    out.writeInt(input);
                    out.flush();
                }
                // Close the output stream and the socket
                out.close();
                socket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

    }
    public static void main(String[] args) throws ParseException, IOException {
        Application.app();
    }

}