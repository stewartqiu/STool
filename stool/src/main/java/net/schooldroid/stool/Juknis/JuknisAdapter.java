package net.schooldroid.stool.Juknis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public JuknisAdapter (RecyclerView recyclerView, ArrayList<ModelJuknis> arrayList, Context context){
        Collections.sort(arrayList ,ModelJuknis.Sort);
        this.recyclerView = recyclerView;
        this.arrayList = arrayList;
        this.context = context;
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

        ExpandableLayout expandableLayout;
        RelativeLayout expandButton;
        TextView headerText;
        HtmlTextView contentText;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            expandButton = itemView.findViewById(R.id.expand_button);
            headerText = itemView.findViewById(R.id.juknisHeader);
            contentText = itemView.findViewById(R.id.juknisContent);
            icon = itemView.findViewById(R.id.arrowIcon);

            expandableLayout.setInterpolator(new OvershootInterpolator());
            expandableLayout.setOnExpansionUpdateListener(this);

            expandButton.setOnClickListener(this);
        }

        public void bind(){
            int position = getAdapterPosition();
            boolean isSelected = position == selectedItem;

            expandButton.setSelected(isSelected);
            expandableLayout.setExpanded(isSelected, false);

            headerText.setText(arrayList.get(position).header);
            contentText.setHtml(arrayList.get(position).content);
            final Class<?> act = arrayList.get(position).linkToActivity;

            if (act != null) {
                contentText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, act));
                    }
                });
            }
            else {
                contentText.setOnClickListener(null);
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

            int position = getAdapterPosition();
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                expandButton.setSelected(true);
                expandableLayout.expand();
                icon.setImageResource(R.drawable.expand_down);
                selectedItem = position;
            }
        }

        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }

}
