package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.Song;
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

public class SongForm extends FormLayout {

    TextField songId = new TextField("Song id");
    TextField songName = new TextField("Song Name");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<Song> binder = new BeanValidationBinder<>(Song.class);

    public SongForm(List<Song> songs) {
        addClassName("song-form");
        binder.bindInstanceFields(this);
        songId.setReadOnly(true);
        add(songId, songName, createButtonsLayout());
    }

    public void setSong(Song song) {
        binder.setBean(song);
    }

    public static abstract class SongFormEvent extends ComponentEvent<SongForm> {
        private Song song;
        protected SongFormEvent(SongForm source, Song song) {
            super(source, false);
            this.song = song;
        }

        public Song getSong() {
            return song;
        }
    }

    public static class SaveEvent extends SongFormEvent {
        SaveEvent(SongForm source, Song song) {
            super(source, song);
        }
    }

    public static class DeleteEvent extends SongFormEvent {
        DeleteEvent(SongForm source, Song song) {
            super(source, song);
        }

    }

    public static class CloseEvent extends SongFormEvent {
        CloseEvent(SongForm source) {
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
