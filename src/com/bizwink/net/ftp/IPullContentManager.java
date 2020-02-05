package com.bizwink.net.ftp;

import java.util.*;
import java.sql.Timestamp;

import com.bizwink.net.ftp.*;

public interface IPullContentManager
{
    PullContent getSiteInfo(int siteid);

    int getPublishWay(int siteid);

    String getSiteName(int siteid);
}
