package com.media_collection.frontend.views.lists;

import com.media_collection.frontend.data.domain.SongCollection;
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

import java.util.stream.Collectors;

@Route(value = "song_collections", layout = MainLayout.class)
@PageTitle("Song Collections List")
@Component
public class SongCollectionsView extends VerticalLayout {
    Grid<SongCollection> grid = new Grid<>();
    TextField filterText = new TextField();
    SongCollectionForm form;
    private final BackendService service;

    public SongCollectionsView(BackendService service) {
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
        form = new SongCollectionForm(service.findAllSongCollections(filterText.getValue()), service);
        form.setWidth("25em");
        form.addSaveListener(this::saveSongCollection);
        form.addDeleteListener(this::deleteSongCollection);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("songCollection-grid");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setSizeFull();
        grid.addColumn(SongCollection::getSongCollectionId).setHeader("Song collection id").setSortable(true);
        grid.addColumn(SongCollection::getUserId).setHeader("User id").setSortable(true);
        grid.addColumn(SongCollection::getSongCollectionName).setHeader("Song collection name").setSortable(true);
        grid.addColumn(songCollection -> songCollection.getSongs().stream()
                .map(service::mapSongIdToTitle).collect(Collectors.joining("; ")))
                .setHeader("Songs in collection");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editSongCollection(event.getValue()));
    }

    private com.vaadin.flow.component.Component getToolbar() {
        filterText.setPlaceholder("Filter by collection name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addSongCollectionButton = new Button("Add song collection");
        addSongCollectionButton.addClickListener(click -> addSongCollection());
        var toolbar = new HorizontalLayout(filterText, addSongCollectionButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editSongCollection(SongCollection songCollection) {
        if (songCollection == null) {
            closeEditor();
        } else {
            form.setSongCollection(songCollection);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setSongCollection(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addSongCollection() {
        grid.asSingleSelect().clear();
        editSongCollection(new SongCollection());
    }
    private void updateList() {
        grid.setItems(service.findAllSongCollections(filterText.getValue()));
    }

    private void saveSongCollection(SongCollectionForm.SaveEvent event) {
        service.saveSongCollection(event.getSongCollection());
        updateList();
        closeEditor();
    }
    private void deleteSongCollection(SongCollectionForm.DeleteEvent event) {
        service.deleteSongCollection(event.getSongCollection());
        updateList();
        closeEditor();
    }
}
