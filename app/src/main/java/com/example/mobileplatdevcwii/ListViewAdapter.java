// Iuri Insali S1504620

package com.example.mobileplatdevcwii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private List<Roadwork> originalData;
    private List<Roadwork> filteredData = null;
    private LayoutInflater layoutInflater;
    Context context;
    int layoutId;
    ValueFilter valueFilter;

    public ListViewAdapter (Context context, int viewId, List<Roadwork> RoadworkList)
    {
        this.context = context;
        this.layoutId = viewId;
        this.filteredData = RoadworkList;
        this.originalData = RoadworkList;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Roadwork getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = View.inflate(context, layoutId, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.locationText);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (filteredData == null)
        {
            viewHolder.textView.setText("There is currently no information available");
        }
        else
        {
            viewHolder.textView.setText(filteredData.get(position).getTitle());
        }


        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        if (valueFilter == null)
        {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter
    {
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if (charSequence !=null && charSequence.length() >0) {
                List<Roadwork> filteredList = new ArrayList<>();
                for (int i = 0; i < filteredData.size(); i++) {

                    if (filteredData.get(i).getTitle().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                        Roadwork item = filteredData.get(i);
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            else
            {
                results.count = filteredData.size();
                results.values = filteredData;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results)
        {
            filteredData = (ArrayList<Roadwork>)results.values;
            notifyDataSetChanged();
        }
    }
}

