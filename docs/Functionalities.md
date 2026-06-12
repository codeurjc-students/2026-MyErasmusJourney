
This document describes all the different functionalities which are going to be developed in this project. 
### Legend

✅ Done
🏗️ In progress
🔜 Coming soon
### Basic Functionalities 🛠️

|     | Funcitonality        | Current State | User access                         | Description                                                                                                           |
| :-- | :------------------- | :-----------: | :---------------------------------- | :-------------------------------------------------------------------------------------------------------------------- |
| 1   | Create an account    |      🔜       | Unauthenticated user                | Generate an account with an email, a nickname and a password.                                                         |
| 2   | Log in               |      🔜       | Unauthenticated user                | Authenticate an user, by sending                                                                                      |
| 3   | Update user          |      🔜       | Authenticated user / Administrator  | The user can update its account information                                                                           |
| 4   | Delete user          |      🔜       | Authenticated user /  Administrator | User can delete its own account or it can be deleted by an administrator.                                             |
| 5   | Post experience      |      🔜       | Authenticated user                  | Create and publish a post with information provided by the user                                                       |
| 6   | Delete experience    |      🔜       | Authenticated user / Administrator  | The author of a post as well as an administrator can delete the post.                                                 |
| 7   | Comment post         |      🔜       | Authenticated user                  | Any user can comment in any post                                                                                      |
| 8   | Delete comment       |      🔜       | Authenticated user / Administrator  | Comments can be deleted by their author or an administrator.                                                          |
| 9   | Add city             |      🔜       | Administrator                       | Administrator user can add a city therefore an authenticated user can post experiences related to the new city.       |
| 10  | Show user's posts    |      🔜       | Authenticated user / Administrator  | An authenticated user will be able to see its posts on its account page. Administrators can see anyone's posts.       |
| 11  | Show user's comments |      🔜       | Authenticated user / Administrator  | An authenticated user will be able to see its comments on its account page. Administrators can see anyone's comments. |
### Intermediate Functionalities ⚙️

|     | Funcionalidad            | Estado | Usuarios con acceso  | Descripción                                                                                                     |
| :-- | :----------------------- | :----: | :------------------- | :-------------------------------------------------------------------------------------------------------------- |
| 1   | Filter posts             |   🔜   | Unauthenticated user | Any user has the option to filter the posts/experiences by date, city, rating, type...                          |
| 2   | Add user's profile image |   🔜   | Authenticated user   | Every user with an account will have the opportunity upload a profile image.                                    |
| 3   | Add multimedia to post   |   🔜   | Authenticated user   | Posts will have the possibility to attach multimedia files such as videos or images.                            |
| 4   | Show city related posts  |   🔜   | Unauthenticated user | City page will show the posts related to the city, allowing any user to access this page.                       |
| 5   | Show city average rating |   🔜   | Unauthenticated user | In the city page the average rating of the posts related to the city will be displayed.                         |
| 6   | Share post               |   🔜   | Unauthenticated user | Users will be able to share the link to access a post through other social media applications such as WhatsApp. |

### Advances Functionalities 🚀
|     | Funcionalidad             | Estado | Usuarios con acceso  | Descripción                                                                                                                                            |
| :-- | :------------------------ | :----: | :------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1   | Reply comment             |   🔜   | Authenticated user   | Users will have the possibility to reply to a comment made in a post.                                                                                  |
| 2   | Email verification        |   🔜   | Unauthenticated user | After filling up the register form a code will be sent to the provided email to verify the email and activate the account.                             |
| 3   | Show experiences city map |   🔜   | Unauthenticated user | In the city page a map will be displayed. The map will show different pinpointed locations, each pin will show the experience related to the location. |
| 4   | Show cities map           |   🔜   | Unauthenticated user | The cities page will show a map of Europe pinpointing the cities that have been added to the system.                                                   |
