package com.unikom.asktech.View;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.unikom.asktech.Adapter.CustomAdapter;
import com.unikom.asktech.Controller.chatBotController;
import com.unikom.asktech.Model.ChatModel;
import com.unikom.asktech.Model.api.RequestTask;
import com.unikom.asktech.R;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatBot extends AppCompatActivity implements chatBotController.chatBotCallbackListener {
    private EditText inputEditText;
    private ListView mListView;
    private List<ChatModel> list_chat = new ArrayList<>();
    private List<ChatModel> models;
    private ImageButton sendButtonMassage;
    private String uuid = UUID.randomUUID().toString();
    private SessionsClient mSessionsClient;
    private SessionName mSessionName;
    private CustomAdapter mCustomAdapter;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        //messagesTextView = findViewById(R.id.textMessage);
        mCustomAdapter = new CustomAdapter(list_chat,this);
        inputEditText = findViewById(R.id.inputEditText);
        sendButtonMassage = findViewById(R.id.sendButton);
        mListView = findViewById(R.id.list_of_message);
        mListView.setAdapter(mCustomAdapter);
        sendButtonMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        });
        initChatBot();
        mImageButton = findViewById(R.id.ImageButtonLeft);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               back();
            }
        });

    }

    @Override
    public void initChatBot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.test_agent_credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            mSessionsClient = SessionsClient.create(sessionsSettings);
            mSessionName = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage() {
        String msg = inputEditText.getText().toString();
        ChatModel model = new ChatModel(msg,true);
        list_chat.add(model);
        QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
        new RequestTask(ChatBot.this, mSessionName, mSessionsClient, queryInput).execute(list_chat);

        inputEditText.setText("");
    }

    public void back(){
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
    }
}
