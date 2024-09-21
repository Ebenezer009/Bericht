module de.renew.netdoc {
    exports de.renew.netdoc.gui;
    exports de.renew.netdoc.gui.commands;
    exports de.renew.netdoc.gui.components;
    exports de.renew.netdoc.gui.components.documentcontainer;
    exports de.renew.netdoc.gui.event;
    exports de.renew.netdoc.gui.swing;
    exports de.renew.netdoc.gui.windows;
    exports de.renew.netdoc.gui.windows.documentcontainer;

    exports de.renew.netdoc.io;
    exports de.renew.netdoc.io.documentformatters;
    exports de.renew.netdoc.io.documentparsers;
    exports de.renew.netdoc.io.managers;

    exports de.renew.netdoc.model.command;
    exports de.renew.netdoc.model.command.commands;
    exports de.renew.netdoc.model.command.exceptions;
    exports de.renew.netdoc.model.container;
    exports de.renew.netdoc.model.container.commands;
    exports de.renew.netdoc.model.container.commands.manager;
    exports de.renew.netdoc.model.container.containers;
    exports de.renew.netdoc.model.container.event;
    exports de.renew.netdoc.model.container.managers;
    exports de.renew.netdoc.model.doctarget;
    exports de.renew.netdoc.model.doctarget.targets;
    exports de.renew.netdoc.model.document;
    exports de.renew.netdoc.model.document.documents;
    exports de.renew.netdoc.model.document.documents.linear;
    exports de.renew.netdoc.model.document.event;
    exports de.renew.netdoc.model.document.maps;
    exports de.renew.netdoc.model.document.parts;
    exports de.renew.netdoc.model.document.parts.linear;
    exports de.renew.netdoc.model.document.parts.linear.tex;

    exports de.renew.netdoc.renew.gui;
    exports de.renew.netdoc.renew.hotdraw;
    exports de.renew.netdoc.renew.hotdraw.figures;
    exports de.renew.netdoc.renew.plugin;

    //
    requires CH.ifa.draw;
    requires de.renew.gui;
    requires log4j;
    requires java.desktop;

}