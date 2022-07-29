package com.edgar.lytodifferentcolor.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.edgar.lytodifferentcolor.R;
import com.edgar.lytodifferentcolor.Object.ColorPointObject;
import com.edgar.lytodifferentcolor.Object._ImgCustom;

import java.util.ArrayList;
import java.util.List;

public class ColorPointAdapter extends ArrayAdapter<ColorPointObject> {
    private Context context;
    private int resource;
    private ArrayList<ColorPointObject> arrColorPoint;


    public ColorPointAdapter(@NonNull Context context, int resource, @NonNull List<ColorPointObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrColorPoint = new ArrayList<>(objects);
    }

    @Override
    public int getCount() {
        return arrColorPoint.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }

        if(arrColorPoint.size() > 0) {
            ColorPointObject colorPointObject =  arrColorPoint.get(position);
            _ImgCustom imgCustom = convertView.findViewById(R.id.ivPointColor);
            imgCustom.setColorFilter(Color.parseColor(colorPointObject.idColor));
        }

        return convertView;
    }

    public void update(ArrayList<ColorPointObject> arrColorPoint) {
        this.arrColorPoint.clear(); // clear dữ liệu bảng màu hiện tại
        this.arrColorPoint.addAll(arrColorPoint); // đổ dữ liệu đã được update mới vào
        this.notifyDataSetChanged(); // gọi hàm cập nhật lại adapter
    }
}
