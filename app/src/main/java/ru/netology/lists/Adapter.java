package ru.netology.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Adapter extends BaseAdapter {
    private List<Map<String, String>> content;
    private LayoutInflater inflater;

    public Adapter(Context context, List<Map<String, String>> content) {
        if (content == null) {
            this.content = new ArrayList<>();
        } else {
            this.content = content;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < content.size()) {
            return content.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list, parent, false);
        }

        Map<String, String> map = content.get(position);

        TextView first = view.findViewById(R.id.first);
        first.setText(map.get("text"));

        TextView second = view.findViewById(R.id.second);
        second.setText(map.get("count"));

        ImageButton delete = view.findViewById(R.id.delete);
        delete.setTag(String.valueOf(position));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

}
