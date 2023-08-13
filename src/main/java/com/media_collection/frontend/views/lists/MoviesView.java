package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.Movie;
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

@Route(value = "movies", layout = MainLayout.class)
@PageTitle("Movies List")
@Component
public class MoviesView extends VerticalLayout {
    Grid<Movie> grid = new Grid<>();
    TextField filterText = new TextField();
    MovieForm form;
    private final BackendService service;

    public MoviesView(BackendService service) {
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
        form = new MovieForm(service.findAllMovies(filterText.getValue()));
        form.setWidth("25em");
        form.addSaveListener(this::saveMovie);
        form.addDeleteListener(this::deleteMovie);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("movie-grid");
        grid.setSizeFull();
        grid.addColumn(Movie::getMovieId).setHeader("Movie id").setSortable(true);
        grid.addColumn(Movie::getMovieTitle).setHeader("Title").setSortable(true);
        grid.addColumn(Movie::getMovieYear).setHeader("Release date").setSortable(true);
        grid.addColumn(movie -> movie.getMovieCollections().size()).setHeader("User collections").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editMovie(event.getValue()));
    }

    private com.vaadin.flow.component.Component getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addMovieButton = new Button("Add movie");
        addMovieButton.addClickListener(click -> addMovie());
        var toolbar = new HorizontalLayout(filterText, addMovieButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editMovie(Movie movie) {
        if (movie == null) {
            closeEditor();
        } else {
            form.setMovie(movie);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setMovie(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addMovie() {
        grid.asSingleSelect().clear();
        editMovie(new Movie());
    }
    private void updateList() {
        grid.setItems(service.findAllMovies(filterText.getValue()));
    }

    private void saveMovie(MovieForm.SaveEvent event) {
        service.saveMovie(event.getMovie());
        updateList();
        closeEditor();
    }
    private void deleteMovie(MovieForm.DeleteEvent event) {
        service.deleteMovie(event.getMovie());
        updateList();
        closeEditor();
    }
}
