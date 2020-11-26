package org.mp;

import java.io.IOException;

public interface Observer{
    void notify(Object sender, String infoOut) throws IOException;
}
