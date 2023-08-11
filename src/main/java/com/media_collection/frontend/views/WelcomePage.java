package com.media_collection.frontend.views;

import com.vaadin.flow.component.html.Div;
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
    public WelcomePage() {
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
        Div div = new Div();
        Paragraph paragraph = new Paragraph("Current statistics:");
        header1.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.Margin.MEDIUM);
        content.add(header1,div,paragraph);
        add(content);
    }
}
