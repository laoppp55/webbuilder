package com.bizwink.webapps.security;
import java.util.*;
import java.lang.reflect.*;

/**
 * Provides authorization service for the forum system. The service
 * is pluggable to allow easier integration for custom authentication.
 */
public interface IWebAuthManager {
  /**
   * Gets authorization for a user.
   *
   * @throws:UnauthedException if authentication failed.
   */
  public webAuth getAuth(String username, String password) throws webUnauthedException;

  /**
   * Gets the anonymous user authorization.
   */
  public webAuth getAnonymousAuth();
}