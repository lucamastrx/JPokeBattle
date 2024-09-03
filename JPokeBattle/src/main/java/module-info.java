module it.jpokebattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.jpokebattle to javafx.fxml;
    exports it.jpokebattle;
}