package ru.netology.lists;


import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListViewActivity extends AppCompatActivity {
    private List<Map<String, String>> content = new ArrayList();
    private int i = 0;
    private String[] arrayContent;
    private ListView list;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        textSave();

        arrayContent = readFile();

        list = findViewById(R.id.list);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap();
                map.put("text", arrayContent[i]);
                map.put("count", Integer.toString(arrayContent[i].length()));
                content.add(map);
                adapter = new Adapter(ListViewActivity.this, content);
                list.setAdapter(adapter);
                i++;
            }
        });


    }

    private String[] readFile() {
        String[] arrayContent = null;
        try {
            FileInputStream fileInput = openFileInput("list.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuilder strBuffer = new StringBuilder();
            String lines;
            while ((lines = buffer.readLine()) != null) {
                strBuffer.append(lines);
            }
            arrayContent = strBuffer.toString().split("~");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayContent;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public void SaveFile(String filePath, String FileContent) {
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(FileContent);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void textSave() {
        String folderName = "temp/codeFolder";
        String fileName = "list.txt";
        String booksList = getString(R.string.large_text);

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + folderName
                + "/" + fileName;
        if (isExternalStorageWritable()) {
            SaveFile(fullPath, booksList);
        }
    }

}
