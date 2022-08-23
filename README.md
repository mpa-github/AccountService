# AccountService
Education project to learn the basics of user authentication and authorization, and how to register security events, and get acquainted with various requirements, such as modern information security standards for web applications.

Java version "17.0.3"

Features implementation:

Develop and implement the API structure:

Authentication
-POST api/auth/signup (allows the user to register on the service)
-POST api/auth/changepass (changes a user password)

Business functionality
-GET api/empl/payment (gives access to the employee's payrolls)
-POST api/acct/payments (uploads payrolls)
-PUT api/acct/payments (updates payment information)

Service functionality
-PUT api/admin/user/role (changes user roles)
-DELETE api/admin/user (deletes a user)
-GET api/admin/user (displays information about all users)

Security functionality
-GET /api/security/events (displays information about security events)

Add the user authentication and authorization functionality to the service.
Implement some requirements of security standards for the authentication procedure.
