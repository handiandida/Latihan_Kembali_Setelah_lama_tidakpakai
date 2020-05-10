package com.unikom.asktech.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unikom.asktech.Model.ChatModel;
import com.unikom.asktech.R;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<ChatModel> list_chat_models ;
    private Context context;
    private LayoutInflater layoutInflater;




    public CustomAdapter(List<ChatModel> list_chat_models, Context context) {
        this.list_chat_models = list_chat_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(ChatModel chatModel){
        this.list_chat_models.add(chatModel);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list_chat_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_chat_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        ChatModel chatModel = list_chat_models.get(position);
        if (chatModel.isSend()){
            convertView = layoutInflater.inflate(R.layout.list_item_message_send, null);
            holder.messageBody = convertView.findViewById(R.id.textMessage);
            convertView.setTag(holder);
            holder.messageBody.setText(chatModel.getMessage());
        }else{
            convertView = layoutInflater.inflate(R.layout.list_item_message_recv, null);
            holder.messageBody =  convertView.findViewById(R.id.textMessage);
            convertView.setTag(holder);
            holder.messageBody.setText(chatModel.getMessage());
        }
        return convertView;
    }

    class MessageViewHolder{
        public TextView messageBody;
    }

    }



