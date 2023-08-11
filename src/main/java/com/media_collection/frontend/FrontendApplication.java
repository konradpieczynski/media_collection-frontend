package com.media_collection.frontend;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "media_collection")
public class FrontendApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}
