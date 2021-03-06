# TechNews


News feed app which gives a user regularly-updated news from the internet related to technology use.

App was built with use the Guardian API. This is a well-maintained API which returns information in a JSON format.

![newsappgif](https://user-images.githubusercontent.com/29640816/41799635-6c8de3ec-762f-11e8-97b5-a18d1650ed1b.gif)

When there is no data to display, the app shows a custom screen that informs the user how to populate the list.

![nonewsgif](https://user-images.githubusercontent.com/29640816/41812560-95512bc8-76e2-11e8-942e-c8bfcdb8ab72.gif)

The app checks whether the device is connected to the internet and responds appropriately. The result of the request is validated to account for a bad server response or lack of server response. The App displays custom screen with possible solutions for internet conection problems.

![nointernetgif](https://user-images.githubusercontent.com/29640816/41812591-297e445c-76e3-11e8-9488-6d2d5199a88f.gif)

App has a settings screen that was added to the News Feed app which was made earlier. It allows users to narrow down the 
stories displayed in from the feed. The available preference options presented to the user are the earliest date of stories
and order of stories depending on relevance or date.

![rightformatgif](https://user-images.githubusercontent.com/29640816/42190596-38696426-7e1b-11e8-809d-62d0cd6d87f2.gif)
![relevancegif](https://user-images.githubusercontent.com/29640816/42199006-ffb103c2-7e48-11e8-9be2-f017826610e1.gif)

If user enters the date in a wrong format, he is prompted to check his date.
![wrongformatgif](https://user-images.githubusercontent.com/29640816/42190651-8514a736-7e1b-11e8-841c-a68d6e2f9778.gif)

App queries the content.guardianapis.com API to fetch news stories related to the topic chosen by the user.
![topicgif](https://user-images.githubusercontent.com/29640816/42237495-292fd3fa-7ebb-11e8-91da-04f2fe544bf5.gif)
![topic2gif](https://user-images.githubusercontent.com/29640816/42237522-415156a2-7ebb-11e8-899b-de8849df79b8.gif)
