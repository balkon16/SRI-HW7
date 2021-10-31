This application serves as a demonstration of author's skills in building a web
application using the [Spring Framework](https://spring.io/). Creating the application
was a mandatory element of a semester-long course _Distributed Systems and Service Integration_
conducted at the [Polish-Japanese Academy of Information Technology](https://www.pja.edu.pl/en/).

Having cloned the repository you can run the application locally and access it via the `http://localhost:8081/api` endpoint.

The two entities *DataSources* and *ExchangeRates* are implemented using a one-to-many relationship. A response for a
correct request is enriched with a self-link generated using [HATEOAS](https://en.wikipedia.org/wiki/HATEOAS).

You can recreate requests using the attached Postman collection (the _postman_collection.json_ file). Once you import the
_postman_collection.json_ file to Postman you can find out more about endpoints and methods by consulting usage comments.

The file *task_details_in_polish.pdf* (written in Polish) lists application's requirements.