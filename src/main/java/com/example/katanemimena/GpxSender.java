package com.example.katanemimena;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GpxSender {
    public static String user;
    public static double totalD;
    public static double totalE;
    public static double totalT;
    public static double averageS;
    public static double totalDT;
    public static double totalET;
    public static double totalTT;
    public static double totalAT;
    public static double totalDTA;
    public static double totalETA;
    public static double totalTTA;
    public static double totalATA;
    public static void sendGpxFile(InputStream inputStream, String serverIp, int serverPort) {
        Socket socket = null;
        try {
            socket = new Socket(serverIp, serverPort);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, bytesRead);
            }

            byteStream.flush();
            byte[] fileContent = byteStream.toByteArray();

            out.writeObject(fileContent);
            out.flush();

            Object inputObject = in.readObject();
            user = (String) inputObject;
            inputObject = in.readObject();
            totalD = (double) inputObject;
            inputObject = in.readObject();
            totalE = (double) inputObject;
            inputObject = in.readObject();
            totalT = (double) inputObject;
            inputObject = in.readObject();
            averageS = (double) inputObject;
            Log.i("Results",user + " your results for this run are: ");
            Log.i("Results","Distance: " + totalD + " km");
            Log.i("Results","Elevation: " + totalE + " m");
            Log.i("Results","Time: " + totalT + " minutes");
            Log.i("Results","Average speed: " + averageS + " km/h");

            inputObject = in.readObject();
            totalDT = (double) inputObject;
            inputObject = in.readObject();
            totalET = (double) inputObject;
            inputObject = in.readObject();
            totalTT = (double) inputObject;
            inputObject = in.readObject();
            totalAT = (double) inputObject;

            inputObject = in.readObject();
            totalDTA = (double) inputObject;
            inputObject = in.readObject();
            totalETA = (double) inputObject;
            inputObject = in.readObject();
            totalTTA = (double) inputObject;
            inputObject = in.readObject();
            totalATA = (double) inputObject;

            Log.i("Results",user + " your total statistics compared to others: ");
            Log.i("Results","Your average distance: " + totalDT + " km");
            Log.i("Results","Your average elevation: " + totalET + " m");
            Log.i("Results","Your average time: " + totalTT + " minutes");
            Log.i("Results","Your average speed for all runs: " + totalAT + " km/h");

            Log.i("Results","--------------------------");

            Log.i("Results","All users' average distance: " + totalDTA + " km");
            Log.i("Results","All users' elevation: " + totalETA + " m");
            Log.i("Results","All users' time: " + totalTTA + " minutes");
            Log.i("Results","All users' speed for all runs: " + totalATA + " km/h");




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}