package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.SongCollection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class SongCollectionForm extends FormLayout {

    TextField songCollectionId = new TextField("Song collection id");
    TextField songCollectionName = new TextField("Song collection name");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<SongCollection> binder = new BeanValidationBinder<>(SongCollection.class);

    public SongCollectionForm(List<SongCollection> songCollections) {
        addClassName("songCollection-form");
        binder.bindInstanceFields(this);
        songCollectionId.setReadOnly(true);
        add(songCollectionId, songCollectionName, createButtonsLayout());
    }

    public void setSongCollection(SongCollection songCollection) {
        binder.setBean(songCollection);
    }

    public static abstract class SongCollectionFormEvent extends ComponentEvent<SongCollectionForm> {
        private SongCollection songCollection;
        protected SongCollectionFormEvent(SongCollectionForm source, SongCollection songCollection) {
            super(source, false);
            this.songCollection = songCollection;
        }

        public SongCollection getSongCollection() {
            return songCollection;
        }
    }

    public static class SaveEvent extends SongCollectionFormEvent {
        SaveEvent(SongCollectionForm source, SongCollection songCollection) {
            super(source, songCollection);
        }
    }

    public static class DeleteEvent extends SongCollectionFormEvent {
        DeleteEvent(SongCollectionForm source, SongCollection songCollection) {
            super(source, songCollection);
        }

    }

    public static class CloseEvent extends SongCollectionFormEvent {
        CloseEvent(SongCollectionForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }
    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }
}
