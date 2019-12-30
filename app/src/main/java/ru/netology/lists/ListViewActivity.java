package ru.netology.lists;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
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


public class ListViewActivity extends AppCompatActivity {
    private List<Map<String, String>> content = new ArrayList();
    private BaseAdapter listContentAdapter;
    private int i = 0;
    private String[] arrayContent;
    private ListView list;
    private ImageButton delete;

    private final String TEXT = "SAVED_TEXT";
    private final String EMPTY = ":(";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        writeFile();

        arrayContent = readFile();



        list = findViewById(R.id.list);
        delete = findViewById(R.id.delete);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap();
                map.put("text", arrayContent[i]);
                map.put("count", Integer.toString(arrayContent[i].length()));
                content.add(map);
                listContentAdapter.notifyDataSetChanged();
                delete.setOnClickListener(deleteOnClickListener);
                i++;
            }
        });



        /*findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.remove(i);
                listContentAdapter.notifyDataSetChanged();
            }
        });*/


        //list.setOnItemClickListener(listViewOnItemClickListener);

        //prepareContent();

        listContentAdapter = createAdapter(content);
        list.setAdapter(listContentAdapter);

    }


    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        return new SimpleAdapter(this, values,
                R.layout.list, new String[]{"text", "count"}, new int[]{R.id.first, R.id.second});

    }

    @NonNull
    private void prepareContent() {
        String[] arrayContent = readFile();

        for (int i = 0; i < arrayContent.length; i++) {
            Map<String, String> map = new HashMap();
            map.put("text", arrayContent[i]);
            map.put("count", Integer.toString(arrayContent[i].length()));
            content.add(map);
        }
    }


    AdapterView.OnItemClickListener listViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            content.remove(i);
            listContentAdapter.notifyDataSetChanged();
        }
    };

    private void writeFile(){
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

    private String[] readFile(){
        String[] arrayContent = null;
        try {
            FileInputStream fileInput = openFileInput("list.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer strBuffer = new StringBuffer();
            String lines;
            while((lines = buffer.readLine()) != null){
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

    View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            content.remove(i);
            listContentAdapter.notifyDataSetChanged();
        }
    };
}
