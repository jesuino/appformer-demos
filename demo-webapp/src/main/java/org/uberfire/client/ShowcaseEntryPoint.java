package org.uberfire.client;

import javax.annotation.PostConstruct;

import org.gwtbootstrap3.client.ui.Label;
import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

@EntryPoint
public class ShowcaseEntryPoint {


    @PostConstruct
    public void startApp() {
        RootPanel.get().add(new Label("HELLO!"));
        hideLoadingPopup();
    }


    //Fade out the "Loading application" pop-up
    private void hideLoadingPopup() {
        final Element e = RootPanel.get( "loading" ).getElement();


        new Animation() {

            @Override
            protected void onUpdate( double progress ) {
                e.getStyle().setOpacity( 1.0 - progress );
            }

            @Override
            protected void onComplete() {
                e.getStyle().setVisibility( Style.Visibility.HIDDEN );
            }
        }.run( 500 );
    }
}