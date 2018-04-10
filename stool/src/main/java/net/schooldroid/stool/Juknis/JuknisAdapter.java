package net.schooldroid.stool.Juknis;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.schooldroid.stool.R;
import net.schooldroid.stool.STool;


import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.Collections;

public class JuknisAdapter extends RecyclerView.Adapter<JuknisAdapter.ViewHolder>{

    private static final int UNSELECTED = -1;

    ArrayList<ModelJuknis> arrayList;
    private int selectedItem = UNSELECTED;
    RecyclerView recyclerView;

    Context context;

    ArrayList<ModelJuknis> arrayListSub;

    public JuknisAdapter (RecyclerView recyclerView, ArrayList<ModelJuknis> arrayList, ArrayList<ModelJuknis> arrayListSub,Context context, int openUrut){
        Collections.sort(arrayList ,ModelJuknis.Sort);
        this.recyclerView = recyclerView;
        this.arrayList = arrayList;
        this.context = context;
        this.arrayListSub = arrayListSub;

        if (openUrut > 0) {
            selectedItem = openUrut - 1;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.juknis_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ExpandableLayout expandableLayout;
        RelativeLayout expandButton;
        TextView headerText;
        HtmlTextView contentText;
        ImageView icon, contentImage;
        RelativeLayout contentParent;
        Button linkBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            expandButton = itemView.findViewById(R.id.expand_button);
            headerText = itemView.findViewById(R.id.juknisHeader);
            contentText = itemView.findViewById(R.id.juknisContentText);
            icon = itemView.findViewById(R.id.arrowIcon);
            contentImage = itemView.findViewById(R.id.juknisContentImage);
            contentParent = itemView.findViewById(R.id.juknisContentParent);

            linkBtn = itemView.findViewById(R.id.linkButton);

            expandableLayout.setInterpolator(new OvershootInterpolator());
        }

        public void bind(){
            int position = getAdapterPosition();

            headerText.setText(arrayList.get(position).header);
            contentText.setHtml(arrayList.get(position).content);
            expandButton.setOnClickListener(this);

            boolean isSelected = position == selectedItem;


            expandButton.setSelected(isSelected);
            expandableLayout.setExpanded(isSelected, false);

            // RECYCLERVIEW BUG
            if (isSelected) {
                icon.setImageResource(R.drawable.expand_down);
            } else {
                icon.setImageResource(R.drawable.chevron_right);
            }


            final String kategoriSubJuknis = arrayList.get(position).kategoriSubJuknis;
            final Class<?> act = arrayList.get(position).linkToActivity;

            // set on click kalau ada subjuknis
            if (kategoriSubJuknis != null && !kategoriSubJuknis.isEmpty()){

                Collections.sort(arrayListSub, ModelJuknis.Sort);

                if (!arrayListSub.isEmpty()) {
                    linkBtn.setVisibility(View.VISIBLE);
                    contentParent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            STool.showJuknis(context,arrayListSub,kategoriSubJuknis,0);
                        }
                    });

                    linkBtn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { contentParent.performClick(); }});
                    contentText.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { contentParent.performClick(); }});
                }

            }
            // set on click kalau activity tidak null
            // Link to activity
            else if (act != null) {
                linkBtn.setVisibility(View.VISIBLE);
                contentParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, act));
                    }
                });

                linkBtn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { contentParent.performClick(); }});
                contentText.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { contentParent.performClick(); }});
            }
            else {
                linkBtn.setVisibility(View.GONE);
                contentParent.setOnClickListener(null);
                linkBtn.setOnClickListener(null);
                contentText.setOnClickListener(null);
            }





            // Show image
            Integer resourceId = arrayList.get(position).resourceId;
            // image visibility gone kalau null
            if (resourceId==null) {
                contentImage.setVisibility(View.GONE);
            }
            // Tampilkan image kalau tidak null
            else {
                contentImage.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(resourceId)
                        .centerInside()
                        .resize(arrayList.get(position).imageWidth,arrayList.get(position).imageHeight)
                        .into(contentImage);
            }

        }

        @Override
        public void onClick(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.expandButton.setSelected(false);
                holder.expandableLayout.collapse();
                holder.icon.setImageResource(R.drawable.chevron_right);
            }

            final int position = getAdapterPosition();
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                expandButton.setSelected(true);
                expandableLayout.expand();
                icon.setImageResource(R.drawable.expand_down);
                selectedItem = position;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(position);
                    }
                });
            }


        }


    }

}
