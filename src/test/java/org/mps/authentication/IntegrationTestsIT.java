package org.mps.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mps.authentication.CredentialValidator.ValidationStatus.VALIDATION_OK;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * The tests in the class assume a happy path (a successful registration)
 */
public class IntegrationTestsIT {

  @Test
  void userRegistrationTopDownIT() {
    Date date = new Date(2, 3, 2010) ;
    PasswordString passwordString = new PasswordString("adfasf.23r4.") ;
    CredentialStore credentialStore = Mockito.mock(CredentialStore.class) ;

    CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class) ;
    Mockito.when(credentialValidator.validate()).thenReturn(VALIDATION_OK) ;

    UserRegistration userRegistration = new UserRegistration() ;
    userRegistration.register(date, passwordString, credentialStore, credentialValidator);

    Mockito.verify(credentialValidator).validate(); ;
    Mockito.verify(credentialStore).register(date, passwordString); ;
  }

  @Test
  void credentialValidatorTopDownIT() {
    Date date = Mockito.mock(Date.class) ;
    Mockito.when(date.validate()).thenReturn(true) ;

    PasswordString passwordString = Mockito.mock(PasswordString.class) ;
    Mockito.when(passwordString.validate()).thenReturn(true) ;

    CredentialStore credentialStore = Mockito.mock(CredentialStore.class) ;

    CredentialValidator credentialValidator = new CredentialValidator(date, passwordString, credentialStore) ;

    UserRegistration userRegistration = new UserRegistration() ;
    userRegistration.register(date, passwordString, credentialStore, credentialValidator);

    Mockito.verify(credentialStore).register(date, passwordString); ;
  }

  @Test
  void credentialStoreTopDownIT() {
    Date date = Mockito.mock(Date.class) ;
    Mockito.when(date.validate()).thenReturn(true) ;

    PasswordString passwordString = Mockito.mock(PasswordString.class) ;
    Mockito.when(passwordString.validate()).thenReturn(true) ;

    CredentialStore credentialStore = new CredentialStoreSet() ;

    CredentialValidator credentialValidator = new CredentialValidator(date, passwordString, credentialStore) ;

    UserRegistration userRegistration = new UserRegistration() ;
    userRegistration.register(date, passwordString, credentialStore, credentialValidator);

    assertEquals(1, credentialStore.size()) ;
  }

  @Test
  void dateTopDownIT() {
    Date date = new Date(10, 2, 1990) ;

    PasswordString passwordString = Mockito.mock(PasswordString.class) ;
    Mockito.when(passwordString.validate()).thenReturn(true) ;

    CredentialStore credentialStore = new CredentialStoreSet() ;

    CredentialValidator credentialValidator = new CredentialValidator(date, passwordString, credentialStore) ;

    UserRegistration userRegistration = new UserRegistration() ;
    userRegistration.register(date, passwordString, credentialStore, credentialValidator);

    assertEquals(1, credentialStore.size()) ;
  }

  @Test
  void passWordStringTopDownIT() {
    Date date = new Date(10, 2, 1990) ;
    PasswordString passwordString = new PasswordString("hello.1323") ;
    CredentialStore credentialStore = new CredentialStoreSet() ;
    CredentialValidator credentialValidator = new CredentialValidator(date, passwordString, credentialStore) ;

    UserRegistration userRegistration = new UserRegistration() ;
    userRegistration.register(date, passwordString, credentialStore, credentialValidator);

    assertEquals(1, credentialStore.size()) ;
    assertTrue(credentialStore.credentialExists(date, passwordString)) ;
  }
}
