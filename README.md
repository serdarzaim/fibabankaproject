# fibabankaproject

Postman requests are attached.

##/api/users/register
This API call creates an user.
User must provide informations below in the body of the request.
TCKN, First Name, Last Name, Email, Password
User recieves UserId and authentication token.

##/api/users/login
Users login with this  API call.
User must provide informations below in the body of the request.
TCKN, Password
User recieves an authentication token.

##//api/users/updateuser
This API call updates already existing user.
User must provide informations below in the body of the request.
Also user must provide valid authentication token in the headers.
TCKN can not be changed. 
TCKN, First Name, Last Name, Email, Password

##/api/accounts/all
This API call gets all user informations.
User must provide informations below in the body of the request.
Also user must provide valid authentication token in the headers.
userID
This call returns all information about the users accounts.

##/api/accounts/create
This API call creates an account for the user.
Users can create multiple accounts.
User must provide informations below in the body of the request.
Also user must provide valid authentication token in the headers.
Balance can not be null.
User Id, Title, Description , Balance, Currency

##/api/accounts/delete/{accountId}
This api call deletes an account for the user.
User must provide informations below in the body of the request.
Also user must provide valid authentication token in the headers.
User must provide accountid in the URL.
User Id

##/api/transactions/moneytransfer
This api call transfer amount between two accounts.
User must provide informations below in the body of the request.
Also user must provide valid authentication token in the headers.
Sender Account ID, Reciepent Account ID, Amount, Note
