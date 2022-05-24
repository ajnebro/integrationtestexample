package org.mps.authentication;

/**
 * Interface representing credential store classes
 */
public interface CredentialStore {
  boolean credentialExists(Date date, PasswordString passwordString) ;
  void register(Date date, PasswordString passwordString) throws CredentialExistsException ;

  int size () ;
}
