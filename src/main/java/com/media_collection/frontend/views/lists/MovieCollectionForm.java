package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.MovieCollection;
import com.media_collection.frontend.data.domain.Movie;
import com.media_collection.frontend.data.domain.User;
import com.media_collection.frontend.data.service.BackendService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class MovieCollectionForm extends FormLayout {

    TextField movieCollectionId = new TextField("Movie collection id");
    ComboBox<Long> userId = new ComboBox<>("User id");
    TextField movieCollectionName = new TextField("Movie collection name");
    MultiSelectComboBox<Long> movies = new MultiSelectComboBox<>("Movie in collection: ");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<MovieCollection> binder = new BeanValidationBinder<>(MovieCollection.class);

    public MovieCollectionForm(List<MovieCollection> movieCollections, BackendService backendService) {
        addClassName("movieCollection-form");
        binder.bindInstanceFields(this);
        movieCollectionId.setReadOnly(true);
        userId.setItems(backendService.getUserCache().stream().map(User::getUserId).toList());
        userId.setItemLabelGenerator((ItemLabelGenerator<Long>) backendService::mapUserIdToName);
        movies.setItems(backendService.getMovies().stream().map(Movie::getMovieId).toList());
        movies.setItemLabelGenerator((ItemLabelGenerator<Long>) backendService::mapMovieIdToTitle);
        add(movieCollectionId, userId, movieCollectionName, movies, createButtonsLayout());
    }

    public void setMovieCollection(MovieCollection movieCollection) {
        binder.setBean(movieCollection);
    }

    public static abstract class MovieCollectionFormEvent extends ComponentEvent<MovieCollectionForm> {
        private MovieCollection movieCollection;
        protected MovieCollectionFormEvent(MovieCollectionForm source, MovieCollection movieCollection) {
            super(source, false);
            this.movieCollection = movieCollection;
        }

        public MovieCollection getMovieCollection() {
            return movieCollection;
        }
    }

    public static class SaveEvent extends MovieCollectionFormEvent {
        SaveEvent(MovieCollectionForm source, MovieCollection movieCollection) {
            super(source, movieCollection);
        }
    }

    public static class DeleteEvent extends MovieCollectionFormEvent {
        DeleteEvent(MovieCollectionForm source, MovieCollection movieCollection) {
            super(source, movieCollection);
        }

    }

    public static class CloseEvent extends MovieCollectionFormEvent {
        CloseEvent(MovieCollectionForm source) {
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
