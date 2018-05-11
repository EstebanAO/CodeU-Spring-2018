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

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final String password;
  private final Instant creation;
  private String aboutMe;
  private final MessageStore messageStore;
  private final ConversationStore conversationStore;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param creation the creation time of this User
   * @param password the password of this User
   * @param aboutMe the about me text of the User Profile
   */
  public User(UUID id, String name, Instant creation, String password, String aboutMe) {
    this.id = id;
    this.name = name;
    this.creation = creation;
    this.password = password;
    this.aboutMe = aboutMe;
    this.messageStore = MessageStore.getInstance();
    this.conversationStore = ConversationStore.getInstance();
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }
  /** Returns the password of this User. */
  public String getPassword() {
    return password;
  }
  /** Retruns the aboubtMe text of the User. */
  public String getAboutMe() {
    return aboutMe;
  }
  /** Sets the aboutMe text of the User.*/
  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public List<Message> getMessages() {
      List<Message> messagesByUser = messageStore.getMessagesByUser(id);
      return messagesByUser;
  }

  public String getConversationTitle(UUID conversationId) {
      return conversationStore.getConversationTitleWithId(conversationId);
  }

  public void removeMessage(Message message) {
      messageStore.removeMessage(message);
  }

}
