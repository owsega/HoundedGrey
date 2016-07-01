package com.owsega.houndedgrey;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.android.fd.DefaultRequestInfoFactory;
import com.hound.core.model.sdk.ClientMatch;
import com.hound.core.model.sdk.HoundRequestInfo;

import java.util.ArrayList;
import java.util.UUID;

/**
 * We use a singleton in order to not hold a memory reference to the host activity since this is registered in the Houndify
 * singleton.
 */
public class MiscRequestInfoFactory extends DefaultRequestInfoFactory {

    private static MiscRequestInfoFactory instance;

    private JsonNode conversationState;

    private MiscRequestInfoFactory(Context context) {
        super(context);
    }

    public static MiscRequestInfoFactory get(final Context context) {
        if (instance == null) {
            instance = new MiscRequestInfoFactory(context);
        }
        return instance;
    }

    public void setConversationState(JsonNode conversationState) {
        this.conversationState = conversationState;
    }

    public HoundRequestInfo createFirst() {
        final HoundRequestInfo requestInfo = super.create();
        requestInfo.setConversationState(conversationState);

        ArrayList<ClientMatch> clientMatchList = new ArrayList<>();
        clientMatchList.add(MiscClientMatchFactory.getLightsOnMatch());
        clientMatchList.add(MiscClientMatchFactory.getLightsOffMatch());
        requestInfo.setClientMatches(clientMatchList);

        requestInfo.setUserId("User_ID");
        requestInfo.setRequestId(UUID.randomUUID().toString());
        requestInfo.setLatitude(6.5244);  // lagos
        requestInfo.setLongitude(3.3792); // lagos
        return requestInfo;
    }

    @NonNull
    @Override
    public HoundRequestInfo create() {
        return createFirst();
    }
}
