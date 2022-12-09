package jpass.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;


public class EmptyClipboardContentTest {
    @Test
    public void testEmptyClipboardObject() {
        ClipboardUtils.EmptyClipboardContent emptyClipboardContent = new ClipboardUtils.EmptyClipboardContent();

        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        Assertions.assertFalse(emptyClipboardContent.isDataFlavorSupported(dataFlavor));
        Assertions.assertArrayEquals(new DataFlavor[] { }, emptyClipboardContent.getTransferDataFlavors());
        Assertions.assertThrows(UnsupportedFlavorException.class, () -> emptyClipboardContent.getTransferData(dataFlavor));
    }
}

