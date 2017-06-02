package com.luminous.doit.fragToDo.message;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.doit.R;
import com.luminous.doit.fragToDo.FragToDo;
import com.luminous.doit.fragToDo.InsetMessage;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by 90418 on 2017-05-30.
 */

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.ViewHolder> {
    private ArrayList<Message> messages;
    private Context context;
    private Fragment fragment;
    //用于发送通知
    private Handler handler;
    public MessAdapter(final Fragment fragment){
        messages = new ArrayList<>();
        this.fragment = fragment;
        handler= new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    Log.e("thistime",hour+":"+minute);
                    for(Message m : messages){
                        String[] time = m.getTimeStart().split(":");
                        if(Integer.valueOf(time[0])==hour&&Integer.valueOf(time[1])==minute&&m.getFinish().equals("false")){
                            Intent intent = new Intent(fragment.getActivity(), FragToDo.class);
                            PendingIntent pi = PendingIntent.getActivity(fragment.getContext(),0,intent,0);
                            Notification notification = new NotificationCompat.Builder(fragment.getContext())
                                    .setContentTitle("你有一件事要做哦")
                                    .setContentText(m.getMessage())
                                    .setSmallIcon(R.drawable.notify)
                                    .setLargeIcon(BitmapFactory.decodeResource(fragment.getResources(),R.mipmap.ic_launcher))
                                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                                    .setVibrate(new long[]{0,1000,1000,1000})
                                    .setLights(Color.GREEN,1000,1000)
                                    .setOngoing(false)
                                    .setContentIntent(pi)
                                    .setAutoCancel(true)
                                    .setWhen(System.currentTimeMillis()).build();
                            NotificationManager manager = (NotificationManager) fragment.getActivity().getSystemService(fragment.getActivity().NOTIFICATION_SERVICE);
                            manager.notify(1,notification);
                        }
                    }
                }
            }
        };
        sendMessage();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.e("adapter","oncreate");
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMennu = new PopupMenu(parent.getContext(), v ,Gravity.RIGHT);
                popupMennu.getMenuInflater().inflate(R.menu.menu,popupMennu.getMenu());
                popupMennu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.menu_delete){
                            MessHandler handler = new MessHandler(parent.getContext());
                            int positon = viewHolder.getAdapterPosition();
                            Message m = messages.get(positon);
                            if(m.getFinish().equals("true")){
                                Toast.makeText(fragment.getContext(),"已经完成的事情就不能删除啦",Toast.LENGTH_LONG).show();
                            }else{
                                handler.deleMessage(m.getTimeStart());
                                messages = handler.getAllMessages();
                                notifyDataSetChanged();
                            }
                        }
                        if(item.getItemId() ==R.id.menu_change){
                            //编辑的方法
                            int positon = viewHolder.getAdapterPosition();
                            Message m = messages.get(positon);
                            if(m.getFinish().equals("true")){
                                Toast.makeText(fragment.getContext(),"已经完成的事情就不能更改啦",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(fragment.getActivity(),InsetMessage.class);
                                intent.putExtra("startTime",m.getTimeStart())
                                        .putExtra("stopTime",m.getTimeStop())
                                        .putExtra("message",m.getMessage());
                                fragment.startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
                popupMennu.show();
                return true;
            }
        });
        viewHolder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.cardView.setBackgroundResource(R.color.message_when_press);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        viewHolder.cardView.setBackgroundResource(R.color.message_when_normal);
                        break;
                    case MotionEvent.ACTION_UP:
                        viewHolder.cardView.setBackgroundResource(R.color.message_when_normal);
                        break;
                }
                return false;
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MessHandler handler = new MessHandler(parent.getContext());
                int position = viewHolder.getAdapterPosition();
                Message m = messages.get(position);

                if(isChecked){
                    handler.finish(m.getTimeStart());
                    viewHolder.checkBox.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.message_when_checked));
                    viewHolder.textView.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.message_when_checked));
                    m.setFinish("true");
                }else {
                    handler.restart(m.getTimeStart());
                    viewHolder.checkBox.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.message_when_not_checked));
                    viewHolder.textView.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.message_when_not_checked));
                    m.setFinish("false");
                }
                TextView textWelcome =(TextView) fragment.getActivity().findViewById(R.id.text_welcome);
                int countTodo=handler.findFalseMessageCount();
                if (countTodo==0){textWelcome.setText("添加今天要做的事情吧");}
                else{textWelcome.setText("你今天还有"+countTodo+"件事没有做");}

            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("adapter","onBind");
        holder.textView.setText(messages.get(position).getMessage());
        holder.checkBox.setText(messages.get(position).getTimeStart());
        if(messages.get(position).getFinish().equals("true")){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }
    }
    //他会根据长度调用oncreate方法
    @Override
    public int getItemCount() {
        return messages.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CheckBox checkBox;
        TextView textView;
        LinearLayout linearLayout;
        public ViewHolder(View v){
            super(v);
            //初始化v中的控件ImageView
            cardView = (CardView) v;
            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
            textView = (TextView) v.findViewById(R.id.textView);
        }
    }
    public void initMessages(){
        MessHandler handler = new MessHandler(context);
        messages = handler.getAllMessages();
        this.notifyDataSetChanged();
    }
    public void sendMessage(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    Calendar c = Calendar.getInstance();
                    int second = c.get(Calendar.SECOND);
                    if(second==0){
                        handler.sendEmptyMessageDelayed(0,0);
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.start();
    }
}
