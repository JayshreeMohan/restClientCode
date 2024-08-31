A Rest Client Executor made so that if we have multiple rest calls in the project we don't have to write the rest client code again and again,
this will cater to different type of rest Template to be used, We can pass the request in any form,there isn't a need to change the format of request or response,
Any types of request params and headers can be passed
Future scope of this is we can do ApiTracking for all the api's hit through this on amazon aws
It also calculates the latency of the API, i.e, the time taken by api to execute
JUnit TestCases are written for this with maximum code coverage
