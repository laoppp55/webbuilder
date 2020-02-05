package com.bizwink.cms.toolkit.counter;

import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface IJIMIManager {

  abstract void createImage(String code, OutputStream stream) throws IOException;

  abstract void drawStr(String code);

  abstract int getRand(int max);

  abstract long getCount(int siteid);

  abstract String getCode(String num);

  abstract void insertCount(int siteid, long num);
}