package com.hyrax.backend.service;

import com.hyrax.backend.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static org.junit.Assert.assertEquals;

public class MarkServiceTest extends TestBase {

    @Value("${hyrax.server.address}")
    private String serverAddress;

    @Autowired
    private MarkService markService;

    @Before
    public void setup() {
    }

    @Test
    public void testMarkUrlGenerate() {
        String expect = serverAddress + "/vehicle/mark/bao_ma.jpg/noToken";
        String actual = markService.getUrl("宝马");
        assertEquals("vehicle icon url should be create correctly", expect, actual);
    }

    @Test
    public void testIconRead() {
        byte[] icon = markService.getMark("bao_ma.jpg");
        assertEquals("vehicle icon should be read successfully", true, icon != null);
    }


}
