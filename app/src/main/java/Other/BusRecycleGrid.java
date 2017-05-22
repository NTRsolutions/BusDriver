package Other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.verbosetech.busdriverapp.R;

import java.util.List;

import Models.Student;

/**
 * Created by this pc on 22-05-17.
 */

public class BusRecycleGrid extends RecyclerView.Adapter<BusRecycleGrid.MyHolder>{

    public RecyclerView re;
    private List<Student> dataSet ;
    public Context context=null;
    VenueAdapterClickCallbacks venueAdapterClickCallbacks;

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
        }
    }

    public BusRecycleGrid(Context c, List<Student> data, VenueAdapterClickCallbacks venueAdapterClickCallback)
    {

        this.dataSet = data;
        this.venueAdapterClickCallbacks=venueAdapterClickCallback;
        context=c;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_bus_card, parent, false);
        MyHolder myNewsHolder=new MyHolder(view);
        re = (RecyclerView)parent.findViewById(R.id.bus_attendance_grid);
        return myNewsHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        TextView name = holder.name;
        TextView class_sec = holder.class_section;
        TextView address = holder.address;
        TextView father_name = holder.father_name;
        TextView mother_name = holder.mother_name;
        TextView father_contact = holder.father_contact;
        TextView mother_contact = holder.mother_contact;
        ImageView image=holder.image;

        name.setText(dataSet.get(position).getName());
        String p=dataSet.get(position).getImage();

        Log.e("Imageeeee",p);
        if(p!=null) {

            Glide.with(context).load(dataSet.get(position).getImage())
                    .centerCrop()
                    .crossFade()
                    .thumbnail(0.5f)
                    .override(150,150)
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

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface VenueAdapterClickCallbacks {
        void onCardClick( String p);

    }


}
