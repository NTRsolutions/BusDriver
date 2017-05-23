package com.project.verbosetech.busdriverapp.Other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.verbosetech.busdriverapp.Models.Student;
import com.project.verbosetech.busdriverapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.project.verbosetech.busdriverapp.R.drawable.ic_undo_grey_24dp;
import static com.project.verbosetech.busdriverapp.R.drawable.picked_droped_bckgrnd;
import static com.project.verbosetech.busdriverapp.R.drawable.undo_button_background;

/**
 * Created by this pc on 22-05-17.
 */

public class BusRecycleGrid extends RecyclerView.Adapter<BusRecycleGrid.MyHolder>{

    public RecyclerView re;
    private List<Student> dataSet ;
    public Context context=null;
    VenueAdapterClickCallbacks venueAdapterClickCallbacks;
    PrefManager pref;

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView class_section;
        TextView address;
        TextView father_name;
        TextView mother_name;
        TextView father_contact;
        TextView mother_contact;
        ImageView image;
        RelativeLayout expandArea;
        Button picked;

        public MyHolder(View itemView)
        {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.class_section = (TextView) itemView.findViewById(R.id.class_section);
            this.address=(TextView)itemView.findViewById(R.id.Address);
            this.father_name=(TextView)itemView.findViewById(R.id.father_name);
            this.mother_name=(TextView)itemView.findViewById(R.id.mother_name);
            this.father_contact=(TextView)itemView.findViewById(R.id.father_contact_no);
            this.mother_contact=(TextView)itemView.findViewById(R.id.mother_contact_no);
            this.image=(ImageView)itemView.findViewById(R.id.image);
            this.expandArea=(RelativeLayout)itemView.findViewById(R.id.expandArea);
            this.picked=(Button)itemView.findViewById(R.id.picked);
        }
    }

    public BusRecycleGrid(Context c, List<Student> data, VenueAdapterClickCallbacks venueAdapterClickCallback)
    {

        this.dataSet = data;
        this.venueAdapterClickCallbacks=venueAdapterClickCallback;
        context=c;
        pref=new PrefManager(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_bus_card, parent, false);
        MyHolder myNewsHolder=new MyHolder(view);
        re = (RecyclerView)parent.findViewById(R.id.bus_attendance_grid);
        view.setTag(myNewsHolder);
        return myNewsHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        TextView name = holder.name;
        TextView class_sec = holder.class_section;
        TextView address = holder.address;
        TextView father_name = holder.father_name;
        TextView mother_name = holder.mother_name;
        TextView father_contact = holder.father_contact;
        TextView mother_contact = holder.mother_contact;
        ImageView image=holder.image;
        final Button picked=holder.picked;

        picked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(picked.getText().toString().equals("Picked")){
                picked.setCompoundDrawablesWithIntrinsicBounds( ic_undo_grey_24dp, 0, 0, 0);
                picked.setTextColor(context.getResources().getColor(R.color.grey));
                picked.setBackground(context.getResources().getDrawable(undo_button_background));
                picked.setText("Undo");}
                else
                {
                    picked.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
                    picked.setTextColor(context.getResources().getColor(R.color.splashTitle));
                    picked.setBackground(context.getResources().getDrawable(picked_droped_bckgrnd));
                    picked.setText("Picked");
                }

            }
        });

        name.setText(dataSet.get(position).getName());
        String p=dataSet.get(position).getImage();

        Log.e("Imageeeee",p);
        if(p!=null) {

            Glide.with(context).load(dataSet.get(position).getImage())
                    .dontAnimate()
                    .centerCrop()
                    .crossFade()
                    .thumbnail(0.5f)
                    .override(500,500)
                    .bitmapTransform(new CircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);

        }

        class_sec.setText(dataSet.get(position).getClass_sec());
        address.setText(dataSet.get(position).getAddress());
        father_name.setText(dataSet.get(position).getFather_name());
        mother_name.setText(dataSet.get(position).getMother_name());
        father_contact.setText(dataSet.get(position).getFather_contact());
        mother_contact.setText(dataSet.get(position).getMother_contact());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                venueAdapterClickCallbacks.onCardClick(dataSet.get(position).getName());

            }
        });

        Log.e("Value",dataSet.get(position).isChecked+"");

        if (dataSet.get(position).isChecked == true) {
            holder.expandArea.setVisibility(View.VISIBLE);
        } else {
            holder.expandArea.setVisibility(View.GONE);
        }

        onExpand( holder.itemView, context, position);

    }

    private void onExpand(View view, final Context mContext, final int position) {
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataSet.get(position).isChecked!= true) {
                            dataSet.get(position).isChecked=true;
                            re.scrollToPosition(position);
                        }

                        else {
                            dataSet.get(position).isChecked=false;
                            re.scrollToPosition(position);
                        }
                        notifyItemChanged(position);

                    }
                }

        );
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface VenueAdapterClickCallbacks {
        void onCardClick( String p);

    }

    public void setFilter(List<Student> Model) {
        dataSet = new ArrayList<>();
        dataSet.addAll(Model);
        notifyDataSetChanged();
    }


}
