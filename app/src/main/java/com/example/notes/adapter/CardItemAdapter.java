package com.example.notes.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.UpdateActivity;
import com.example.notes.model.NotesModel;
import com.example.notes.viewModel.NotesViewModel;

import java.util.List;

public class CardItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<NotesModel> list;

    NotesViewModel notesViewModel;



    boolean starBoy =false;

    public  final  int LAYOUT_ITEM_WITH_IMG=0;
    public  final  int LAYOUT_ITEM_WITHOUT_IMG=1;


    public CardItemAdapter(Context context, List<NotesModel> list,NotesViewModel notesViewModel) {
        this.context = context;
        this.list = list;
        this.notesViewModel=notesViewModel;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;


        if(viewType==LAYOUT_ITEM_WITH_IMG){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_with_img,parent,false);

             return  new ViewHolderWithImage(view);
        } else  {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
            return  new ViewHolderWithoutImg(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        NotesModel notesModel = list.get(position);

        if(holder.getItemViewType()==LAYOUT_ITEM_WITH_IMG){

            ViewHolderWithImage viewHolder = (ViewHolderWithImage) holder;
            viewHolder.title.setText(notesModel.getTitle());
            viewHolder.description.setText(notesModel.getDescription());
            Glide.with(context).load(notesModel.getImg()).into(viewHolder.img);


        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean starSelected = !notesModel.isStarSelected();
                notesModel.setStarSelected(starSelected);

                notesViewModel.updateData(list.get(position));

                // Update the star icon drawable based on the updated starSelected value
                if (starSelected) {
                    viewHolder.star.setImageResource(R.drawable.star);
                } else {
                    viewHolder.star.setImageResource(R.drawable.star_outline);
                }

            }
        });

        }
        else if (holder.getItemViewType()==LAYOUT_ITEM_WITHOUT_IMG){

            ViewHolderWithoutImg viewHolder = (ViewHolderWithoutImg) holder;

            viewHolder.title.setText(notesModel.getTitle());
            viewHolder.description.setText(notesModel.getDescription());


            viewHolder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean starSelected = !notesModel.isStarSelected();
                    notesModel.setStarSelected(starSelected);

                    notesViewModel.updateData(list.get(position));

                    // Update the star icon drawable based on the updated starSelected value
                    if (starSelected) {
                        viewHolder.star.setImageResource(R.drawable.star);
                    } else {
                        viewHolder.star.setImageResource(R.drawable.star_outline);
                    }
                }
            });



        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                deleteAlertDialogBox(position);

                return  true;
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("id",list.get(position).getId());
                i.putExtra("title",list.get(position).getTitle());
                i.putExtra("description",list.get(position).getDescription());
                i.putExtra("img",list.get(position).getImg());
                i.putExtra("isSelected",list.get(position).isStarSelected());
                ((MainActivity) context).startActivityForResult(i, 49);
            }
        });


    }

    private void deleteAlertDialogBox(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete Note");
        builder.setMessage("Do u want to delete the Note ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              deleteData(position);
            }
        });

        builder.setNegativeButton("No",null);

        builder.create().show();

    }

    private void deleteData(int position) {
       notesViewModel.deleteData(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderWithImage extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView img;

        ImageView star;

        public ViewHolderWithImage(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.card_item_img_title);
            description=itemView.findViewById(R.id.card_item_img_description);
            img=itemView.findViewById(R.id.card_item_img);
            star = itemView.findViewById(R.id.card_item_img_star);


        }
    }


    public class ViewHolderWithoutImg extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;

        ImageView star;

        public ViewHolderWithoutImg(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_item_title);
            description=itemView.findViewById(R.id.card_item_description);
            star = itemView.findViewById(R.id.card_item_star);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return  list.get(position).getImg()!=null?LAYOUT_ITEM_WITH_IMG:LAYOUT_ITEM_WITHOUT_IMG;

    }
}
