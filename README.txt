Third Party Libraries: 
* OkHttp (to fetch the api)
* Gson (to decipher the JSON file)

Notes:
* I did not finish developing the second screen do to issues encountered with trying to implement a nested recyclerview for the My Rides screen. 
* Was able to fetch the given json file and use Gson to organize the information
* Used recyclerview for rides per day list and hard coded 3 cardviews for individual ride details within the day. The app supports up to 3 rides perday eith each ride having a max of 3 waypoints. 

* next time, would definitely use value xml files to organize. Didn't really focus on that because was too busy trying to get nested recyclerview to work. Definitely refactor to clean up the code. 