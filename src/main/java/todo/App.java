package todo;

import todo.data.DataStore;
import todo.renderers.MenuRenderer;

public class App {
    public static void main(String[] args) {
        DataStore.generateData();

        MenuRenderer menuRenderer = new MenuRenderer();
        menuRenderer.renderMainMenu();
    }
}
