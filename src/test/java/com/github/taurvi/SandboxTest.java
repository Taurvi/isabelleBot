package com.github.taurvi;

import org.bson.types.ObjectId;
import org.junit.Test;

public class SandboxTest {
    @Test
    public void sandboxTest() {
        for(int i = 0; i < 5; i++) {
            ObjectId objectid = new ObjectId();
            System.out.println(objectid.toHexString());
        }
    }
}
