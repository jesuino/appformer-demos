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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.editor.shared.util.ExtensionToEditorMode;
import org.uberfire.ext.widgets.common.client.ace.AceEditor;
import org.uberfire.ext.widgets.common.client.ace.AceEditorMode;
import org.uberfire.ext.widgets.common.client.ace.AceEditorTheme;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

@Dependent
@Templated
public class EditorView implements EditorPresenter.View, IsElement {

    @Inject
    @DataField
    HTMLDivElement editorRoot;

    @Inject
    @DataField
    AceEditor aceEditor;
    
    @Inject
    @DataField
    HTMLButtonElement btnSave;

    EditorPresenter presenter;

    @Override
    public void init(EditorPresenter presenter) {
        this.presenter = presenter;
        aceEditor.startEditor();
        aceEditor.setTheme(AceEditorTheme.CHROME);
        aceEditor.setAutocompleteEnabled(true);
        this.getContentLocationAndLoad();
    }

    @Override
    public HTMLElement getElement() {
        return editorRoot;
    }

    @Override
    public void setContent(String type, String content) {
        aceEditor.getElement().getStyle().setOpacity(1.0);
        aceEditor.setReadOnly(false);
        aceEditor.setText(content);
        AceEditorMode java = ExtensionToEditorMode.fromExtension(type);
        aceEditor.setMode(java);
    }
    
    @EventHandler("btnSave")
    public void saveContent(ClickEvent event) {
        try {
            aceEditor.getElement().getStyle().setOpacity(0.4);
            aceEditor.setReadOnly(true);
            presenter.saveContent(aceEditor.getText());
        } catch (RequestException e) {
            Window.alert("Error saving content: " + e.getMessage());
            DomGlobal.console.error(e);
        }
        
    }
    
    private void getContentLocationAndLoad() {
        String assetLocation = Window.Location.getParameter("asset");
        
        if (assetLocation != null) {
            try {
                presenter.loadAsset(assetLocation);
            } catch (RequestException e) {
                Window.alert("Not able to load asset " + e.getMessage());
                DomGlobal.console.error(e);
            }
        } else {
            Window.alert("Provide the asset location using the query param asset");
        }
    }


}
