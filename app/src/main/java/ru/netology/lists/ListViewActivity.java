package ru.netology.lists;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Загружаю после того, как отправила поэтому пишу коммент к заданию тут.
//Я вообще не поняла, при чем тут какой-то автор, который упоминается в формулировке задания, и откуда я его должна взять
//Поэтому в итоге я сделала так как поняла. А именно: добавление/удаление изначальных строк-примеров. В общем посмотрите.
//Кажется даже все работает

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

        writeFile();

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

    private void writeFile() {
        String booksList = getString(R.string.large_text);

        try {
            FileOutputStream fileOutput = openFileOutput("list.txt", MODE_PRIVATE);
            fileOutput.write(booksList.getBytes());
            fileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] readFile() {
        String[] arrayContent = null;
        try {
            FileInputStream fileInput = openFileInput("list.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
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

}
