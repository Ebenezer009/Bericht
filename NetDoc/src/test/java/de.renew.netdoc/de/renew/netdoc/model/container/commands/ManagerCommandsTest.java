package de.renew.netdoc.model.container.commands;

import de.renew.netdoc.io.IOManager;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.manager.AllDocumentsCommand;
import de.renew.netdoc.model.container.commands.manager.AllTargetsCommand;
import de.renew.netdoc.model.container.commands.manager.CurrentDocumentCommand;
import de.renew.netdoc.model.container.commands.manager.CurrentTargetCommand;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentMap;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ManagerCommandsTest {

    @Mock
    private ContainerManager manager;
    @Mock
    private Document document;
    @Mock
    private DocTarget target;
    @Mock
    private DrawingTarget drawingTarget;
    @Mock
    private IOManager ioManager;
    @Mock
    private DocumentMap documentMap;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        setupMocks();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    private void setupMocks() {
        when(manager.getCurrentDocument()).thenReturn(document);
        when(manager.getCurrentTarget()).thenReturn(target);
        when(manager.getIOManager()).thenReturn(ioManager);
        when(manager.getDocumentMap()).thenReturn(documentMap);
        when(documentMap.getDocuments()).thenReturn(List.of(document));
        when(documentMap.getTargets()).thenReturn(List.of((drawingTarget)));
    }

    @Test
    void testGetCloseCurrentDocument() throws CommandException, ContainerException {
        //given
        CurrentDocumentCommand command = (CurrentDocumentCommand) ManagerCommands.getCloseCurrentDocument(manager);
        //when
        command.execute();
        //then
        verify(manager).closeDocument(document, false);
    }

    @Test
    void testGetOpenCurrentDocument() throws CommandException, ContainerException {
        //given
        CurrentTargetCommand command = (CurrentTargetCommand) ManagerCommands.getOpenCurrentDocument(manager);
        //when
        command.execute();
        //then
        verify(manager).openDocument(target);
    }

    @Test
    void testGetSaveCurrentDocument() throws CommandException, IOException {
        //given
        CurrentDocumentCommand command = (CurrentDocumentCommand) ManagerCommands.getSaveCurrentDocument(manager);
        //when
        command.execute();
        //then
        verify(ioManager).saveDocumentPart(document);
    }

    @Test
    void testGetCloseAllDocuments() throws CommandException, ContainerException {
        //given
        AllDocumentsCommand command = (AllDocumentsCommand) ManagerCommands.getCloseAllDocuments(manager);
        //when
        command.execute();
        //then
        verify(manager).closeDocument(document, false);
    }

    @Test
    void testGetOpenAllDocuments() throws CommandException, ContainerException {
        //given
        AllTargetsCommand command = (AllTargetsCommand) ManagerCommands.getOpenAllDocuments(manager);
        //when
        command.execute();
        //then
        verify(manager).openDocument(drawingTarget);
    }

    @Test
    void testGetSaveAllDocuments() throws IOException, CommandException {
        //given
        AllDocumentsCommand command = (AllDocumentsCommand) ManagerCommands.getSaveAllDocuments(manager);
        //when
        command.execute();
        //then
        verify(ioManager).saveDocumentPart(document);
    }
}