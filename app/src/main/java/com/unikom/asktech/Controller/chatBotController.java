package com.unikom.asktech.Controller;


public class chatBotController {

    private chatBotCallbackListener mListener;

    public chatBotController(chatBotCallbackListener listener){
        mListener = listener;
    }



    public interface chatBotCallbackListener{
        void initChatBot();
        void sendMessage();



    }


}
