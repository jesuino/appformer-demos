/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.editor.client.screens;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberElemental;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

@ApplicationScoped
@WorkbenchScreen( identifier = "EditorPresenter" )
public class EditorPresenter {
    
    public interface View extends UberElemental<EditorPresenter> {
  
        void setContent(String type, String content);

        void showError(String string);
    }

    @Inject
    private View view;
    

    private String loadedAssetLocation;
    
    @PostConstruct
    public void init() {
        view.init(this);
    }  
    
    @WorkbenchPartTitle
    public String getTitle() {
        return "Editor";
    }

    @WorkbenchPartView
    public View getView() {
        return view;
    }
    
    /**
     * Load an asset content
     * @throws RequestException 
     */
    public void loadAsset(String location) throws RequestException {
        loadedAssetLocation = location;
        sendRequest(loadedAssetLocation, null, "not able to load asset");

    }

    /**
     * 
     * Save the editor content to the location
     * @param text
     * @throws RequestException 
     */
    public void saveContent(String text) throws RequestException {
        sendRequest(loadedAssetLocation, text, "not able to save asset");
    }

    private void sendRequest(String assetLocation, String text, String errorMessage) throws RequestException {
        String error = "Error '" +  errorMessage 
                                 + "' when handling asset " 
                                 + assetLocation;
        String assetURI = buildAssetURI(assetLocation);
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, assetURI);
        
        if (text != null) {
            builder = new RequestBuilder(RequestBuilder.POST, assetURI);
        }
        
        builder.sendRequest(text, new RequestCallback() {
            
            @Override
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == Response.SC_NOT_FOUND) {
                    getView().showError(error);
                    return;
                }
                JSONValue value = JSONParser.parseStrict(response.getText());
                String content = value.isObject().get("content").isString().stringValue();
                String type = value.isObject().get("extension").isString().stringValue();
                getView().setContent(type, content);
            }
            
            @Override
            public void onError(Request request, Throwable exception) {
                getView().showError(error);
            }
        });
    }
    
    private String buildAssetURI(String location) {
        String encodedLocation = URL.encodePathSegment(location);
        return "./rest/asset/" + encodedLocation;
    }


}
