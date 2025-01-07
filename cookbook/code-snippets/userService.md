<details>
<summary>

# Method: `isActive(String userName)`

</summary>

This section explains how to use the `isActive(String username)` method to check if a user is active based on their username.

## Method Explanation
The `isActive(String username)` method queries the database for a user with the specified username and returns the activity status (`user_active`) of that user. If the user does not exist, it returns `false`.

## Usage Example
Here's an example of how to call this method in your code:

```java
boolean isUserActive = userService.isActive("john_doe");

if (isUserActive) {
    System.out.println("User is active.");
} else {
    System.out.println("User is not active.");
}
```
In this example, method `isActive` from `UserService` is called a username as an argument. The result is then printed to the console based on whether the user is active or not.

</details>
<details>

<summary>

# Method: `findByName(String userName)`

</summary>

This section explains how to use the `findByName(String userName)` method to retrieve a user by their username from the database.

## Method Explanation
The `findByName(String userName)` method queries the database for a user with the specified username and returns the corresponding `User` object. If no such user exists, it returns `null`.

## Usage Example
Here's an example of how to call this method in your code:

```java
User johnDoe = userService.findByName("john_doe");

if (johnDoe != null) {
    System.out.println("User found: " + johnDoe.getUser_userName());
} else {
    System.out.println("User not found.");
}
```
In this example, method `findByName` from `UserService` is called with a username as an argument. The result is then printed to the console based on whether the user was found or not. If the user is found, their userName is also printed.

</details>

<details>
<summary>

# Method: `findRecordByName(String userName)`

</summary>

This section explains how to use the `findRecordByName(String userName)` method to retrieve a user (as a record, type: `Map<String, Object>`) by their username from the database.

## Method Explanation
The `findRecordByName` method finds a user record by username and returns it as a `Map<String, Object>`.

## Usage Example
Here's an example of how to call this method in your code:

```java
Map<String, Object> userRecord = userService.findRecordByName("john_doe");

if (userRecord != null) {
	System.out.println("User found: " + userRecord.get("user_userName"));
} else {
    System.out.println("User not found.");
}
```
In this example, method `findRecordByName` from `UserService` is called with a username as an argument. The result is then printed to the console based on whether the user was found or not. If the user is found, their userName is also printed.

</details>


