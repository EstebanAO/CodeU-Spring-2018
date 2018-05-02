// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserStore {

  /** Singleton instance of UserStore. */
  private static UserStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserStore getInstance() {
    if (instance == null) {
      instance = new UserStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private Map<UUID, User> usersById;
  private Map<String, User> usersByUsername;


  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    usersById = new HashMap<UUID, User>();
    usersByUsername = new HashMap<String, User>();
  }

  /** Load a set of randomly-generated User objects. */
  public void loadTestDataById() {

    usersById.putAll(DefaultDataStore.getInstance().getAllUsersById());
  }

  /** Load a set of randomly-generated User objects. */
  public void loadTestDataByUsername() {
    usersByUsername.putAll(DefaultDataStore.getInstance().getAllUsersByUsername());
  }

  /**
   * Access the User object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public User getUser(String username) {
    return usersByUsername.get(username);
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public User getUser(UUID id) {
    return usersById.get(id);
  }

  /** Add a new user to the current set of users known to the application. */
  public void addUser(User user) {
    usersById.put(user.getId(), user);
    usersByUsername.put(user.getName(), user);
    persistentStorageAgent.writeThrough(user);
  }

  /** Updates a user to the current set of users known to the application. */
  public void updateUser(User user) {
    persistentStorageAgent.writeThrough(user);
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String username) {
      if (usersByUsername.containsKey(username) )
      {
        return true;
      }
    return false;
  }

  /**
   * Sets the Map of Users by id stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsersById(Map<UUID, User> users) {
    this.usersById = users;
  }

  /**
   * Sets the MAp of Users by username stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsersByUsername(Map<String, User> users) {
    this.usersByUsername = users;
  }
}
