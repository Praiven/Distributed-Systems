package com.example.katanemimena;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.OpenableColumns;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.katanemimena.R.id.menu_one;
import static com.example.katanemimena.R.id.menu_two;

public class SecondActivity extends AppCompatActivity {

    Button btn1;

    private ImageView imageView3;
    private static final int READ_REQUEST_CODE = 42;
    private String selectedFilePath;
    private InputStream inputStream;
    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        btn1 = (Button) findViewById(R.id.btn1);
        imageView3 = (ImageView) findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    public void openPopUpMenuBar(View view) {
        PopupMenu popupMenu = new PopupMenu(SecondActivity.this,btn1);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if(id == menu_one) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,READ_REQUEST_CODE) ;
                } else if(id == menu_two) {
                    Toast.makeText(SecondActivity.this,menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(SecondActivity.this,BarChartActivity.class);
                    startActivity(I);                }
                else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                selectedFilePath = getAddressFromUri(uri);

                if (selectedFilePath != null) {
                    // Αποθηκεύστε το InputStream και το Uri
                    try {
                        inputStream = getContentResolver().openInputStream(uri);
                        fileUri = uri;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(SecondActivity.this, "Σφάλμα: Αρχείο δεν βρέθηκε", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Αποστολή του αρχείου GPX στον άλλο υπολογιστή
                    String serverIp = "192.168.1.98";
                    int serverPort = 1234;
                    sendGpxFile(inputStream, serverIp, serverPort);

                    Toast.makeText(SecondActivity.this, "Επιλέξατε το αρχείο: " + uri.toString(), Toast.LENGTH_SHORT).show();

                    // Εμφάνιση της διεύθυνσης του αρχείου GPX
                    String address = uri.toString();
                    Log.d("SecondActivity", "Διεύθυνση αρχείου: " + address);
                }

            } else {
                Toast.makeText(SecondActivity.this, "Αδυναμία εύρεσης του αρχείου GPX", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getAddressFromUri(Uri uri) {
        String address = "";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex != -1) {
                address = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return address;
    }

    private void sendGpxFile(final InputStream inputStream, final String serverIp, final int serverPort) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GpxSender.sendGpxFile(inputStream, serverIp, serverPort);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SecondActivity.this, "Το αρχείο GPX στάλθηκε με επιτυχία.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        thread.start();
    }
}