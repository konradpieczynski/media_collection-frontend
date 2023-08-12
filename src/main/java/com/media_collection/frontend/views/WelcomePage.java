package com.media_collection.frontend.views;

import com.media_collection.frontend.data.service.BackendService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Component;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Welcome page")
@Component
public class WelcomePage extends VerticalLayout {
    final BackendService backendService;

    public WelcomePage(BackendService backendService) {
        this.backendService = backendService;
        addClassName("list-view");
        setSizeFull();
        createPage();
    }

    private void createPage() {
        VerticalLayout content = new VerticalLayout();
        content.setDefaultHorizontalComponentAlignment(Alignment.START);
        content.setWidthFull();
        content.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        H1 header1 = new H1("Welcome to media collection!");
        header1.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);
        Paragraph paragraph = getParagraph();
        content.add(header1, paragraph);
        add(content);
    }

    private Paragraph getParagraph() {
        Paragraph paragraph = new Paragraph();
        Paragraph paragraph1 = new Paragraph("Current statistics:");
        Paragraph paragraph2 = new Paragraph("Users: " + backendService.getUserCache().size());
        Paragraph paragraph3 = new Paragraph("Songs: " + backendService.getSongCache().size());
        Paragraph paragraph4 = new Paragraph("Song collections: " + backendService.getSongCollectionCache().size());
        Paragraph paragraph5 = new Paragraph("Movies: " + backendService.getMovieCache().size());
        Paragraph paragraph6 = new Paragraph("Movie collections: " + backendService.getMovieCollectionCache().size());
        paragraph.add(paragraph1, paragraph2, paragraph2, paragraph3, paragraph4, paragraph5, paragraph6);
        paragraph.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.Margin.MEDIUM);
        return paragraph;
    }
}
