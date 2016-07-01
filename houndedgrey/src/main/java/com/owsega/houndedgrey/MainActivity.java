package com.owsega.houndedgrey;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.android.sdk.TextSearch;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.core.model.sdk.HoundResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText queryEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryEditTxt = (EditText) findViewById(R.id.edit_query);
        Button button = (Button) findViewById(R.id.button_text_search);
        if (button != null)
            button.findViewById(R.id.button_text_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initiateSearch(queryEditTxt.getText());
                }
            });
    }

    private void initiateSearch(CharSequence text) {
        Toast.makeText(this, "Initiating search with " + text, Toast.LENGTH_SHORT).show();
        new TextSearcher().execute(getTextSearch(text.toString()));

    }

    private TextSearch getTextSearch(String query) {
        return new TextSearch.Builder()
                .setQuery(query)
                .setRequestInfo(MiscRequestInfoFactory.get(this).create())
                .setClientId(Constants.CLIENT_MATCH_ID)
                .setClientKey(Constants.CLIENT_MATCH_KEY)
                .build();
    }

    /**
     * async task to run a text query using a TextSearch object
     */
    private class TextSearcher extends AsyncTask<TextSearch, Void, Object[]> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = ProgressDialog.show(MainActivity.this, null, "Searching");
        }

        @Override
        protected Object[] doInBackground(TextSearch... params) {
            try {
                TextSearch.Result result = params[0].search();
                final HoundResponse response = result.getResponse();
                final VoiceSearchInfo info = result.getSearchInfo();
                Object[] returnObj = new Object[2];

                if (!response.getResults().isEmpty()) {
                    //todo what if there are more than one item in the results array? try get(1) get(2) etc
                    returnObj[0] = response.getResults().get(0).getConversationState();
                }

                String message;
                try {
                    message = "Response\n\n" + new JSONObject(info.getContentBody()).toString(4);
                } catch (final JSONException ex) {
                    message = "Bad JSON\n\n" + response;
                }
                returnObj[1] = message;
                return returnObj;
            } catch (TextSearch.TextSearchException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object[] result) {
            if (result != null) {
                MiscRequestInfoFactory.get(MainActivity.this).setConversationState(
                        result[0] instanceof JsonNode ? (JsonNode) result[0] : null);
                TextView textView = (TextView) findViewById(R.id.response_display_tv);
                if (textView != null) textView.setText((String) result[1]);
            } else
                Toast.makeText(MainActivity.this, "Exception occurred!", Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
        }
    }
}
