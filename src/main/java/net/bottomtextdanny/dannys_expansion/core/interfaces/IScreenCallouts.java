package net.bottomtextdanny.dannys_expansion.core.interfaces;

public interface IScreenCallouts {

    boolean screenMouseScrolled(double mouseX, double mouseY, double delta);

    boolean screenMouseReleased(double mouseX, double mouseY, int button);

    boolean screenMouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY);
}
