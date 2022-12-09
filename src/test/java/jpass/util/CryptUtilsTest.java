package jpass.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;

class CryptUtilsTest {

    @Test
    void testGetPKCS5Sha256Hash() throws Exception {
        byte[] expectedHash = {
                // "the cake is a lie" => 652D99FE88A68271D3B6414B6673FB80A0A3FA96B10E4D83FDB1B8AA8B65BC1E
                (byte) 0x65, (byte) 0x2D, (byte) 0x99, (byte) 0xFE, (byte) 0x88, (byte) 0xA6, (byte) 0x82, (byte) 0x71,
                (byte) 0xD3, (byte) 0xB6, (byte) 0x41, (byte) 0x4B, (byte) 0x66, (byte) 0x73, (byte) 0xFB, (byte) 0x80,
                (byte) 0xA0, (byte) 0xA3, (byte) 0xFA, (byte) 0x96, (byte) 0xB1, (byte) 0x0E, (byte) 0x4D, (byte) 0x83,
                (byte) 0xFD, (byte) 0xB1, (byte) 0xB8, (byte) 0xAA, (byte) 0x8B, (byte) 0x65, (byte) 0xBC, (byte) 0x1E,
        };

        Assertions.assertArrayEquals(expectedHash, CryptUtils.getPKCS5Sha256Hash("the cake is a lie".toCharArray()));
    }

    @Test
    void testGetNullPKCS5Sha256Hash() {
        Assertions.assertThrows(Exception.class, () -> CryptUtils.getPKCS5Sha256Hash(null));
    }

    @Test
    void testGetEmptyPKCS5Sha256Hash() throws Exception {
        byte[] expectedHash = {
                // empty char array => 0DC9B0E0900F0CE71F36C359CBCF968D6366F2762F5699A2F5EA5FDCCB70F0C8
                (byte) 0x0D, (byte) 0xC9, (byte) 0xB0, (byte) 0xE0, (byte) 0x90, (byte) 0x0F, (byte) 0x0C, (byte) 0xE7,
                (byte) 0x1F, (byte) 0x36, (byte) 0xC3, (byte) 0x59, (byte) 0xCB, (byte) 0xCF, (byte) 0x96, (byte) 0x8D,
                (byte) 0x63, (byte) 0x66, (byte) 0xF2, (byte) 0x76, (byte) 0x2F, (byte) 0x56, (byte) 0x99, (byte) 0xA2,
                (byte) 0xF5, (byte) 0xEA, (byte) 0x5F, (byte) 0xDC, (byte) 0xCB, (byte) 0x70, (byte) 0xF0, (byte) 0xC8,
        };

        Assertions.assertArrayEquals(expectedHash, CryptUtils.getPKCS5Sha256Hash(new char[0]));
    }

    @Test
    @Timeout(1)
    void testSha256HashIteration0() throws Exception {
        byte[] expectedHash = {
                (byte) 0xCA, (byte) 0x97, (byte) 0x81, (byte) 0x12, (byte) 0xCA, (byte) 0x1B, (byte) 0xBD, (byte) 0xCA,
                (byte) 0xFA, (byte) 0xC2, (byte) 0x31, (byte) 0xB3, (byte) 0x9A, (byte) 0x23, (byte) 0xDC, (byte) 0x4D,
                (byte) 0xA7, (byte) 0x86, (byte) 0xEF, (byte) 0xF8, (byte) 0x14, (byte) 0x7C, (byte) 0x4E, (byte) 0x72,
                (byte) 0xB9, (byte) 0x80, (byte) 0x77, (byte) 0x85, (byte) 0xAF, (byte) 0xEE, (byte) 0x48, (byte) 0xBB,
        };

        Assertions.assertArrayEquals(expectedHash, CryptUtils.getSha256Hash("a".toCharArray()));
    }
}