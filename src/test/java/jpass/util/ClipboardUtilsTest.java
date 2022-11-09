package jpass.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ClipboardUtilsTest {

    private final class EmptyClipboard implements Transferable, ClipboardOwner {

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                throw new UnsupportedFlavorException(flavor);
            }
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {  }
    }

    private final EmptyClipboard emptyClipboard = new EmptyClipboard();

    @BeforeEach
    public void setUp() throws Exception {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(emptyClipboard, emptyClipboard);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(emptyClipboard, emptyClipboard);
    }

    @Test
    public void testGetEmptyClipboardContent() {
        Assertions.assertNull(ClipboardUtils.getClipboardContent());
    }

    @Test
    public void testGetTextClipboardContent() {
        StringSelection selection = new StringSelection("a text string");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

        Assertions.assertEquals("a text string", ClipboardUtils.getClipboardContent());
    }

    @Test
    public void testGetNonTextClipboardContent() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[] { DataFlavor.imageFlavor };
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.imageFlavor;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                        if (isDataFlavorSupported(flavor)) {
                            return image;
                        } else {
                            throw new UnsupportedFlavorException(flavor);
                        }
                    }
                }, null
        );

        Assertions.assertNull(ClipboardUtils.getClipboardContent());
    }

    @Test
    public void testClearClipboardContent() throws Exception {
        StringSelection selection = new StringSelection("a text string");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

        ClipboardUtils.clearClipboardContent();

        String clipboard = ClipboardUtils.getClipboardContent();

        Assertions.assertNull(clipboard);
    }

    @Test
    public void testSetClipboardContentNull() throws Exception {
        StringSelection selection = new StringSelection("a text string");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

        ClipboardUtils.setClipboardContent(null);

        String clipboard = ClipboardUtils.getClipboardContent();

        Assertions.assertNull(clipboard);
    }

    @Test
    public void testSetClipboardContentEmpty() throws Exception {
        StringSelection selection = new StringSelection("a text string");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

        ClipboardUtils.setClipboardContent("");

        String clipboard = ClipboardUtils.getClipboardContent();

        Assertions.assertNull(clipboard);
    }

    @Test
    public void testSetClipboardContentNormal() throws Exception {
        ClipboardUtils.setClipboardContent("a test string");

        String clipboard = ClipboardUtils.getClipboardContent();

        Assertions.assertEquals("a test string", clipboard);
    }
}