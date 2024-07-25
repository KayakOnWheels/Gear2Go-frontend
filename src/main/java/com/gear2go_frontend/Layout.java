package com.gear2go_frontend;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Route("")
public class Layout extends AppLayout {

    public Layout() {
        H1 title = new H1("Gear2Go");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        HorizontalLayout navigation = getNavigation();
        navigation.getElement();

        addToNavbar(title, navigation);
    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();

        navigation.addClassNames(
                JustifyContent.END,
                Gap.SMALL,
                Height.MEDIUM,
                Width.FULL);

        navigation.add(
                createLink("Products", Products.class),
                createLink("Sign In", Login.class),
                createIconLink(VaadinIcon.CART, Products.class),
                createIconLink(VaadinIcon.USER, Products.class));

        return navigation;
    }

    private RouterLink createLink(String viewName, Class<? extends Component> view) {
        RouterLink link = new RouterLink();
        link.add(viewName);

        link.setRoute(view);

        link.addClassNames(
                Display.FLEX,
                AlignItems.CENTER,
                Padding.Horizontal.MEDIUM,
                TextColor.SECONDARY,
                FontWeight.MEDIUM);

        link.getStyle().set("text-decoration", "none");

        return link;
    }

    private RouterLink createIconLink(VaadinIcon icon, Class<? extends Component> view) {
        RouterLink link = new RouterLink();

        link.setRoute(view);

        link.addClassNames(
                Display.FLEX,
                Gap.XSMALL,
                Height.MEDIUM,
                AlignItems.CENTER,
                Padding.Horizontal.SMALL,
                TextColor.BODY
        );

        Icon iconComponent = new Icon(icon);
        iconComponent.addClassNames(
                BoxSizing.BORDER,
                Padding.XSMALL
        );

        link.add(iconComponent);
        return link;
    }

}
