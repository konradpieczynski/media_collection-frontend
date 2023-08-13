package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.MovieCollection;
import com.media_collection.frontend.data.service.BackendService;
import com.media_collection.frontend.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route(value = "movie_collections", layout = MainLayout.class)
@PageTitle("Movie Collections List")
@Component
public class MovieCollectionsView extends VerticalLayout {
    Grid<MovieCollection> grid = new Grid<>();
    TextField filterText = new TextField();
    MovieCollectionForm form;
    private final BackendService service;

    public MovieCollectionsView(BackendService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }
    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm() {
        form = new MovieCollectionForm(service.findAllMovieCollections(filterText.getValue()), service);
        form.setWidth("25em");
        form.addSaveListener(this::saveMovieCollection);
        form.addDeleteListener(this::deleteMovieCollection);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("movieCollection-grid");
        grid.setSizeFull();
        grid.addColumn(MovieCollection::getMovieCollectionId).setHeader("Movie collection id").setSortable(true);
        grid.addColumn(MovieCollection::getUserId).setHeader("User id").setSortable(true);
        grid.addColumn(MovieCollection::getMovieCollectionName).setHeader("Movie collection name").setSortable(true);
        grid.addColumn(MovieCollection::getMovies).setHeader("Movies in collection");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editMovieCollection(event.getValue()));
    }

    private com.vaadin.flow.component.Component getToolbar() {
        filterText.setPlaceholder("Filter by collection name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addMovieCollectionButton = new Button("Add movie collection");
        addMovieCollectionButton.addClickListener(click -> addMovieCollection());
        var toolbar = new HorizontalLayout(filterText, addMovieCollectionButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editMovieCollection(MovieCollection movieCollection) {
        if (movieCollection == null) {
            closeEditor();
        } else {
            form.setMovieCollection(movieCollection);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setMovieCollection(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addMovieCollection() {
        grid.asSingleSelect().clear();
        editMovieCollection(new MovieCollection());
    }
    private void updateList() {
        grid.setItems(service.findAllMovieCollections(filterText.getValue()));
    }

    private void saveMovieCollection(MovieCollectionForm.SaveEvent event) {
        service.saveMovieCollection(event.getMovieCollection());
        updateList();
        closeEditor();
    }
    private void deleteMovieCollection(MovieCollectionForm.DeleteEvent event) {
        service.deleteMovieCollection(event.getMovieCollection());
        updateList();
        closeEditor();
    }
}
