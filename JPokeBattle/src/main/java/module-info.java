module it.jpokebattle {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.web;

    requires transitive org.controlsfx.controls;
    // requires transitive com.dlsc.formsfx;
    // requires transitive net.synedra.validatorfx;
    // requires transitive org.kordamp.ikonli.javafx;
    // requires transitive org.kordamp.bootstrapfx.core;
    // requires transitive eu.hansolo.tilesfx;
    // requires transitive com.almasb.fxgl.all;
    requires transitive javafx.media;
    requires transitive com.google.gson;
    requires transitive java.desktop;
    requires transitive com.fasterxml.jackson.annotation;
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;


    exports it.jpokebattle;
    opens it.jpokebattle;

    // Controller
    exports it.jpokebattle.controller;
    opens it.jpokebattle.controller;

    exports it.jpokebattle.controller.battle;
    opens it.jpokebattle.controller.battle;

    exports it.jpokebattle.controller.gameover;
    opens it.jpokebattle.controller.gameover;

    exports it.jpokebattle.controller.leaderboard;
    opens it.jpokebattle.controller.leaderboard;

    exports it.jpokebattle.controller.menu;
    opens it.jpokebattle.controller.menu;





    // Model
    exports it.jpokebattle.model;
    opens it.jpokebattle.model;

    exports it.jpokebattle.model.battle;
    opens it.jpokebattle.model.battle;

    exports it.jpokebattle.model.exceptions;
    opens it.jpokebattle.model.exceptions;

    exports it.jpokebattle.model.leaderboard;
    opens it.jpokebattle.model.leaderboard;

    exports it.jpokebattle.model.player;
    opens it.jpokebattle.model.player;

    exports it.jpokebattle.model.pokemon;
    opens it.jpokebattle.model.pokemon;

    exports it.jpokebattle.model.team;
    opens it.jpokebattle.model.team;

    exports it.jpokebattle.model.type;
    opens it.jpokebattle.model.type;





    // Util
    exports it.jpokebattle.util;
    opens it.jpokebattle.util;




    // View
    exports it.jpokebattle.view;
    opens it.jpokebattle.view;








}
