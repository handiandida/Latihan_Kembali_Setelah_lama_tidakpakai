package com.unikom.asktech.Model.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.unikom.asktech.Adapter.CustomAdapter;
import com.unikom.asktech.Model.ChatModel;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

import java.util.List;

public class RequestTask extends AsyncTask<List<ChatModel>, Void, DetectIntentResponse> {

    private Activity activity;
    private SessionName mSessionName;
    private SessionsClient mSessionsClient;
    private QueryInput mQueryInput;
    private ListView mListView;
    private List<ChatModel> models;

    public RequestTask(Activity activity, SessionName session, SessionsClient sessionsClient, QueryInput queryInput) {
        this.activity = activity;
        this.mSessionName= session;
        this.mSessionsClient = sessionsClient;
        this.mQueryInput = queryInput;
    }


    @Override
    protected DetectIntentResponse doInBackground(List<ChatModel>... params) {
        models = params[0];
        try{
            DetectIntentRequest detectIntentRequest =
                    DetectIntentRequest.newBuilder()
                            .setSession(mSessionName.toString())
                            .setQueryInput(mQueryInput)
                            .build();
            return mSessionsClient.detectIntent(detectIntentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(DetectIntentResponse response) {

        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            ChatModel chatModel= new ChatModel(botReply,false);
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models, activity.getApplicationContext());

        } else {
        }
    }

}
