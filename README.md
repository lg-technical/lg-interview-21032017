# lg-interview-21032017

Create simple service for analysing user elearning course usage. It has to be able to accept 
entries about user activity (in json format), store them (somehow) and allow to retrieve some 
analytic data from them.

1. Functional requirements:
- application has to be able to accept under POST endpoint **/usage/course** request with following json in body
```json 
{   
    "userId"    :   "some_string_with_user_id",   
    "courseId"  :   "some_string_with",  
    "started"   :   "some_stated_date_time",  
    "timeSpent" :   "time_spent_on_course_in_seconds"   
}
```

- dates are and should be specified in format ISO-8601 calendar system,
 eg. `2017-12-03T10:15:30+01:00 Europe/Warsaw`
- application should allow getting statistics for time user spend on courses. `GET /usage/user/{userId}/daily` should return amount 
of time user spent on any courses every day (in seconds). For example

```json 
{   
    "dailyUsage" : [  
       {"day": "2017-12-03", "time" : 34566},  
       {"day": "2017-12-05", "time" : 4536}  
   ]    
}
```

- application should allow getting statistics for user course usage. `GET /usage/user/{userId}/perCourse` should return amount 
of time user spent on each course since beginning of service creation (in seconds). For example

```json 
{   
    "courseUsage" : [  
       {"courseId": "cool45", "time" : 4767},  
       {"courseId": "poor567", "time" : 366}   
   ]    
}
```
- application should allow getting statistics for course usage per day. `GET /usage/course/{courseId}/daily` should return amount 
of time spent by all users on course every day (in seconds). For example

```json
{   
    "courseUsage" : [  
       {"day": "2017-12-03", "time" : 134566},  
       {"day": "2017-12-05", "time" : 24536},  
       {"day": "2017-12-07", "time" : 294536}  
   ]    
}
```

- Skip days when no activity is present
- result should be presented assuming UTC timezone

2. Nonfunctional requirements:
- application has to be written in any JVM language (Java 8 is preferable but scala, 
kotlin or groovy are also allowed)
- we do not enforce any web framework (Spring Boot, Dropwizard, Spark, Ratpack - whatever will suit you)
- you can use any persistence method (in-memory, sqllite or something else) but it should not require 
setting up separate database outside of project.
- code should be properly tested
- service dependencies and lifecycle should be managed by proper tool (maven, gradle, sbt) 



