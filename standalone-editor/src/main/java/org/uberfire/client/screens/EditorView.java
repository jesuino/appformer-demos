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

package org.uberfire.client.screens;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.ext.widgets.common.client.ace.AceEditor;
import org.uberfire.ext.widgets.common.client.ace.AceEditorTheme;

import com.google.gwt.core.client.JavaScriptObject;

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

    EditorPresenter presenter;

    @Override
    public void init(EditorPresenter presenter) {
        this.presenter = presenter;
        aceEditor.startEditor();
        aceEditor.setTheme(AceEditorTheme.CHROME);
        aceEditor.setReadOnly(false);
        aceEditor.addOnChangeHandler(this::onEditorChange);
        aceEditor.setAutocompleteEnabled(true);
    }

    @Override
    public HTMLElement getElement() {
        return editorRoot;
    }
    
    protected void onEditorChange(JavaScriptObject obj) {
    }


}
