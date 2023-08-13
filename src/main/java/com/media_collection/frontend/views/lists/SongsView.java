package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.Song;
import com.media_collection.frontend.data.service.BackendService;
import com.media_collection.frontend.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route(value = "songs", layout = MainLayout.class)
@PageTitle("Songs List")
@Component
public class SongsView extends VerticalLayout {
    Grid<Song> grid = new Grid<>();
    TextField filterText = new TextField();
    SongForm form;
    private final BackendService service;

    public SongsView(BackendService service) {
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
        form = new SongForm(service.findAllSongs(filterText.getValue()));
        form.setWidth("25em");
        form.addSaveListener(this::saveSong);
        form.addDeleteListener(this::deleteSong);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("song-grid");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setSizeFull();
        grid.addColumn(Song::getSongId).setHeader("Song id").setSortable(true);
        grid.addColumn(Song::getSongAuthor).setHeader("Artist").setSortable(true);
        grid.addColumn(Song::getSongTitle).setHeader("Title").setSortable(true);
        grid.addColumn(song -> song.getSongCollections().size()).setHeader("User collections").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editSong(event.getValue()));
    }

    private com.vaadin.flow.component.Component getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addSongButton = new Button("Add song");
        addSongButton.addClickListener(click -> addSong());
        var toolbar = new HorizontalLayout(filterText, addSongButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editSong(Song song) {
        if (song == null) {
            closeEditor();
        } else {
            form.setSong(song);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setSong(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addSong() {
        grid.asSingleSelect().clear();
        editSong(new Song());
    }
    private void updateList() {
        grid.setItems(service.findAllSongs(filterText.getValue()));
    }

    private void saveSong(SongForm.SaveEvent event) {
        service.saveSong(event.getSong());
        updateList();
        closeEditor();
    }
    private void deleteSong(SongForm.DeleteEvent event) {
        service.deleteSong(event.getSong());
        updateList();
        closeEditor();
    }
}
